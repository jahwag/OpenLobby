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
 * Singleton Executors ThreadPool wrapper.
 * 
 * Allows blocking and non-blocking tasks.
 * 
 * All modules should use this. Threads should not be allocated in any other way.
 * Failure to comply will not lead to catastrophic results, however, it may 
 * interfere with performance goals.
 */

import scala.actors.threadpool.Executors

object SmartThreadPool {
  def tp = Executors.newCachedThreadPool
  
  /**
   * Executes task immediately (blocking).
   * @return typed return value.
   */
  def runImmediate(task: Runnable, clazz : Any):Any = {
    return tp.submit(task).get
  }
  
  /**
   * Executes task later.
   * @return Non-blocking return value. Call get() to get return value.
   */
  def runLater(task: Runnable) = {
    tp.submit(task)
  }
  
}
