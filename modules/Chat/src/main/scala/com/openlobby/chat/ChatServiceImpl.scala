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
import com.openlobby.communication.MessengerService
import com.springrts.unitsync.Unitsync
import java.util.Arrays
import org.osgi.service.log.LogService

/**
 * Contains implementations of commands the user can send to the server.
 */
class ChatServiceImpl extends ChatService with ListenerObserver{
  @volatile private var logService : LogService = _
  @volatile private var unitsync : Unitsync = _
  @volatile private var messengerService : MessengerService = _
  val model = new ChatModel(this)
  
  def update(cmd: String, args : Array[String], sentences : Array[String]) {
    
    cmd match {
      case "MOTD" => model.motd(args apply 1)
      case "SERVERMSGBOX" => model.serverMessageBox(args slice (1, args.length - 1), args apply (args length))
      case "ADDUSER" => model.addUser(args apply 1, args apply 2, args apply 3)
      case "REMOVEUSER" => model.removeUser(args apply 1)
      case "JOIN" => model.join(args apply 1)
      case "JOINFAILED" => model.joinFailed(args apply 1, args apply 2)
      case "CHANNELS" => model.channel(args apply 1, (args apply 2).toInt)
      case "ENDOFCHANNELS" => model.endOfChannels
      case "MUTELISTBEGIN" => model.muteListBegin
      case "MUTELIST" => model.muteList(args apply 1, args apply 2)
      case "MUTELISTEND" => model.muteListEnd
      case "CHANNELTOPIC" => model.channelTopic(args apply 1, args apply 2, args apply 3, args apply 4)
      case "CLIENTS" => model.clients(args apply 1, args slice (2, args length))
      case "JOINED" => model.joined(args apply 1, args apply 2)
      case "LEFT" => model.left(args apply 1, args apply 2, args slice (3, args length))
      case "FORCELEAVECHANNEL" => model.forceLeaveChannel(args apply 1, args apply 2, args slice (3, args length))
      case "CHANNELMESSAGE" => model.channelMessage(args apply 1, args slice (2, args length))
      case "SAID" => model.said(args apply 1, args apply 2, args slice (3, args length))
      case "SAIDEX" => model.saidEx(args apply 1, args apply 2, args slice(3, args length))
      case "SAYPRIVATE" => model.sayPrivate(args apply 1, args slice(2, args length))
      case "PONG" => ping
      case default => //logService.log(LogService.LOG_DEBUG, "Chat got unsupported command: " + cmd)
    }
  }
  
  /**
   * Client should send this command on every few seconds to maintain constant connection
   * to the server. Server will assume timeout occured if it does not hear from client for more
   * than x seconds. To figure out how long does a reply take, use message ID with this
   * command.
   */
  private def ping {
    
  }
  
  def join(channel : String) {
    println(getMethodName() + " is not yet implemented.")
  }
  
  def channels {
    println(getMethodName() + " is not yet implemented.")
  }
    
  def muteList(channel : String) {
    println(getMethodName() + " is not yet implemented.")
  }
    
  def leave(channel : String) {
    println(getMethodName() + " is not yet implemented.")
  }
    
   
  def channelTopic(channel : String, topic : String) {
    println(getMethodName() + " is not yet implemented.")
  }
  
  def forceLeaveChannel(channel : String, username : String, reason : Array[String]) {
    println(getMethodName() + " is not yet implemented.")
  }
    
  def say(channel : String, message : String) {
    println(getMethodName() + " is not yet implemented.")
  }
  
  def sayEx(channel : String, message : String) {
    println(getMethodName() + " is not yet implemented.")
  }
    
  def sayPrivate(username : String, message : String) {
    println(getMethodName() + " is not yet implemented.")
  }
  
  def getMethodName(depth : Int = 1):String = {
    val ste = Thread.currentThread.getStackTrace
    val i = ste.length - 1 - depth
    return ste.apply(i).getMethodName(); //Thank you Tom Tresansky
  }
    
}
