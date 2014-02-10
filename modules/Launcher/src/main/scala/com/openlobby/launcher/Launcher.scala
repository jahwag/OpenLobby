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
import java.util.ServiceLoader
import javax.script.ScriptEngineManager
import org.osgi.framework.{Constants, BundleContext, BundleException}
import org.osgi.framework.launch.FrameworkFactory

object Launcher {
	final val context: BundleContext = startFramework

	def getContext: BundleContext = context

	def init {
		try {
			BundleLauncher.init(context)
		} catch {
			case e: BundleException => e.printStackTrace
		}
	}

	private def startFramework: BundleContext = {
		val frameworkFactory = ServiceLoader.load(classOf[FrameworkFactory]).iterator().next();

		val config = new HashMap[String, String];
		config.put(Constants.FRAMEWORK_STORAGE_CLEAN, "true")
		config.put("felix.embedded.execution", "true")
		config.put("felix.shutdown.hook", "true")
		val framework = frameworkFactory.newFramework(config);
		framework.start();

		// Add shutdown hook
		Runtime.getRuntime.addShutdownHook(
			new Thread {
				override def run {
					println("Stopping OpenLobby.")

					try {
						framework.waitForStop(0);
					} finally {
						System.exit(0);
					}

				}
			})



		return framework.getBundleContext
	}

}
