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

import java.util.HashMap
import org.apache.felix.framework.Felix
import org.osgi.framework.launch.Framework

class EmbeddedFramework {
  var framework : Framework = initFramework
  
  private def initFramework:Framework = {
    val config = new HashMap
    val felix = new Felix(config)
    felix.start
    
    return felix
  }
}
