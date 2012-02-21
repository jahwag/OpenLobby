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

import com.openlobby.communication.ListenerObserver
import com.openlobby.communication.ListenerService
import com.openlobby.communication.MessengerService
import com.springrts.unitsync.Unitsync
import org.apache.felix.dm.DependencyActivatorBase
import org.apache.felix.dm.DependencyManager
import org.osgi.framework.BundleContext
import org.osgi.service.log.LogService

class Activator extends DependencyActivatorBase {  
  
  def init(ctx : BundleContext, manager : DependencyManager) {
    manager.add(createComponent
                .setInterface(Array(classOf[LoginService].getName, classOf[ListenerObserver].getName), null)
                .setImplementation(classOf[LoginServiceImpl])
                
                .add(createServiceDependency.setService(classOf[ListenerService])
                     .setRequired(true)
      )
                .add(createServiceDependency
                     .setService(classOf[LogService])
                     .setRequired(false)
      )
                
                .add(createServiceDependency
                     .setService(classOf[Unitsync])
                     .setRequired(false)
      )
                .add(createServiceDependency
                     .setService(classOf[MessengerService])
                     .setRequired(true)
      )
    )
  }
  
  def destroy(ctx : BundleContext, manager : DependencyManager) {
    
  }
  
}
