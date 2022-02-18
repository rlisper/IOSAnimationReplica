package com.rlisper.iosanimationreplica.ui.main

import android.content.res.Resources
import android.view.View
import java.util.*

/**
 * Converts Pixel to DP.
 */
val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts DP to Pixel.
 */
val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Any.TAG: String
    get() = this.javaClass.simpleName