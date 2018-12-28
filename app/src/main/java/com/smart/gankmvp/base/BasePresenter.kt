package com.smart.gankmvp.base

import java.lang.ref.Reference
import java.lang.ref.WeakReference

abstract class BasePresenter<V> {
    protected lateinit var mViewRef: Reference<V>
    fun attachView(v: V) {
        mViewRef = WeakReference<V>(v)
    }

    fun getView(): V? {
        return mViewRef.get()
    }

    fun detachView() {
        if (mViewRef != null) {
            mViewRef.clear()
        }
    }
}