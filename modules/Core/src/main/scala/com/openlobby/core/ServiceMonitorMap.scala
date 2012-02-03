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

import scala.collection.mutable.HashMap

/**
 * Synchronized monitor of service use.
 */
class ServiceMonitorMap {
  private val serviceInUse = new HashMap[String, Int]
  
  private def synchronizedAccess(fun : Unit) = synchronized { fun }
  
  def addService(service : Object) = 
    synchronizedAccess(serviceInUse put (resolve(service), 0) )

  def removeService(service : Object) = 
    synchronizedAccess( serviceInUse -=(resolve(service)) )
  
  def incrementService(service : Object) = 
    synchronizedAccess( serviceInUse += ((resolve(service), serviceInUse(resolve(service))+1)) )    
  
  def decrementService(service : Object) = 
    synchronizedAccess( serviceInUse += ((resolve(service), serviceInUse(resolve(service))-1)) )  
  
  def get(service : Object):Int = {serviceInUse(resolve(service)) }

  def contains(service : Object):Boolean = { serviceInUse.contains(resolve(service)) }
  
  private def resolve(service : Object):String = service.getClass.getSimpleName
}
