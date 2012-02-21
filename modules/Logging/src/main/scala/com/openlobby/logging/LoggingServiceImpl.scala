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

package com.openlobby.logging

import java.text.SimpleDateFormat
import org.osgi.service.log.LogEntry
import org.osgi.service.log.LogListener
import org.osgi.service.log.LogReaderService
import org.osgi.service.log.LogService

class LoggingServiceImpl extends LoggingService with LogListener {
  @volatile private var logService : LogService = _
  
  def logged(entry : LogEntry) {
    val time = new SimpleDateFormat("hh:mm:ss zzz").format(entry.getTime)
    val std = "["+  entry.getBundle.getSymbolicName +"]: " + entry.getMessage
    var msg = ""
    entry.getLevel match {
      case LogService.LOG_INFO => msg = time + "[INFO]" + std
      case LogService.LOG_WARNING => msg = time + "[WARNING]" + std
      case LogService.LOG_DEBUG => msg = time + "[DEBUG]" + std
      case LogService.LOG_ERROR => msg = time + "[ERROR]" + std +" "+ entry.getException.getStackTrace.mkString("\n* ")
    }
    println(msg)
  }
  
  def added(logReaderService : LogReaderService) {
    logService.log(LogService.LOG_INFO, "LogListener added.")
    logReaderService.addLogListener(this) // register as a listener
  }
  
  def removed(logReaderService : LogReaderService) {
    logService.log(LogService.LOG_INFO, "LogListener removed.")
    logReaderService.removeLogListener(this) // unregister as a listener
  }  
}
