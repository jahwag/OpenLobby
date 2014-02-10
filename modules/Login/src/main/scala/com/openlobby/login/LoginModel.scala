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

import org.osgi.service.log.LogService
import com.openlobby.primer.GenericObserver

class LoginModel(parent : LoginServiceImpl) extends GenericObserver[LoginModelObserver] {
  private var agreement = ""
  private var motd = ""
  private var onlinePlayEnabled = true
  
  def tasServer(serverVersion : String, springVer : String, udpPort : String, serverMode : String) {
    val installVer = parent.unitsync.getSpringVersion
    
    val springVerMajor = if(springVer.split(".").length > 0) springVer.split(".")(0) else springVer
    val springVerMinor = if(springVer.split(".").length > 1) springVer.split(".")(1) else "0"
    val installVerMajor = if(installVer.split(".").length > 0) installVer.split(".")(0) else installVer
    val installVerMinor = if(installVer.split(".").length > 1) installVer.split(".")(1) else "0"
    
    if(springVerMajor.equals(installVerMajor) && !springVerMinor.equals(installVerMinor)) {
      parent.logService.log(LogService.LOG_WARNING, "An official Spring minor version update is available (not mandatory for online play): " + springVer + ".")
      // TODO notify update consumers
    } else if(springVerMajor.equals(installVerMajor)) {
      parent.logService.log(LogService.LOG_INFO, "Server major version " + springVer + " matches local version " + installVer + ". Online play will be enabled.")
    } else {
      parent.logService.log(LogService.LOG_WARNING, "An official Spring major version update is available (mandatory for online play): " + springVer + ".")
      // Disable online play
      onlinePlayEnabled = false
      // TODO notify update consumers
    }
    // test
    //parent.login("oltest", "test1234", "")
  }
  
  /**
   * Registration request was denied by server.
   * @reason reason for denial.
   */
  def registrationDenied(reason : String) = getObservers.foreach {elem => elem.registrationDenied(reason)}
  
  /**
   * Registration request was accepted by server.
   */
  def registrationAccepted = getObservers.foreach {elem => elem.registrationAccepted}
  
  /**
   * Server accepted login request.
   */
  def accepted = getObservers.foreach {elem => elem.accepted}
  
  /**
   * Server denied login request.
   * @reason reason for denial.
   */
  def denied(reason : String) = getObservers.foreach {elem => elem.denied(reason)}
  
  /**
   * Sent by server indicating that it has finished sending the login info (which is sent
   * immediately after accepting the LOGIN command). This way client can figure out when
   * server has finished updating clients and battles info and can so figure out when login
   * sequence is finished. 
   * 
   * Note that sending login info consists of 4 phases:
   * sending MOTD
   * sending list of all users currently logged on the server
   * sending info currently active battles
   * sending statuses of all users
   * LOGININFOEND command is sent when server has finished sending this info.
   */
  def loginInfoEnd = {
    getObservers.foreach {elem => elem.loginInfoEnd(motd)}
    motd = ""
  }
  
  /**
   * Sent by server upon receiving LOGIN command, if client has not yet agreed to server's
   * "terms-of-use" agreement. Server may send multiple AGREEMENT commands (which
   * corresponds to multiple new lines in agreement), finishing it by AGREEMENTEND
   * command. Client should send CONFIRMAGREEMENT and then resend LOGIN command,
   * or disconnect from the server if he has chosen to refuse the agreement.
   * agreement: Agreement is sent in "Rich Text" format (.rtf file streamed via socket).
   * 
   * No response is expected until AGREEMENTEND is received. See that command for more
   * info.
   */
  def agreement(line : String) { agreement += line }
  
  /**
   * Sent by server after multiple AGREEMENT commands. This way server tells the client
   * that he has finished sending the agreement (this is the time when lobby should popup
   * the "agreement" screen and wait for user to accept/reject it).
   */
  def agreementEnd  {
    getObservers.foreach {elem => elem.agreementEnd(agreement)}
    agreement = ""
  }
  
  /**
   * Sent by server after client has successfully logged in. Server can send multiple MOTD
   * commands (each MOTD corresponds to one line, for example).
   */
  def motd(line : String) {
    motd += line
  }
  
}
