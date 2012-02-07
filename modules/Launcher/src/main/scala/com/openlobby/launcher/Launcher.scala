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
import java.util.HashMap
import java.util.LinkedList
import java.util.ServiceLoader
import org.osgi.framework.Bundle
import org.osgi.framework.BundleContext
import org.osgi.framework.launch.FrameworkFactory

class Launcher {
  val modulesDir = "module/"
  val context : BundleContext = startFramework
  launchBundles
  
  private def startFramework:BundleContext= {
    val frameworkFactory = ServiceLoader.load(classOf[FrameworkFactory]).iterator().next();
    val config = new HashMap[String, String];
    val framework = frameworkFactory.newFramework(config);
    framework.start();
    return framework.getBundleContext
  }
  
  private def launchBundles {
    val list = List(installBundle("scala-library"), installBundle("listener"))
    
    list.foreach {bundle => bundle.start}
  }
  
  /**
   * Locate the most recent version of a bundle and install it.
   * @param artifactId artifactId of bundle.
   */
  private def installBundle(artifactId: String):Bundle = {
    val files = new File(modulesDir).listFiles
    
    val versions = new LinkedList[File]
    
    files.foreach { file =>
      if(file.getName.contains(artifactId)) versions.add(file)
    }
    
    Collections.sort(versions)
    
    println("Installed bundle: " + versions.getFirst +".")
    
    return context.installBundle("file:" + versions.getFirst)
  }
}
