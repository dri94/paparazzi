package app.cash.paparazzi.internal

import android.view.IWindowSession
import android.view.IWindowSessionCallback

object WindowManagerInterceptor {
  @JvmStatic
  fun intercept(
    callback: IWindowSessionCallback
  ): IWindowSession? {
    return PaparazziWindowSession()
  }
}
