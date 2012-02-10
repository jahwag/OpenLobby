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

package com.openlobby.login

import com.openlobby.messenger.MessengerService
import com.springrts.unitsync.Unitsync
import java.net.InetAddress
import org.osgi.service.log.LogService

class LoginServiceImpl extends LoginService {
  @volatile private var logService : LogService = _
  @volatile private var unitsync : Unitsync = _
  @volatile private var messengerService : MessengerService = _
  private var onlinePlayEnabled = true
  
  def update(cmd: String, args : Array[String]) {
    cmd match {
      case "TASServer" => doTASServer(args.apply(1), args.apply(2), args.apply(3), args.apply(4))
      case default => logService.log(LogService.LOG_INFO, "Got unspported command from server: " + cmd +".")
    }
  }
  
  private def doTASServer(serverVersion : String, springVer : String, udpPort : String, serverMode : String) {
    val installVer = unitsync.getSpringVersion
    
    val springVerMajor = if(springVer.split(".").length > 0) springVer.split(".")(0) else springVer
    val springVerMinor = if(springVer.split(".").length > 1) springVer.split(".")(1) else "0"
    val installVerMajor = if(installVer.split(".").length > 0) installVer.split(".")(0) else installVer
    val installVerMinor = if(installVer.split(".").length > 1) installVer.split(".")(1) else "0"
    
    if(springVerMajor.equals(installVerMajor) && !springVerMinor.equals(installVerMinor)) {
      logService.log(LogService.LOG_WARNING, "An official Spring minor version update is available (not mandatory for online play): " + springVer + ".")
      // TODO notify update consumers
    } else if(springVerMajor.equals(installVerMajor)) {
      logService.log(LogService.LOG_INFO, "Server major version " + springVer + " matches local version " + installVer + ". Online play will be enabled.")
    } else {
      logService.log(LogService.LOG_WARNING, "An official Spring major version update is available (mandatory for online play): " + springVer + ".")
      // Disable online play
      onlinePlayEnabled = false
      // TODO notify update consumers
    }
  }
  
  def login(username : String, password : String, compFlags : String = "a b sp") {
    val cpu = Runtime.getRuntime().availableProcessors // no. of cores, a compromise due to lack of cpu frequency from java api, though probably more relevant today anyway
    val localIP = InetAddress.getLocalHost().getAddress.toString // for NAT hole-punching
    val lobbyNameAndVersion = "OpenLobby"
    val userId = Math.abs(username.hashCode)
    
    messengerService.send("LOGIN "+username+" "+password+" "+cpu+" "+localIP+" "+lobbyNameAndVersion+" "+userId+" "+compFlags) // send login command
  }
  
  def register(username : String, password : String) = messengerService.send("REGISTER " + username + " " + password )
  
  def renameAccount(username : String) = messengerService.send("RENAME" + username)
  
  def changePassword(oldPassword : String, newPassword : String) = messengerService.send("CHANGEPASSWORD" + oldPassword + " " + newPassword)
  
  def confirmAgreement = messengerService.send("CONFIRMAGREEMENT")
  
}