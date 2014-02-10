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

import com.openlobby.primer.GenericObserver

class BattleModel(parent : BattleServiceImpl) extends GenericObserver[BattleModelObserver] {

  /**
   * Sent to a client who previously sent OPENBATTLE command to server, if client's request
   * to open new battle has been approved. If server rejected client's request, client is
   * notified via OPENBATTLEFAILED command. Server first sends BATTLEOPENED
   * command, then OPENBATTLE command (this is important - client must have the battle in
   * his battle list before he receives OPENBATTLE command!).
   */
  def openBattle(battleId : String) {
    
  }
  
  /**
   * Sent by server to all registered users, when a new battle has been opened. Series of
   * BATTLEOPENED commands are sent to user when he logs in (1 command for each
   * battle). Use Battle.createBattleOpenedCommand method to create this command in a
   * String (when modifying TASServer source).
   * type: Can be 0 or 1 (0 = normal battle, 1 = battle replay)
   * natType: NAT traversal method used by the host. Must be one of: 0: none 1: Hole
   * punching 2: Fixed source ports
   * founder: Username of the client who started this battle.
   * passworded: A boolean - must be "0" or "1" and not "true" or "false" as it is default in
   * Java! Use Misc.strToBool and Misc.boolToStr methods (from TASServer source) to
   * convert from one to another.
   * 
   * maphash: A signed 32-bit integer as returned from unitsync.dll.
   * BATTLECLOSED BATTLE_ID 
   * Description
   * Sent when founder has closed a battle (or if he was disconnected).
   * 
   * BATTLEOPENED BATTLE_ID type natType founder IP port
   * maxplayers passworded rank maphash {map} {title} {modname}
   */
  def battleOpened(battleId : String, battleType : String, 
                           natType : String, founder : String, ip : String, 
                           port : String, maxPlayers : String, 
                           passworded : String, rank : String, mapHash : String, 
                           mapName : String, title : String, modName : String) {
    
  }
  
  /**
   * Sent when founder has closed a battle (or if he was disconnected).
   */
  def battleClosed(battleId : String) {
    
  }
  
  /**
   * Sent by server telling the client that he has just joined the battle successfully. Server will
   * also send a series of CLIENTBATTLESTATUS commands after this command, so that user
   * will get the battle statuses of all the clients in the battle.
   * hashcode: A signed 32-bit integer.
   */
  def joinBattle(battleId : String, hashCode : String) {
    
  }
  
  /**
   * Sent by server to battle founder each time a client requests to join his battle, if the battle
   * founder supports battle authorization system (i.e. he provided the battle authorization
   * compatibility flag ('b') in the LOGIN command.).
   * 
   * Response
   * When client receives this command, he must send either a JOINBATTLEACCEPT or a
   * JOINBATTLEDENY command to the server.
   */
  def joinBattleRequest(username : String, ip : String) {
    
  }
  
  /**
   * Sent by server to all clients when a new client joins the battle.
   * scriptPassword: A random, client-generated string wich will be written to script.txt by
   * the host, to avoid account spoofing (= someone is trying to join the battle under wrong
   * user-name).
   */
  def joinedBattle(battleId: String, username : String, scriptPassword : String) {
    
  }
  
  /**
   * Sent by server to all users when client left a battle (or got disconnected from the server).
   */
  def leftBattle(battleId : String, username : String) {
    
  }
  
  /**
   * Sent by server to user who just tried to join a battle but has been rejected by server.
   */
  def joinBattleFailed(reason : String) {
    
  }
  
  /**
   * Sent by server to user who just tried to open(=host) a new battle and was rejected by the
   server.
   */
  def openBattleFailed(reason : String)  {
    
  }
  
