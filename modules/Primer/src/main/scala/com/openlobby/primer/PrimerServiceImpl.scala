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

package com.openlobby.primer

import com.openlobby.io.IOService
import com.openlobby.launcher.BundleLauncher
import java.io.File
import java.util.Collections
import java.util.LinkedList
import org.osgi.framework.Bundle
import org.osgi.framework.BundleContext
import org.osgi.framework.BundleException
import org.osgi.service.log.LogService

class PrimerServiceImpl extends PrimerService {
	@volatile private var logService: LogService = _
//	@volatile private val ioService: IOService = _
	@volatile private var context: BundleContext = _
	val modulesDir = "./module"

	def added(ioService: IOService) {
		ioService.loadConfig
		launchBundles(ioService)
	}

	def removed(ioService: IOService) {
	}

	@throws(classOf[BundleException])
	def launchBundles(ioService: IOService) {
		val modules: Array[String] = ioService.getConfigValues("modules")

		// Start each module
		modules.foreach {
			module =>
				val bundle = installBundle(module)

				try {
					bundle.start()
				} catch {
					case e: BundleException => logService.log(LogService.LOG_ERROR, "Bundle " + bundle.getSymbolicName + " failed to start.")
				}
		}
	}

	/**
	 * Locate the most recent version of a bundle and install it.
	 * @param artifactId artifactId of bundle.
	 */
	@throws(classOf[BundleException])
	private def installBundle(artifactId: String): Bundle = {
		val files = new File(modulesDir).listFiles

		val versions = new LinkedList[File]

		files.foreach {
			file =>
				if (file.getName.contains(artifactId)) versions.add(file)
		}

		if (versions.isEmpty) {
			throw new BundleException("[ERROR]: Unable to locate artifactId: " + artifactId)
		}

		Collections.sort(versions)

		logService.log(LogService.LOG_ERROR, versions.getFirst.toString)

		return context.installBundle("file:" + versions.getFirst)
	}

}
