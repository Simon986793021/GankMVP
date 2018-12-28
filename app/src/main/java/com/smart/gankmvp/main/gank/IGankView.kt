package com.smart.gankmvp.main.gank

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.webkit.WebView
import android.widget.ProgressBar

interface IGankView{
    abstract fun setDataRefresh(refresh: Boolean?)
    abstract fun getLayoutManager(): GridLayoutManager
    abstract fun getRecyclerView(): RecyclerView
}