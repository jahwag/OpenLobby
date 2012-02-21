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
import com.openlobby.communication.MessengerService
import com.springrts.unitsync.Unitsync
import org.osgi.service.log.LogService

class BattleServiceImpl extends BattleService with ListenerObserver {
  @volatile private var logService : LogService = _
  @volatile private var unitsync : Unitsync = _
  @volatile private var messengerService : MessengerService = _
  private val model = new BattleModel(this)
  
  def update(cmd: String, args : Array[String], sentences : Array[String]) {
    cmd match {
      case "OPENBATTLE" => model.openBattle(args apply 1)
      
      
      case "REMOVESCRIPTAGS" => model.removeScriptTags(args.slice(1, args length))
      case "SETSCRIPTTAGS" => model.setScriptTags(args.slice(1, args length))
      case "SCRIPTSTART" => model.scriptStart
      case "SCRIPT" => model.script(args apply 1)
      case "SCRIPTEND" => model.scriptEnd
      case "ENABLEUNITS" => model.enableUnits(args.slice(1, args length))
      case "ENABLEALLUNITS" => model.enableAllUnits
      case "DISABLEUNITS" => model.disableUnits(args.slice(1, args length))
      
        
        /*
         case "FORCESPECTATORMODE" => model.forceSpectatorMode(args apply 1)
         case "REMOVESTARTRECT" => model.removeStartRect((args apply 1).toInt)
         case "ADDSTARTRECT" => model.addStartRect((args apply 1).toInt, (args apply 2).toInt, (args apply 3).toInt, (args apply 4).toInt, (args apply 5).toInt)
         case "UPDATEBOT" => model.updateBot(args apply 1, args apply 2, args apply 3)
         case "REMOVEBOT" => model.removeBot(args apply 1)
         case "ADDBOT" => model.addBot(args apply 1, args apply 2, args apply 3, args apply 4)
         case "RING" => model.ring(args apply 1)
         case "FORCETEAMCOLOR" => model.forceTeamColor(args apply 1, args apply 2)
         case "FORCETEAMNO" => model.forceTeamNo(args apply 1, (args apply 2).toInt)
         case "FORCEALLYNO" => model.forceAllyNo(args apply 1, (args apply 2).toInt)
         case "KICKFROMBATTLE" => model.kickFromBattle(args apply 1)
         case "SETHANDICAPSTATUS" => model.setHandicapStatus(args apply 1, (args apply 2).toInt)
         case "MYBATTLESTATUS" => model.myBattleStatus((args apply 1).toInt, args apply 2)
         case "MYSTATUS" => model.myStatus((args apply 1).toInt)
         case "SAYBATTLEEX" => model.sayBattleEx(args apply 1)
         case "SAYBATTLE" => model.sayBattle(args apply 1)
         case "UPDATEBATTLEINFO" => model.updateBattleInfo((args apply 1).toInt, (args apply 2).toBoolean, (args apply 3).toInt, args apply 4)
         case "JOINBATTLEDENY" => model.joinBattleDeny(args apply 1, args apply 2)
         case "JOINBATTLEACCEPT" => model.joinBattleAccept(args apply 1)
         case "JOINBATTLE" => model.joinBattle((args apply 1).toInt)
         case "LEAVEBATTLE" => model.leaveBattle
         */
      case default => //logService.log(LogService.LOG_DEBUG, "Battle got unsupported command: " + cmd)// do nothing
    }
  }
  
  def openBattle(battleType : String, natType : String, password : String, 
                 port : String, maxPlayers : String,  
                 rank : String, mapName : String, title: String, 
                 modName : String) {
    val mapHash = ""
    val modHash = ""
  }
  
  def removeScriptTags(tags : Array[String]) {
    println(getMethodName() + " is not yet implemented.")
  }

  def setScriptTags(tags : Array[String]) {
    println(getMethodName() + " is not yet implemented.")
  }
  
  def scriptStart {
    println(getMethodName() + " is not yet implemented.")
  }

  def script(line : String) {
    println(getMethodName() + " is not yet implemented.")
  }
  
  def scriptEnd {
    println(getMethodName() + " is not yet implemented.")
  }

  def removeStartRect(allyNo : Int) {
    println(getMethodName() + " is not yet implemented.")
  }

  def addStartRect(allyNo : Int, left : Int, top : Int, right : Int, bottom : Int) {
    println(getMethodName() + " is not yet implemented.")
  }

  def updateBot(name : String, battleStatus : String, color  : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def removeBot(name : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def addBot(name : String, battleStatus  : String, teamcolor  : String, aidll  : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def ring(username : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def enableUnits(units : Array[String]) {
    println(getMethodName() + " is not yet implemented.")
  }
  
  def enableAllUnits {
    println(getMethodName() + " is not yet implemented.")
  }

  def disableUnits(units : Array[String]) {
    println(getMethodName() + " is not yet implemented.")
  }

  def forceSpectatorMode(username : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def forceTeamColor(username : String, color  : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def forceAllyNo(username : String, allyNo : Int) {
    println(getMethodName() + " is not yet implemented.")
  }

  def forceTeamNo(username : String, teamNo : Int) {
    println(getMethodName() + " is not yet implemented.")
  }

  def kickFromBattle(username : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def setHandicapStatus(username : String, value : Int) {
    println(getMethodName() + " is not yet implemented.")
  }

  def myBattleStatus(battleStatus  : Int, myTeamColor : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def myStatus(status : Int) {
    println(getMethodName() + " is not yet implemented.")
  }

  def sayBattleEx(message : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def sayBattle(message : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def updateBattleInfo(spectatorCount : Int, locked : Boolean, mapHash : Int, mapName : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def joinBattleDeny(username : String, reason : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def joinBattleAccept(username : String) {
    println(getMethodName() + " is not yet implemented.")
  }

  def joinBattle(battleId : Int) {
    println(getMethodName() + " is not yet implemented.")
  }
  
  def leaveBattle {
    println(getMethodName() + " is not yet implemented.")
  }
  
  def getMethodName(depth : Int = 1):String = {
    val ste = Thread.currentThread.getStackTrace
    val i = ste.length - 1 - depth
    return ste.apply(i).getMethodName(); //Thank you Tom Tresansky
  }
  
  
}
