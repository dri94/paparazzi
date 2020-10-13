/*
 * Copyright (C) 2019 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.cash.paparazzi

import java.io.File

data class Environment(
  val platformDir: String,
  val appTestDir: String,
  val resDir: String,
  val assetsDir: String,
  val packageName: String,
  val compileSdkVersion: Int)

@Suppress("unused")
fun androidHome() = System.getenv("ANDROID_SDK_ROOT")
    ?: System.getenv("ANDROID_HOME")
    ?: "${System.getProperty("user.home")}/Library/Android/sdk"

fun detectEnvironment(): Environment {
  checkInstalledJvm()

  val resourcesFile = File(System.getProperty("paparazzi.test.resources"))
  val configLines = resourcesFile.readLines()
  return Environment(
      platformDir = configLines[3],
      appTestDir = System.getProperty("user.dir"),
      resDir = configLines[1],
      assetsDir = configLines[4],
      packageName = configLines[0],
      compileSdkVersion = configLines[2].toInt()
  )
}

private fun checkInstalledJvm() {
  val jvmVendor = System.getProperty("java.vendor") ?: return // we tried...
  val jvmVersion = Runtime.version()

  if (jvmVendor.startsWith("Oracle") || jvmVersion.feature() < 11) {
    throw IllegalStateException(
        "Unsupported JRE detected! Please install and run Paparazzi test suites on OpenJDK version 11 or greater."
    )
  }
}