  /**
   * Sent by server to all registered clients telling them some of the parameters of the battle
   * changed. Battle's inside changes, like starting metal, energy, starting position etc., are
   * sent only to clients participating in the battle via SETSCRIPTTAGS command.
   * SpectatorCount: Assume that spectator count is 0 if battle type is 0 (normal battle) and
   * 1 if battle type is 1 (battle replay), as founder of the battle is automatically set as a
   * spectator in that case.
   * locked: A boolean (0 or 1). Note that when client creates a battle, server assumes it is
   * unlocked (by default). Client must make sure it actually is.
   * maphash: A signed 32-bit integer. See OPENBATTLE command for more info.
   * 
   * UPDATEBATTLEINFO BATTLE_ID SpectatorCount locked maphash {mapname}
   */
  def updateBattleInfo(battleId : String, spectatorCount : String,
                               locked : String, mapHash : String, mapName : String) {
    
  }
  
  /**
   * Sent by server to all clients participating in a battle when client sent a message to it
   * using SAYBATTLE command. BATTLE_ID is not required since every client knows in
   * which battle he is participating in, since every client may participate in only one battle
   * at the time. If client is not participating in a battle, he should ignore this command or
   * raise an error (this should never happen!).
   */
  def saidBattle(username : String, message : String) {
    
  }
  
  /**
   * Sent by server to all clients participating in a battle when client used SAYBATTLEEX
   * command. See SAYBATTLEEX for more info.
   */
  def saidBattleEx(username : String, message : String) {
    
  }
  
  /**
   * Sent by server to all registered clients indicating that client's status changed. Note that
   * client's status is considered 0 if not said otherwise (for example, when you logon, server
   * sends only statuses of those clients whose statuses differ from 0, to save the bandwidth).
   * status: See MYSTATUS command for possible values of this parameter.
   */
  def clientStatus(username : String, status : String) {
    
  }
  
  /**
   * Sent by server to users participating in a battle when one of the clients changes his
   * battle status.
   * 22
   * battlestatus: See MYBATTLESTATUS command for possible values of this parameter.
   * teamcolor: Uses same format as the one used with MYBATTLESTATUS command.
   */
  def clientBattleStatus(username : String, battleStatus : String, 
                                 teamColor : String) {
    
  }
  
  /**
   * Sent by server to user who just opened a battle or joined one. This command is sent after
   * all CLIENTBATTLESTATUS commands for all clients have been sent. This way user can
   * choose suitable team, ally and color numbers since he knows battle statuses of other
   * clients already.
   * 
   * Response
   * When client receives this command, he must send MYBATTLESTATUS command to the
   * server so that server can synchronize battle status with user's.
   */
  def requestBattleStatus {
    
  }
  
  /**
   * Sent to client for whom founder requested kick with KICKFROMBATTLE command. Client
   * doesn't need to send LEAVEBATTLE command, that is already done by the server. The
   * only purpose this commands serves to is to notify client that he was kicked from the
   * battle. Note that client should close the battle internally, since he is no longer a part of it
   *(or he can do that once he receives LEFTBATTLE command containing his username).
   */
  def forceQuitBattle {
    
  }
  
  /**
   * Sent by server to all clients in the battle except for the founder, notifying them some
   * units have been added to disabled units list. Also see DISABLEUNITS
   * unitname1: Multiple units may follow, but at least one must be present in the
   * arguments list.
   */
  def disableUnits(units : Array[String]) {
    
  }
  
  /**
   * Sent by server to all clients in the battle except for the founder, notifying them some
   * units have been removed from disabled units list.
   * unitname1: Multiple units may follow, but at least one must be present in the
   * arguments list.
   */
  def enableUnits(units : Array[String]) {
    
  }
  
  /**
   * Sent by server to all clients in the battle except for the founder, telling them that
   * disabled units list has been cleared.
   */
  def enableAllUnits {
    
  }
  
  /**
   * Sent by server to client telling him user 'username' just rang (client should play the
   * "ring" sound once he receives this command).
   */
  def ring {
    
  }
  
  /**
   * Sent by server when in "redirection mode". When client connects, server will send him
   * only this message and disconnect the socket immediately. Client should connect to
   * 'ipaddress' in that case. This command may be useful when official server address
   * changes, so that clients are automatically redirected to the new one.
   * 
   * Examples
   * REDIRECT 87.96.164.14
   */
  def redirect(ip : String) {
    
  }
  
