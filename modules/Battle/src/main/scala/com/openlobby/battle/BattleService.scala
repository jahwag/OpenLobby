/*
 * Copyright 2012 Jahziah Wagner <jahziah[dot]wagner[at]gmail[dot]com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openlobby.battle

import com.openlobby.communication.ListenerObserver

trait BattleService extends ListenerObserver {

  /**
   * Sent by client when he is trying to open a new battle. The client becomes a founder of
   * this battle, if command is successful (see Response section).
   * 16
   * type: Can be 0 or 1 (0 = normal battle, 1 = battle replay)
   * natType: NAT traversal method used by the host. Must be one of: 0: none 1: Hole
   * punching 2: Fixed source ports
   * password: Must be "*" if founder does not wish to have password-protected game.
   * hashcode: A signed 32-bit integer (acquired via unitsync.dll).
   * maphash: A signed 32-bit integer as returned from unitsync.dll.
   * 
   * Response
   * Client is notified about this command's success via OPENBATTLE/OPENBATTLEFAILED
   * commands.
   * 
   * OPENBATTLE type natType password port maxplayers hashcode
   * rank maphash {map} {title} {modname}
   */
  def openBattle(battleType : String, natType : String, password : String, 
                 port : String, maxPlayers : String,  
                 rank : String, mapName : String, title: String, 
                 modName : String)
  
  /**
   * Sent by a client trying to join a battle. Password is an optional parameter.
   * scriptPassword: A random, client-generated string wich will be written to script.txt by
   * the host, to avoid account spoofing (= someone is trying to join the battle under wrong
   * user-name). If set, the password has to be set too, even if it is empty.
   * 
   * JOINBATTLE BATTLE_ID [password] [scriptPassword]
   */
  def joinBattle(battleId : Int)
    
  /**
   * Sent by client in response to a JOINBATTLEREQUEST command in order to allow the
   * user to join the battle.
   */
  def joinBattleAccept(username : String) 
  
  /**
   * Sent by client in response to a JOINBATTLEREQUEST command in order to prevent the
   * user from joining the battle.
   * @reason default is "" or none.
   */
  def joinBattleDeny(username : String, reason : String = "")
  
  /**
   * Sent by the client when he leaves a battle. Also sent by a founder of the battle when he
   * closes the battle.
   */
  def leaveBattle
  
  /**
   * Sent by the founder of the battle telling the server some of the "outside" parameters of
   * the battle changed.
   * locked: A boolean (0 or 1). Note that when client creates a battle, server assumes it is
   * unlocked (by default). Client must make sure it actually is.
   * maphash: A signed 32-bit integer. See OPENBATTLE command for more info.
   * mapname: Must NOT contain file extension!
   * 
   * UPDATEBATTLEINFO SpectatorCount locked maphash {mapname}
   */
  def updateBattleInfo(spectatorCount : Int, locked : Boolean, mapHash : Int,
                       mapName : String)
  
  /**
   * Sent by client who is participating in a battle to server, who forwards this message to all
   * other clients in the battle. BATTLE_ID is not required since every user can participate in
   * only one battle at the time. If user is not participating in the battle, this command is
   * ignored and is considered invalid.
   */
  def sayBattle(message : String)
  
  /**
   * Sent by any client participating in a battle when he wants to say something in "/me" irc
   * style. Server can forge this command too (for example when founder of the battle kicks a
   * user, server uses SAYBATTLEEX saying founder kicked a user).
   */
  def sayBattleEx(message : String)
  
  /**
   * Sent by client to server telling him his status changed. To figure out if battle is
   * "in-game", client must check in-game status of the host.
   * status: A signed integer in text form (e.g. "1234"). Each bit has its meaning:
   * b0 = in game (0 - normal, 1 - in game)
   * b1 = away status (0 - normal, 1 - away)
   * b2-b4 = rank (see Account class implementation for description of rank) - client is
   * not allowed to change rank bits himself (only server may set them).
   * b5 = access status (tells us whether this client is a server moderator or not) - client
   * is not allowed to change this bit himself (only server may set them).
   * b6 = bot mode (0 - normal user, 1 - automated bot). This bit is copied from user's
   * account and can not be changed by the client himself. Bots differ from human
   * players in that they are fully automated and that some anti-flood limitations do not
   * apply to them.
   */
  def myStatus(status : Int)
  
  /**
   * Sent by a client to the server telling him his status in the battle changed.
   * battlestatus: An integer but with limited range: 0..2147483647 (use signed int and
   * consider only positive values and zero). Number is sent as text. Each bit has its meaning:
   * b0 = undefined (reserved for future use)
   * b1 = ready (0=not ready, 1=ready)
   * b2..b5 = team no. (from 0 to 15. b2 is LSB, b5 is MSB)
   * b6..b9 = ally team no. (from 0 to 15. b6 is LSB, b9 is MSB)
   * b10 = mode (0 = spectator, 1 = normal player)
   * b11..b17 = handicap (7-bit number. Must be in range 0..100). Note: Only host can
   * change handicap values of the players in the battle (with HANDICAP command).
   * These 7 bits are always ignored in this command. They can only be changed using
   * HANDICAP command.
   * b18..b21 = reserved for future use (with pre 0.71 versions these bits were used for
   * team color index)
   * b22..b23 = sync status (0 = unknown, 1 = synced, 2 = unsynced)
   * b24..b27 = side (e.g.: arm, core, tll, ... Side index can be between 0 and 15,
   * inclusive)
   * b28..b31 = undefined (reserved for future use)
   * 
   * @myteamcolor: Should be 32-bit signed integer in decimal form (e.g. 255 and not FF)
   where each color channel should occupy 1 byte (e.g. in hexdecimal: $00BBGGRR, B =
   blue, G = green, R = red). Example: 255 stands for $000000FF.
   */
  def myBattleStatus(battleStatus : Int, myTeamColor : String) 
    
  
  /**
   * Sent by founder of the battle changing username's handicap value (of his battle status).
   * Only founder can change other users handicap values (even they themselves can't
   * change it).
   * value: Must be in range [0, 100] (inclusive).
   */
  def setHandicapStatus(username : String, value : Int) 
    
  /**
   * Sent by founder of the battle when he kicks the client out of the battle. Server remove
   * client from the battle and notify him about it via FORCEQUITBATTLE command.
   */
  def kickFromBattle(username : String) 
    
  /**
   * Sent by founder the of battle when he is trying to force some other client's team number
   * to 'teamno'. Server will update client's battle status automatically.
   */
  def forceTeamNo(username : String, teamNo : Int) 
    
  /**
   * Sent by founder of the battle when he is trying to force some other client's ally number to
   * 'allyno'. Server will update client's battle status automatically.
   */
  def forceAllyNo(username : String, allyNo : Int)
  
  /**
   * Sent by founder of the battle when he is trying to force some other client's team color to
   * 'color'. Server will update client's battle status automatically.
   * color: Should be a 32-bit signed integer in decimal form (e.g. 255 and not FF) where
   * each color channel should occupy 1 byte (e.g. in hexdecimal: $00BBGGRR, B = blue, G
   * = green, R = red). Example: 255 stands for $000000FF.
   */
  def forceTeamColor(username : String, color : String)
  
  /**
   * Sent by founder of the battle when he is trying to force some other client's mode to
   * spectator. Server will update client's battle status automatically.
   */
  def forceSpectatorMode(username : String) 
    
  /**
   * Sent by founder of the battle to server telling him he disabled one or more units. At least
   * one unit name must be passed as an argument.
   * unitname1: Multiple units may follow, but at least one must be present in the
   * arguments list.
   */
  def disableUnits(units : Array[String]) 
    
  /**
   * Sent by founder of the battle to server telling him he enabled one or more previous
   * disabled units. At least one unit name must be passed as an argument.
   * unitname1: Multiple units may follow, but at least one must be present in the
   * arguments list.
   */
  def enableUnits(units : Array[String]) 
    
  /**
   * Sent by founder of the battle to server telling him he enabled ALL units and so clearing
   * the disabled units list.
   */
  def enableAllUnits 
    
  /**
   * Sent by client to server when trying to play a "ring" sound to user 'username'. Only
   * privileged users can ring anyone, although "normal" clients can ring only when they are
   * hosting and only players participating in their battle.
   */
  def ring(username : String) 
    
  /**
   * With this command client can add bots to the battle.
   * teamcolor: Should be 32-bit signed integer in decimal system (e.g. 255 and not FF)
   * where each color channel should occupy 1 byte (e.g. in hexdecimal: $00BBGGRR, B =
   * blue, G = green, R = red).
   */
  def addBot(name : String, battleStatus : String, teamColor : String, 
             aidll : String) 
  
  /**
   * Removes a bot from the battle.
   */
  def removeBot(name : String)
  
  /**
   * Sent by client when he is trying to update status of one of his own bots (only bot owner
   * and battle host may update bot).
   * battlestatus: Similar to that of the normal client's, see MYBATTLESTATUS for more info.
   * teamcolor: Should be 32-bit signed integer in decimal system (e.g. 255 and not FF)
   * where each color channel should occupy 1 byte (e.g. in hexdecimal: $00BBGGRR, B =
   * blue, G = green, R = red).
   */
  def updateBot(name : String, battleStatus : String, color : String)
  
  /**
   * Sent by host of the battle adding a start rectangle for 'allyno' ally team. See lobby client
   * implementation and Spring docs for more info on this one. "left", "top", "right" and
   * "bottom" refer to a virtual rectangle that is 200x200 in size, where coordinates should be
   * in interval [0, 200].
   * 
   * ADDSTARTRECT allyno left top right bottom
   */
  def addStartRect(allyNo : Int, left : Int, top : Int, right : Int, bottom : Int) 
    
  /**
   * Sent by host of the battle removing a start rectangle for 'allyno' ally team. See client
   * implementation and Spring docs for more info on this one.
   */
  def removeStartRect(allyNo : Int)
  
  /**
   * Sent by client who is hosting a battle replay game indicating he is now sending us the
   * game script used in the original replay. Server will then forward this script to all other
   * participants in his battle. Correct sequence of commands when sending the script file is
   * this:
   * 1) SCRIPTSTART command
   * 2) multiple SCRIPT commands
   * 3) SCRIPTEND command
   */
  def scriptStart 
  
  /**
   * Sent by client who previously sent SCRIPTSTART command. Multiple commands of this
   * type are expected after SCRIPTSTART command, ending with SCRIPTEND command as
   * the last command in the sequence (also see SCRIPTSTART comments when sent by the
   * client).
   * line: This is s a line read from the replay script file (use one SCRIPT command for each
   * line!).
   */
  def script(line : String)
  
  /**
   * Sent by client indicating he has finished sending us the game script from the battle
   * replay (also see comments for SCRIPTSTART command).
   */
  def scriptEnd
  
  /**
   * Sent by client (battle host), to set script tags in script.txt. The [pair] format is
   * "key=value can have spaces". Keys may not contain spaces, and are expected to use the
   * '/' character to separate tables (see example). In version 0.35 of TASServer command
   * UPDATEBATTLEDETAILS was completely replaced by this command. The list of
   * attributes that were replaced (with example usage):
   * SETSCRIPTTAGS GAME/StartMetal=1000
   * SETSCRIPTTAGS GAME/StartEnergy=1000
   * SETSCRIPTTAGS GAME/MaxUnits=500
   * SETSCRIPTTAGS GAME/StartPosType=1
   * SETSCRIPTTAGS GAME/GameMode=0
   * SETSCRIPTTAGS GAME/LimitDGun=1
   * SETSCRIPTTAGS GAME/DiminishingMMs=0
   * SETSCRIPTTAGS GAME/GhostedBuildings=1
   * Though in reality all tags are joined together in a single SETSCRIPTTAGS command.
   * Note that when specifying multiple key+value pairs, they must be separated by TAB
   * characters. See the examples bellow.
   * 
   * Examples
   * SETSCRIPTTAGS GAME/MODOPTIONS/TEST=true
   * SETSCRIPTTAGS GAME/StartMetal=1000 GAME/StartEnergy=1000
   * See whitespaces: SETSCRIPTTAGS GAME/StartMetal=1000[TAB]GAME
   * /StartEnergy=1000
   */
  def setScriptTags(tags : Array[String]) 
  
  /**
   * Sent by client (battle host), to remove script tags in script.txt.
   */
  def removeScriptTags(tags : Array[String]) 
  
  
  
}
