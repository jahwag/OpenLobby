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

package com.openlobby.commons

import scala.collection.mutable.ListBuffer

class GenericObserver[T] {
  
  private val observers = new ListBuffer[T]
  
  /**
   * Register as an observer.
   */
  def register(observer : T) = observers append observer
  
  /**
   * Unregister as an observer.
   * 
   * This should be done if a module is removed.
   */
  def unregister(observer : T) = observers remove observers.indexOf(observer)
  
  /**
   * Returns a scala.collection.mutable.ListBuffer of observers.
   */
  def getObservers = observers

}
