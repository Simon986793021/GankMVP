package com.smart.gankmvp.base

import java.lang.ref.Reference
import java.lang.ref.WeakReference

abstract class BasePresenter<V> {
    private lateinit var mViewRef: Reference<V>
    fun attachView(v: V) {
        mViewRef = WeakReference<V>(v)
    }

    fun getView(): V? {
        return mViewRef.get()
    }

  open  fun detachView() {
        if (mViewRef != null) {
            mViewRef.clear()
        }
    }
}