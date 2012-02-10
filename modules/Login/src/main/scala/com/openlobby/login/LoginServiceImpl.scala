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

package com.openlobby.login

import com.springrts.unitsync.Unitsync
import org.osgi.service.log.LogService

class LoginServiceImpl extends LoginService {
  @volatile private var logService : LogService = _
  @volatile private var unitsync : Unitsync = _
  
  def update(cmd: String, args : Array[String]) {
    cmd match {
      case "TASServer" => doTASServer(args.apply(1), args.apply(2), args.apply(3), args.apply(4))
      case default => logService.log(LogService.LOG_INFO, "Got unspported command from server: " + cmd +".")
    }
  }
  
  def doTASServer(serverVersion : String, springVer : String, udpPort : String, serverMode : String) {
    val installVer = unitsync.getSpringVersion
    
    val springVerMajor = if(springVer.split(".").length > 0) springVer.split(".")(0) else springVer
    val springVerMinor = if(springVer.split(".").length > 1) springVer.split(".")(1) else "0"
    val installVerMajor = if(installVer.split(".").length > 0) installVer.split(".")(0) else installVer
    val installVerMinor = if(installVer.split(".").length > 1) installVer.split(".")(1) else "0"
    
    if(springVerMajor.equals(installVerMajor) && !springVerMinor.equals(installVerMinor)) {
      logService.log(LogService.LOG_WARNING, "An official Spring minor version update is available (not mandatory for online play): " + springVer + ".")
      // TODO notify update consumers
    } else if(springVerMajor.equals(installVerMajor)) {
      logService.log(LogService.LOG_INFO, "Server major version " + springVer + " matches local version " + installVer + ". Online play will be enabled.")
    } else {
      logService.log(LogService.LOG_WARNING, "An official Spring major version update is available (mandatory for online play): " + springVer + ".")
      // Disable online play
      // TODO notify update consumers
    }
    
  }
}