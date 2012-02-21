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

import biz.source_code.base64Coder.Base64Coder
import com.openlobby.communication.ListenerObserver
import com.openlobby.communication.MessengerService
import com.springrts.unitsync.Unitsync
import java.net.InetAddress
import java.security.MessageDigest
import org.osgi.service.log.LogService

class LoginServiceImpl extends LoginService with ListenerObserver {
  @volatile var logService : LogService = _
  @volatile var unitsync : Unitsync = _
  @volatile var messengerService : MessengerService = _
  private val model = new LoginModel(this)
  
  def update(cmd: String, args : Array[String], sentences : Array[String]) {
    cmd match {
      case "TASServer" => model.tasServer(args apply(1), args apply(2), args apply(3), args apply(4))
      case "REGISTRATIONDENIED" => model.registrationDenied( (args slice(1, args length)) mkString(" ") )
      case "REGISTRATIONACCEPTED" => model.registrationAccepted
      case "ACCEPTED" => model.accepted
      case "DENIED" => model.denied( (args slice(1, args length)) mkString(" ") )
      case "LOGINFOEND" => model.loginInfoEnd
      case "AGREEMENT" => model.agreement(args apply 1)
      case "AGREEMENTEND" => model.agreementEnd
      case default => //logService.log(LogService.LOG_INFO, "Login got unspported command: " + cmd +".")
    }
  }
  
  def md5Base64(msg : String):String = {
    val md = MessageDigest.getInstance("MD5")
    
    val bytes = msg.getBytes
    
    val digest = md.digest(bytes)
    
    val str = Base64Coder.encodeLines(digest)
    
    return str
  }
  
  def login(username : String, password : String, compFlags : String = "b sp") {
    val cpu = Runtime.getRuntime().availableProcessors // no. of cores, a compromise due to lack of cpu frequency from java api, though probably more relevant today anyway
    val localIP = InetAddress.getLocalHost().getAddress.toString // for NAT hole-punching
    val lobbyNameAndVersion = "OpenLobby"
    val userId = Math.abs(username.hashCode)
    val passwordMD5 = md5Base64(password)
    
    messengerService.send("LOGIN "+username+" "+passwordMD5+" "+cpu+" "+localIP+" "+lobbyNameAndVersion+" "+userId+" "+compFlags) // send login command
  }
  
  def register(username : String, password : String) = messengerService.send("REGISTER " + username + " " + password )
  
  def renameAccount(username : String) = messengerService.send("RENAME" + username)
  
  def changePassword(oldPassword : String, newPassword : String) = messengerService.send("CHANGEPASSWORD" + oldPassword + " " + newPassword)
  
  def confirmAgreement = messengerService.send("CONFIRMAGREEMENT")
  
}