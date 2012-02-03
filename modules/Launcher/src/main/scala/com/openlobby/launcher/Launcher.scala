package com.openlobby.launcher

import java.io.File
import java.util.Collections
import java.util.HashMap
import java.util.LinkedList
import java.util.ServiceLoader
import org.osgi.framework.BundleContext
import org.osgi.framework.launch.Framework
import org.osgi.framework.launch.FrameworkFactory

/**
 * Singleton Launcher.
 */
object Launcher {
  val framework = initFramework
  val context = initContext
  
  private def initFramework:Framework = {
    val fac = ServiceLoader.load(classOf[FrameworkFactory]).iterator().next()
    val config = new HashMap
    return fac.newFramework(config)
  }
  
  private def initContext:BundleContext = {
    startFramework
    val ctx = framework.getBundleContext
    println("OSGi framework loaded.")
    return ctx
  }
  
  /**
   * Starts OSGi framework.
   */
  private def startFramework{
    framework.start
  }
  
  /**
   * Stop OSGi framework.
   */
  def stopOSGi {
    framework.stop
    println("OSGi framework stopped.")
  }
    
  /**
   * Locate the most recent version of a bundle.
   * @param artifactId artifactId of bundle.
   */
  private def findLatestBundle(artifactId: String, workingDir : File):String = {
    val files = workingDir.listFiles
    println(workingDir.getAbsolutePath)
    
    val versions = new LinkedList[File]
    
    files.foreach { file =>
      if(file.getName.contains(artifactId)) versions.add(file)
    }
    
    Collections.sort(versions)
    
    return versions.getLast.getName
  }
  
  def getService(clazz: Object):Object = {
    try {
      def ref = context.getServiceReference(clazz.getClass.getName)
      def service = context.getService(ref)
      return service
    } catch {
      case e: Exception => return null;
    }
  }
  
  /**
   * Installs a bundle.
   * Uses current working directory.
   */
  @throws(classOf[Exception])
  def installBundle(filename: String) {
    installBundle(filename, new File("./"))
  }
  
  /**
   * Installs a bundle.
   * Uses specified working directory.
   */
  @throws(classOf[Exception])
  def installBundle(filename: String, workingDir : File) {
    try {
      context.installBundle(findLatestBundle(filename, workingDir))
    } catch{
      case e: Exception => throw new Exception(e);
    }
  }

}

