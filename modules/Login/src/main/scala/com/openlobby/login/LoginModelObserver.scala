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

trait LoginModelObserver {

  /**
   * Registration request was denied by server.
   * @reason reason for denial.
   */
  def registrationDenied(reason : String)
  
  /**
   * Registration request was accepted by server.
   */
  def registrationAccepted
  
  /**
   * Server accepted login request.
   */
  def accepted
  
  /**
   * Server denied login request.
   * @reason reason for denial.
   */
  def denied(reason : String)
  
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
  def loginInfoEnd(motd : String)
  
  /**
   * Sent by server after multiple AGREEMENT commands. This way server tells the client
   * that he has finished sending the agreement (this is the time when lobby should popup
   * the "agreement" screen and wait for user to accept/reject it).
   */
  def agreementEnd(line : String)
  
}
