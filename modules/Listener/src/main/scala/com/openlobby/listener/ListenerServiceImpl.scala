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

import com.openlobby.commons.CommonsService
import com.openlobby.commons.thread.ServiceThreadImpl
import com.openlobby.constants.commons.ServerConstants
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.util.LinkedList
import org.osgi.service.log.LogService

/**
 * Thread started by OSGi (reflection).
 */
class ListenerServiceImpl extends ServiceThreadImpl with ListenerService {
  
  @volatile private var serverConstants : ServerConstants =_
  @volatile private var commonsService : CommonsService =_
  @volatile private var logService : LogService = _
  private var listenerObservers = new LinkedList[ListenerObserver]
  
  def added(obs : ListenerObserver) {
    logService.log(LogService.LOG_INFO, "Added listener " + obs +".")
    listenerObservers add obs
  }
  
  def removed(obs : ListenerObserver) {
    logService.log(LogService.LOG_INFO, "Removed listener " + obs +".")
    listenerObservers remove obs
  }
  
  override def run {
    logService.log(LogService.LOG_INFO, "Thread started.")

    val (in, os) = connect
    
    try {
      while(getRunState) {
        val msg = listen(in)
        update(msg)
      }
    } catch {
      case e: IOException => logService.log(LogService.LOG_ERROR, e.getMessage) // TODO notify listeners then stop
    }
  }
  
  private def connect:(BufferedReader, BufferedWriter)= {
    val socket = new Socket(serverConstants.getLobbyServer, serverConstants.getLobbyServerPort)
    
    return (new BufferedReader(new InputStreamReader(socket.getInputStream)), 
            new BufferedWriter(new OutputStreamWriter(socket.getOutputStream)))
  }
  
  @throws(classOf[IOException])
  private def listen(in : BufferedReader):String= {
    if(in == null) {
      throw new IOException("No connection to remote server.");
    }
    
    return in.readLine
  }
  
  /**
   * Notifies listeners of message.
   */
  private def update(msg : String) {
    logService.log(LogService.LOG_DEBUG, msg)
    
    val it = listenerObservers.iterator
    while(it.hasNext) {
      val obs : ListenerObserver = it.next
      obs.update(msg)
    }
  }
  
}
