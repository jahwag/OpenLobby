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
import org.osgi.framework.BundleException
import org.osgi.framework.launch.FrameworkFactory

class Launcher{
  val modulesDir = "module/"
  val context : BundleContext = startFramework
  init
  
  def init {
    try {
      System.setProperty("jna.library.path", "C:\\Program Files (x86)\\Spring")
      launchBundles
    } catch{
      case e : BundleException => println(e.getMessage) // TODO error logging
    }
  }
  
  private def startFramework:BundleContext= {
    val frameworkFactory = ServiceLoader.load(classOf[FrameworkFactory]).iterator().next();
    val config = new HashMap[String, String];
    val framework = frameworkFactory.newFramework(config);
    framework.start();
    return framework.getBundleContext
  }
  
  @throws(classOf[BundleException])
  private def launchBundles {
    val list = List(installBundle("org.osgi.compendium"),
                    installBundle("org.apache.felix.dependencymanager"),
                    installBundle("org.apache.felix.log"), 
                    installBundle("scala-library"), 
                    installBundle("openlobby-logging"), 
                    installBundle("openlobby-commons"), 
                   // installBundle("jna-osgi"), 
                    installBundle("unitsync"), 
                    installBundle("openlobby-login"),
                    installBundle("openlobby-listener")
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
  
  /*
  override def serviceChanged(event : ServiceEvent) {
    val name = event.getServiceReference
    
    event.getType match {
      case ServiceEvent.MODIFIED => println("Modified service: " + name)
      case ServiceEvent.REGISTERED => println("Registered service: " + name)
      case ServiceEvent.UNREGISTERING => println("Unregistered service: " + name)
    }
  }*/
  /*
  override def bundleChanged(event : BundleEvent) {
    val name = event.getBundle.getSymbolicName
    
    event.getType match {
      case BundleEvent.INSTALLED => println("Installed bundle: " + name)
      case BundleEvent.STOPPING => println("Stopping bundle: " + name)
      case BundleEvent.UPDATED => println("Updated bundle: " + name)
      case default => // do nothing
    }
  }*/
}
