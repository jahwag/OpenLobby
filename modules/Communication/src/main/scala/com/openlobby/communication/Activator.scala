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
package com.openlobby.communication

import com.openlobby.commons.CommonsService
import com.openlobby.constants.commons.ServerConstants
import org.apache.felix.dm.DependencyActivatorBase
import org.apache.felix.dm.DependencyManager
import org.osgi.framework.BundleContext
import org.osgi.service.log.LogService

class Activator extends DependencyActivatorBase {  
  
  def init(ctx : BundleContext, manager : DependencyManager) {
    manager.add(createComponent
                .setImplementation(classOf[CommunicationServiceImpl])
                .setInterface(Array(classOf[ListenerService].getName,
                                    classOf[MessengerService].getName), null)
                
                .add(createServiceDependency
                     .setService(classOf[ServerConstants])
                     .setRequired(true)
      )
                .add(createServiceDependency
                     .setService(classOf[CommonsService])
                     .setRequired(true)
      )
                .add(createServiceDependency
                     .setService(classOf[LogService])
                     .setRequired(false)
      )
                .add(createServiceDependency
                     .setService(classOf[ListenerObserver])
                     .setRequired(false)
                     .setCallbacks("added", "removed")
      )
    )
  }
  
  def destroy(ctx : BundleContext, manager : DependencyManager) {
  }
  
}
