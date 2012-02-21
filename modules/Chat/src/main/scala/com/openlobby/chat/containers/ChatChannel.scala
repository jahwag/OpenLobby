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

package com.openlobby.chat.containers

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer

class ChatChannel {
  private val users = new ArrayBuffer[User]
  private val text = new ListBuffer[ChatMessage]
  private var topic = ""
  private var topicAuthor = ""
  private var topicTimestamp : Long = 0
  private var userCount = 0
  
  /**
   * Set the channel topic.
   */
  def setTopic(topic : String, author : String, time : Long) {
    this.topic = topic
    this.topicAuthor = author
    this.topicTimestamp = time
  }
  
  /**
   * Set the number of users in the channel (by the server).
   * Probably used for private channels.
   */
  def setUserCount(count : Int) {
    userCount = count
  }
  
  /**
   * Add user to channel.
   * @user user to add.
   */
  def addUser(user : User) {
    users += user
  }
  
  /**
   * Remove user from channel.
   * @user user to remove.
   */
  def removeUser(user : User) {
    users -= user
  }
  
  /**
   * Check if channel contains user.
   * @return true if contains, otherwise false.
   */
  def containsUser(user : User):Boolean = users.contains(user)
  
  /**
   * Retrieve all users in channel.
   */
  def getAllUsers = users
  
  /**
   * Adds a new channel message.
   */
  def addMessage(timestamp : Long, author : User, message : String) {
    text += new ChatMessage(timestamp, author, message)
  }

  /**
   * Adds a new channel message.
   */
  def addMessage(message : ChatMessage) {
    text += message
  }
  
  /**
   * Clears all channel messages.
   */
  def clear {
    text.clear
  }
  
  override def toString = topic

}