  /**
   * Sent by server when urgent message has to be delivered to all users. Lobby program
   * should display this message either in some system log or in some message box (user must
   * see this message!).
   */
  def broadcast(message : String) {
    
  }
  
  /**
   * This command indicates that client has added a bot to the battle.
   * BATTLE_ID: BATTLE_ID is there just to help client verify that the bot is meant for his
   * battle.
   * teamcolor: Should be 32-bit signed integer in decimal system (e.g. 255 and not FF)
   * where each color channel should occupy 1 byte (e.g. in hexdecimal: $00BBGGRR, B =
   * blue, G = green, R = red).
   */
  def addBot() {
    
  }
  
  /**
   * Indicates that bot has been removed from the battle. Sent by server.
   * BATTLE_ID: BATTLE_ID is there just to help client verify that the bot is meant for his
   * battle.
   */
  def removeBot(battleId : String, name : String) {
    
  }
  
  /**
   * Sent by server notifying client in the battle that one of the bots just got his status
   * updated. Also see UPDATEBOT command.
   * BATTLE_ID: BATTLE_ID is there just to help client verify that the bot is meant for his
   * battle.
   * battlestatus: Similar to that of the normal client's, see MYSTATUC for more info.
   * teamcolor: Should be 32-bit signed integer in decimal system (e.g. 255 and not FF)
   * where each color channel should occupy 1 byte (e.g. in hexdecimal: $00BBGGRR, B =
   * blue, G = green, R = red).
   */
  def updateBot(battleId : String, name : String, battleStatus : String,
                        teamColor : String) {
    
  }
    
  /**
   * Sent by server to clients participating in a battle (except for the host). See lobby client
   * implementation and Spring docs for more info on this one. "left", "top", "right" and
   * "bottom" refer to a virtual rectangle that is 200x200 in size, where coordinates should be
   * in interval [0, 200].
   */
  def addStartRect(allyNo : String, left : String, top : String, 
                           right : String, bottom : String) {
    
  }
   
  /**
   * Sent to clients participating in a battle (except for the host). Also see ADDSTARTRECT
   * command.
   */
  def removeStartRect(allyNo : String) {
    
  }
  
  /**
   * Sent by server to clients participating in a battle replay indicating that server will now
   * begin sending game script from the original replay file (also see SCRIPTSTART comments
   * when sent by the client).
   */
  def scriptStart() {
    
  }
  
  /**
   * Sent by server after sending SCRIPTSTART command. Multiple commands of this type
   * are expected after SCRIPTSTART command, ending with SCRIPTEND command as the
   * last command in the sequence (also see comments for SCRIPTSTART command when sent
   * by the client).
   * @line: This is s a line read from the replay script file (one line per one SCRIPT command).
   */
  def script(line : String) {
    
  }
  
  /**
   * Sent by server indicating he has finished sending the game script from the battle replay
   * (also see comments for SCRIPTSTART command).
   */
  def scriptEnd {
    
  }
  
  /**
   * Relayed from battle host's SETSCRIPTTAGS message. Lobby program must cache and
   * process these tags and apply them accordingly when creating "script.txt" file.
   * 
   * SETSCRIPTTAGS GAME/MODOPTIONS/TEST=true
   * SETSCRIPTTAGS GAME/StartMetal=1000 GAME/StartEnergy=1000
   * See whitespaces: SETSCRIPTTAGS GAME/StartMetal=1000[TAB]GAME
   * /StartEnergy=1000
   */
  def setScriptTags(tags : Array[String]) {
    
  }
 
  /**
   * Relayed from battle host's REMOVESCRIPTTAGS message. Lobby program must remove
   * these tags from its cached script tags database.
   * 
   * Examples
   * REMOVESCRIPTTAGS GAME/MODOPTIONS/TEST1 GAME/MODOPTIONS/TEST2
   */
  def removeScriptTags(tags : Array[String]) {
    
  }
  
  
   
  
  
}
