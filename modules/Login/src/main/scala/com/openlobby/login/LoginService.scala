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

trait LoginService {
  
  /**
   * Login to the server.
   * @username account username to connect with.
   * @password account password to connect with.
   * @compflags default = a b sp. Excerpt from lobby protocol: 
   * When connecting, the lobby client can tell the lobby server it is compatible
   * with some optional functionalities that break backward compatibility. Each flag in the
   * space separated compFlags parameter indicates a specific functionality (like IRC
   * user/channel flags). By default, all the optional functionalities are considered as not
   * supported by the client. The currently compatibility flags are:
   * 'a': client supports accountIDs sent in ADDUSER command
   * 'b': client supports battle authorization system (JOINBATTLEREQUEST,
   * JOINBATTLEACCEPT and JOINBATTLEDENY commands)
   * 'sp': client supports scriptPassword sent in the JOINEDBATTLE command
   
   */
  def login(username : String, password : String, compFlags : String = "a b sp")
  
  /**
   * Register a new account.
   * @username account username to register.
   * @password account password to register. Will be encrypted with MD5 in base64-form.
   */
  def register(username : String, password : String)
  
  /**
   * Rename account currently logged in to.
   * @username the new username.
   */
  def renameAccount(username : String)
  
  /**
   * Will change password of client's account (which he is currently using).
   * @param oldPassword previous account password.
   * @param newPassword new account password.
   */
  def changePassword(oldPassword : String, newPassword : String)
  
  /**
   * Sent by client notifying the server that user has confirmed the agreement. Also see
   | AGREEMENT command.
   */
  def confirmAgreement
  
}
