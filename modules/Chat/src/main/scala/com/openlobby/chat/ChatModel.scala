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
import com.openlobby.chat.containers.MainChannel
import com.openlobby.chat.containers.ServerMessage
import com.openlobby.chat.containers.User
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import com.openlobby.primer.GenericObserver

class ChatModel(parent : ChatServiceImpl) extends GenericObserver[ChatModelObserver] {
  
  private val main = new MainChannel
  private val channels = new HashMap[String, ChatChannel] // channels, each channel holds a list of users
  private val privateConversations = new HashMap[String, ChatChannel] // private convos,  user->messages
  private val muteList = new HashMap[String, User] // muteList with tuple containing (user, reason)
  private val serverMessages = new ListBuffer[ServerMessage]// servermessages just messages by timestamp
  private val users = new HashMap[String, User] // users just users
  private val serverUser = new User("Server", "", 1)
  private var selfUser : User =_

  /**
   * A general purpose message sent by the server. Lobby program should display it either in
   * the chat log or in some kind of system log, where client will notice it. Also see
   * SERVERMSGBOX command.
   */
  def serverMsg(msg : String) {
    serverMessages += new ServerMessage(System.currentTimeMillis, serverUser, msg)
  }
  
  /**
   * This is a message sent by the server that should open in a message box dialog window
   * (and not just in the console). Also see SERVERMSG command.
   * url: If specified, lobby client should ask user if he wants this url to be opened in a
   * browser window. Lobby client should then (once user clicks OK) launch the default
   * browser and open this url in it.
   * SERVERMSGBOX {message} [{url}]
   */
  def serverMessageBox(msg : Array[String], url : String) {
    getObservers foreach {observer => observer.serverMessageBox(msg mkString(" "), url)}
  }
  
  /**
   * Sent by server to client telling him new user joined a server. Client should add this user
   * to his clients list which he must maintain while he is connected to the server. Server will
   * send multiple commands of this kind once client logs in, sending him the list of all users
   * currently connected.
   * country: A two-character country code based on ISO 3166 standard. See
   * http://www.iso.org/iso/en/prods-services/iso3166ma/index.html
   * cpu: See LOGIN command for CPU field.
   * accountID: Unique ID bound to the account (can be used to track renammed accounts).
   * This field is only provided if the lobby client sent the accountID compatibility flag ('a') in
   * the LOGIN command.
   */
  def addUser( accountId : String, country : String, cpu : String) {
    users += ((accountId, new User(accountId, country, cpu.toInt)))
  }
  
  /**
   * Sent to client telling him a user disconnected from server. Client should remove this user
   * from his clients list which he must maintain while he is connected to the server.
   */
  def removeUser(user : String) {
    users -= user
  }
  
  /**
   * Sent to a client who has successfully joined a channel.
   * channame: Name of the channel to which client has just joined (by previously sending
   * the JOIN command).
   */
  def join(channel : String) {
    (channels apply channel) addUser(selfUser)
  }
  
  /**
   * Sent if joining a channel failed for some reason.
   * reason: Always provided.
   */
  def joinFailed(channel : String, reason : String) {
    getObservers foreach {observer => observer.joinFailed(channel, reason) }
  }
  
  /**
   * Sent by server to client who requested channel list via CHANNELS command. A series of
   * these commands will be sent to user, one for each open channel. Topic parameter may be
   * omited if topic is not set for the
   */
  def channel(channel : String, userCount : Int) {
    (channels apply channel) setUserCount (userCount)
  }
  
  /**
   * Sent to client who previously requested channel list, after a series of CHANNEL
   * commands (one for each channel).
   */
  def endOfChannels() {
    getObservers foreach {observer => observer.endOfChannels(channels) }
  }
  
  /**
   * Sent by server when sending channel's mute list to the client. This command is
   * immediately followed by 0 or more MUTELIST commands and finally by a MUTELISTEND
   * command.
   */
  def muteListBegin() {
    // do nothing, server protocol is superfluous
  }
  
  /**
   * Sent after MUTELISTBEGIN command. Multiple commands of this kind may be sent after
   * MUTELISTSTART command (or none, if mute list is empty).
   * 
   * mute description: Form of this argument is not prescribed (it may vary from version to
   * version). Lobby program should simply display it as it receives it.
   * 
   * Examples
   * MUTELIST Johnny, 345 seconds remaining
   * MUTELIST rabbit, indefinite time remaining
   */
  def muteList(mutedUser : String, description : String) {
    (users apply mutedUser).setMuteEnabled(true)
    muteList += (((users apply mutedUser).getName, users apply mutedUser))
  }
  
