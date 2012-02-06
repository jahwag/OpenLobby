package com.openlobby.launcher

import java.io.File
import java.util.Collections
import java.util.LinkedList
import org.osgi.framework.BundleContext

/**
 * Singleton Launcher.
 */
class BundleInstaller(modulesDir : String, frameworkContext : BundleContext) {
  
  /**
   * Locate the most recent version of a bundle.
   * @param artifactId artifactId of bundle.
   */
  private def findLatestBundle(artifactId: String):String = {
    val files = new File(modulesDir).listFiles
    
    val versions = new LinkedList[File]
    
    files.foreach { file =>
      if(file.getName.contains(artifactId)) versions.add(file)
    }
    
    Collections.sort(versions)
    
    return versions.getLast.getName
  }
  
  /**
   * Installs a bundle.
   * Uses specified working directory.
   */
  @throws(classOf[Exception])
  def installBundle(filename: String) {
    try {
      frameworkContext.installBundle(findLatestBundle(filename))
    } catch{
      case e: Exception => throw new Exception(e);
    }
  }

}

