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

package com.openlobby.listener

import java.util.LinkedList

class ListenerServiceImpl extends Thread with ListenerService {

  private final def observers = new LinkedList[ListenerObserver]
  
  /**
   * Register a ListenerObserver.
   * @param caller receives server messages.
   */
  def registerObserver(caller : ListenerObserver) {
    observers.add(caller)
  }
  
  /**
   * Remove ListenerObserver. 
   * 
   * This is should be done when caller bundle state is no longer ACTIVE 
   * to prevent NullPointerException.
   * @param caller receives server messages.
   */
  def unregisterObserver(caller : ListenerObserver) {
    observers.remove(caller)
  }
}
