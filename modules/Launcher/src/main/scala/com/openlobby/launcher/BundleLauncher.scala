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

package com.openlobby.launcher

import java.io.File
import java.util.Collections
import java.util.LinkedList
import java.util.prefs.Preferences
import org.osgi.framework.Bundle
import org.osgi.framework.BundleContext
import org.osgi.framework.BundleException

class BundleLauncher(modulesDir : String, context : BundleContext) {
  
  @throws(classOf[BundleException])
  def launchBundles {
    val unitsyncPath = "C:\\Program Files (x86)\\Spring\\unitsync.dll"
    Preferences.userRoot().put("unitsync.path", unitsyncPath);
    //System.setProperty("jna.library.path", unitsyncPath) // TODO
    
    val list = List(installBundle("org.osgi.compendium-4.1.0"),
                    installBundle("org.osgi.compendium-1.4.0"),
                    installBundle("org.apache.felix.dependencymanager"),
                    installBundle("org.apache.felix.log"), 
                    installBundle("groovy"), 
                    installBundle("scala-library"), 
                    installBundle("unitsync"), 
                    installBundle("openlobby-logging"), 
                    installBundle("openlobby-io"),
                    installBundle("openlobby-commons"), 
                    installBundle("openlobby-communication"),
                    installBundle("openlobby-login"),
                    installBundle("openlobby-chat"),
                    installBundle("openlobby-battle")
    )
    
    list.foreach {bundle => bundle.start}
  }
  
  /**
   * Locate the most recent version of a bundle and install it.
   * @param artifactId artifactId of bundle.
   */
  @throws(classOf[BundleException])
  private def installBundle(artifactId: String):Bundle = {
    val files = new File(modulesDir).listFiles
    
    val versions = new LinkedList[File]
    
    files.foreach { file =>
      if(file.getName.contains(artifactId)) versions.add(file)
    }
    
    if(versions.isEmpty) {
      throw new BundleException("[ERROR]: Unable to locate artifactId: " + artifactId)
    }
    
    Collections.sort(versions)
    
    return context.installBundle("file:" + versions.getFirst)
  }
}
