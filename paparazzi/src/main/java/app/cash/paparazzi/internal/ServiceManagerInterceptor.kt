package app.cash.paparazzi.internal

import android.os.IBinder

object ServiceManagerInterceptor {
  @JvmStatic
  fun intercept(
    name: String
  ): IBinder? {
    if (name == "display") {
      return PaparazziDisplayManager()
    }

    return null
  }
}