  /**
   * Sent by server after it has finished sending the list of mutes for a channel. Also see
   * MUTELIST and MUTELISTBEGIN commands.
   */
  def muteListEnd {
    // do nothing, server protocol is superfluous
  }
  
  /**
   * Sent to client who just joined the channel, if some topic is set for this channel.
   * changedtime: Time in milliseconds since Jan 1, 1970 (UTC). Divide by 1000 to get unix
   * time.
   * CHANNELTOPIC channame author changedtime {topic}
   */
  def channelTopic(channel : String, author : String, changedTime : String, topic : String = "" ) {
    (channels apply channel).setTopic(topic, author, changedTime.toLong)
    getObservers foreach {observer => observer.updateChannel(channels apply channel) }
  }
  
  /**
   * Sent to a client who just joined the channel. Note: Multiple commands of this kind can be
   * sent in a row. Server takes the list of clients in a channel and separates it into several
   * lines and sends each line seperately. This ensures no line is too long, because client may
   * have some limit set on the maximum length of the line and it could ignore it if it was too
   * long. Also note that the client itself (his username) is sent in this list too! So when client
   * receives JOIN command he should not add his name in the clients list of the channel - he
   * should wait for CLIENTS command which will contain his name too and he will add
   * himself then automatically.
   */
  def clients(channelName : String, clients : Array[String]) {
    val channel = channels apply channelName
    clients foreach {client => channel.addUser(users apply client)}
    getObservers foreach {observer => observer.updateChannel(channels apply channelName) }
  }
  
  /**
   * Sent to all clients in a channel (except the new client) when a new client joins the
   * channel.
   */
  def joined(channel : String, username : String) {
    (channels apply channel) addUser(users apply username)
    getObservers foreach {observer => observer.updateChannel(channels apply channel) }
  }
  
  /**
   * Sent by server to all clients in a channel when some client left this channel. WARNING:
   * Server does not send this command to a client that has just left a channel, because there
   * is no need to (client who has left the channel knows that already). Client that was kicked
   * from the channel is notified about it via FORCELEAVECHANNEL command.
   */
  def left(channel : String, username : String, reason : Array[String]) {
    (channels apply channel) removeUser(users apply username)
    getObservers foreach {observer => observer.updateChannel(channels apply channel) }
  }
  
  /**
   * Sent to user who has just been kicked from the channel #channame by user "username".
   * (lobby client should now internally close/detach from the channel as he was removed
   * from the clients list of #channame on server side)
   */
  def forceLeaveChannel(channelName : String, username : String, reason : Array[String]) {
    val channel = (channels apply channelName)
    channel removeUser(users apply username)
    getObservers foreach {observer => observer.kickedFromChannel(channel, username, reason.mkString(" ")) }
  }
  
  /**
   * Sent by server to all clients in a channel. Used to broadcast messages in the channel.
   */
  def channelMessage(channel : String, message : Array[String]) {
    (channels apply channel).addMessage(System.currentTimeMillis, serverUser, message.mkString(" "))
  }
  
  /**
   * Sent by server to all clients participating in this channel when one of the clients sent a
   * message to it (including the author of this message).
   */
  def said(channel : String, username : String, message : Array[String]) {
    (channels apply channel).addMessage(System.currentTimeMillis, users apply username, message.mkString(" "))
  }
    
  /**
   * Sent by server when client said something using SAYEX command.
   */
  def saidEx(channel : String, username : String, message : Array[String]) {
    (channels apply channel).addMessage(System.currentTimeMillis, users apply username, message.mkString(" "))
  }
  
  /**
   * Sent by server to a client who just sent SAYcommand to server. This way client
   * can verify that server did get his message and that receiver will get it too.
   * @username own username.
   * @message own message.
   */
  def sayPrivate(username : String, message : Array[String]) {
    (privateConversations apply username).addMessage(System.currentTimeMillis, users apply username, message.mkString(" "))
  }
  
  /**
   * Sent by server after client has successfully logged in. Server can send multiple MOTD
   * commands (each MOTD corresponds to one line, for example).
   */
  def motd(msg : String) {
    main.appendMotd(msg)
  }
  
  /**
   * When client receives this command, he should immediately respond with a USERID
   * command. If client doesn't have a user ID associated yet, he should generate some
   * random ID (unsigned 32 bit integer) and save it permanently as his user ID. He will then
   * provide this user ID each time he logs on to the server via LOGIN command.
   */
  def acquireUserId() {
    // TODO call messenger and send USERID userID e.g.  USERID FA23BB4A
    // userID: Must be an unsigned 32 bit integer, in a hexadecimal form.
    // From ChatServiceImpl?
  }
    
  
  
   
}
