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

import com.openlobby.communication.ListenerObserver

/**
 * Commands which the client can send to the server.
 */
trait ChatService extends ListenerObserver {
  
  /**
   * Sent by client trying to join a channel.
   * key: If channel is locked, then client must supply a correct key to join the channel
   * (clients with access >= Account.ADMIN_ACCESS can join locked channels withouth
   * supplying the key - needed for ChanServ bot).
   */
  def join(channel : String)
  
  /**
   * Sent by client when requesting channels list.
   */
  def channels
    
  /**
   * Sent by client when requesting mute list of a channel.
   */
  def muteList(channel : String)
    
  /**
   * Sent by client when he is trying to leave a channel. When client is disconnected, he is
   * automatically removed from all channels.
   */
  def leave(channel : String)
    
  /**
   * Sent by privileged user who is trying to change channel's topic. Use * as topic if you wish
   * to disable it.
   */
  def channelTopic(channel : String, topic : String) 
    
  /**
   * Sent by client (moderator) requsting that the user is removed from the channel. User will
   * be notified with FORCELEAVECHANNEL command.
   */
  def forceLeaveChannel(channel : String, username : String, reason : Array[String])
    
  /**
   * Sent by client when he is trying to say something in a specific channel. Client must first
   * join the channel before he can receive or send messages to that channel.
   */
  def say(channel : String, message : String)
  
  /**
   * Sent by any client when he is trying to say something in "/me" irc style. Also see SAY
   * command.
   */
  def sayEx(channel : String, message : String)
    
  /**
   * Sent by client when he is trying to send a message to some other client.
   */
  def sayPrivate(username : String, message : String)
    
  
}
    

