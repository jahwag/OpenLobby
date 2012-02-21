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

package com.openlobby.chat

import com.openlobby.chat.containers.ChatChannel
import scala.collection.mutable.HashMap

trait ChatModelObserver {
  
  /**
   * This is a message sent by the server that should open in a message box dialog window
   * (and not just in the console). Also see SERVERMSG command.
   * url: If specified, lobby client should ask user if he wants this url to be opened in a
   * browser window. Lobby client should then (once user clicks OK) launch the default
   * browser and open this url in it.
   * SERVERMSGBOX {message} [{url}]
   */
  def serverMessageBox(msg : String, url : String)
  
  /**
   * Sent if joining a channel failed for some reason.
   * reason: Always provided.
   */
  def joinFailed(channel : String, reason : String)
  
  /**
   * Sent to client who previously requested channel list, after a series of CHANNEL
   * commands (one for each channel).
   */
  def endOfChannels(channels : HashMap[String, ChatChannel])
  
  /**
   * Updated channel.
   */
  def updateChannel(channel : ChatChannel)
  
  /**
   * Sent to user who has just been kicked from the channel #channame by user "username".
   * (lobby client should now internally close/detach from the channel as he was removed
   * from the clients list of #channame on server side)
   */
  def kickedFromChannel(channel : ChatChannel, kickAuthor : String, reason : String)
  
}
