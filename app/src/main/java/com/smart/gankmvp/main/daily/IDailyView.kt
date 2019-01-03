package com.smart.gankmvp.main.daily

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

interface IDailyView {
   fun  getRecyclerView(): RecyclerView
    fun setDataRefresh(refresh:Boolean)
    fun getLayoutManager():LinearLayoutManager
}