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

class User(pName : String, pCountry : String, pCpu : Int) {
  private val name = pName
  private val country = pCountry
  private val cpu = pCpu
  private var muted = false
  private var muteReason = ""
  
  def getName = name
  
  def getCountry = country
  
  def getCpu = cpu
  
  def setMuteEnabled(value : Boolean) {
    muted = value 
    
    if(!muted) {
      muteReason = ""
    }
  }
  
  def getIsMuted = muted
  
  override def toString = name

}
