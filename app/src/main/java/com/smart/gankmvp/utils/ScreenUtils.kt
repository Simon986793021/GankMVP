package com.smart.gankmvp.utils

import android.content.Context

class ScreenUtils private constructor(context: Context) {
    private val context: Context

    val scal: Int
        get() = 100 * screenWidth / 480

    val screenDensityDpi: Int
        get() = context.resources.displayMetrics.densityDpi

    val screenHeight: Int
        get() = context.resources.displayMetrics.heightPixels

    val screenWidth: Int
        get() = context.resources.displayMetrics.widthPixels


    val xdpi: Float
        get() = context.resources.displayMetrics.xdpi

    val ydpi: Float
        get() = context.resources.displayMetrics.ydpi

    init {
        this.context = context.applicationContext
    }

    fun dip2px(f: Float): Int {
        return (0.5 + (f * getDensity(context)).toDouble()).toInt()
    }

    fun dip2px(i: Int): Int {
        return (0.5 + (getDensity(context) * i.toFloat()).toDouble()).toInt()
    }

    fun get480Height(i: Int): Int {
        return i * screenWidth / 480
    }

    fun getDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    fun px2dip(f: Float): Int {
        val f1 = getDensity(context)
        return ((f.toDouble() - 0.5) / f1.toDouble()).toInt()
    }

    fun px2dip(i: Int): Int {
        val f = getDensity(context)
        return ((i.toDouble() - 0.5) / f.toDouble()).toInt()
    }

    companion object {

        private var mScreenTools: ScreenUtils? = null

        fun instance(context: Context): ScreenUtils {
            if (mScreenTools == null)
                mScreenTools = ScreenUtils(context)
            return mScreenTools as ScreenUtils
        }
    }

}
