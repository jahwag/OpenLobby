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

package com.openlobby.core

/**
 * Singleton service locator that monitors bundle lifecycle.
 * Will block when calling a bundle that is not ACTIVE.
 * 
 * All modules should use this as it is the only safe way of getting external 
 * services through context.
 */
import org.osgi.framework.BundleContext
import org.osgi.framework.ServiceEvent
import org.osgi.framework.ServiceListener

object ServiceTracker extends ServiceListener {
  private val monitor = new ServiceMonitorMap 
  
  def serviceChanged(event : ServiceEvent) {
    event.getType match {
      case ServiceEvent.REGISTERED => println("Started service " + event.getServiceReference.toString)
      case ServiceEvent.UNREGISTERING => println("Shutting down service " + event.getServiceReference.toString)// TODO
    }
  }
  
  /**
   * Register service.
   * @service service to register.
   * @context bundle context.
   */
  def registerService(service : Object, context : BundleContext) {
    context.registerService(service.getClass.getName, service, null)
    context.addServiceListener(this)
  }
  
  /**
   * Releases service. NOTE: This could be blocking call.
   * @service service to unregister.
   * @context bundle context.
   */
   def unregisterService(service : Object, context : BundleContext) {
      awaitServiceCondition(monitor.contains(service.getClass.getName)) // still in use
    
      context.removeServiceListener(this)
      context.ungetService(context.getServiceReference(service.getClass.getName))
    }

   /**
    * Awaits service to become available then returns it. Blocking call.
    * Timeout is 60 seconds. If timeout is reached, an log warning will be written.
    * 
    * @return service requested.
    */
    def getService(service : Object, context : BundleContext):Any = {
        awaitServiceCondition(!monitor.contains(service.getClass.getName)) // while not available
        
        return context.getService(context.getServiceReference(service.getClass.getName))
      }
    
    private def awaitServiceCondition(condition : Boolean) {
        var time = 0
        while(condition) { // still in use
          time += 1000
          Thread.sleep(1000) // Sleep 1
        
          if(time > 60000) { // Time exceeded one minute
            // TODO log WARNING 
          }
        }
      }
  
    }
