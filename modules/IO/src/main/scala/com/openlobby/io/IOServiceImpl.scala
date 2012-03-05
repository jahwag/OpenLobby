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

package com.openlobby.io

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Properties
import org.osgi.service.log.LogService

class IOServiceImpl extends IOService {
  @volatile private var logService : LogService = _
  private final val CONFIG_FILENAME = "./config.cfg"
  private val config = new Properties
 
  def loadConfig {
    var is : InputStream = null
    
    // Open inputstream
    try {
      is = new FileInputStream(CONFIG_FILENAME)
    } catch {
      case e: FileNotFoundException => is = createConfig
      case e: Exception => logService.log(LogService.LOG_WARNING, "Could not read " + CONFIG_FILENAME +".")
    }
    
    // Load XML
    config.load(is)
    
    logService.log(LogService.LOG_INFO, "Loaded config file.")
    
    // Close inputstream
    is.close
  }
  
  /**
   * Creates config xml-file.
   */
  @throws(classOf[Exception])
  private def createConfig:InputStream = {
    // Write config to disk
    val os = new FileOutputStream(CONFIG_FILENAME)
    config.store(os, CONFIG_FILENAME)
    os.close
    
    logService.log(LogService.LOG_INFO, "Created config file.")
    
    return new FileInputStream(CONFIG_FILENAME)
  }

  def getConfigValue(key : String):String = {
    return config.getProperty(key)
  }
  
  def getConfigValues(key : String):Array[String] = {
    return getConfigValue(key).split(", ")
  }
  
  def setConfigValue(key : String, value : String) {
    config.setProperty(key, value)
  }
  
  def setConfigValues(key : String, value : Array[String]) {
    config.setProperty(key, value.mkString(", "))
  }
  
  def createScriptText(properties : Array[String]) {
    // TODO implement.
  }
  
}