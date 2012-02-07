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

package com.openlobby.listener

/**
 * Used to associate interested parties with a ListenerService.
 */
trait ListenerService {
  
  private final val SPRING_SERVER = "lobby.springrts.com"
  private final val SPRING_SERVER_PORT = 8200

  /**
   * Register as an observer of server messages.
   * @caller Object which will be called when there is a new message.
   */
  def registerObserver(caller : ListenerObserver)
  
  /**
   * Unregister as an observer of server messages.
   * @caller Object to remove as an observer.
   */
  def unregisterObserver(caller : ListenerObserver)
  
  
  
}
