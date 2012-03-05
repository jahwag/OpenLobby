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

import java.io.IOException

trait IOService {
  
  /**
   * Initializes IO Service.
   */
  def loadConfig

  /**
   * Read a config key.
   * @return value of config key or null if not found.
   */
  def getConfigValue(key : String):String
  
  /**
   * Read a config key which contains multiple sub-elements.
   * @return elements of config key or null if not found.
   */
  def getConfigValues(key : String):Array[String]
  
  /**
   * Sets config key to the specified value.
   * @throws throws IOException if writing failed.
   */
  def setConfigValue(key : String, value : String)
  
  /**
   * Sets config key to the specified values. 
   * @throws throws IOException if writing failed.
   */
  def setConfigValues(key : String, value : Array[String]) 
  
  /**
   * Creates/overwrites script.txt with the specified content.
   * @properties lines to be set inside the [GAME] tag. Should be ended by semicolon. 
   * Example:
   * new String[]{"HostIP=localhost;", "MyPlayerName=hello;"}
   * @throws throws IOException if writing failed.
   */
  @throws(classOf[IOException])
  def createScriptText(properties : Array[String])
  
}
