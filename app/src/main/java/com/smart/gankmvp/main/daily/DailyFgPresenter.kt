package com.smart.gankmvp.main.daily

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.smart.gankmvp.base.BasePresenter
import com.smart.gankmvp.http.Api
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DailyFgPresenter : BasePresenter<IDailyView>() {
    private lateinit var context: Context
    private lateinit var dailyView: IDailyView
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var next_pager: String
    private lateinit var has_more: String
    private val isLoadMore = false //是否加载更多
    private lateinit var timeLine:DailyTimeLine
    private lateinit var adapter DailyListAdapter

    fun getDailyTimeLine(number: String) {
        dailyView = this.getView()!!
        recyclerView = dailyView.getRecyclerView()
        linearLayoutManager = dailyView.getLayoutManager()
        Api.getDailyApiSingleton()?.getDailyTimeLine(number)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(dailyTimeLine->{
            if (dailyTimeLine.getMe) {
                disPlayDailyTimeLine(context, dailyTimeLine, recyclerView, dailyView)
            }
        })
    }

    private fun disPlayDailyTimeLine(
        context: Context,
        dailyTimeLine: DailyTimeLine,
        recyclerView: RecyclerView,
        dailyFgView: IDailyView
    ) {
        next_pager = dailyTimeLine.response.last_key
        has_more = dailyTimeLine.response.has_more
        if (isLoadMore) {
            if (dailyTimeLine.response.feeds== null) {
                adapter.updateLoadStatus(adapter.LOAD_NONE)
                dailyFgView.setDataRefresh(false)
                return
            } else {
                timeLine.getResponse().getFeeds().addAll(dailyTimeLine.getResponse().getFeeds())
            }
            adapter.notifyDataSetChanged()
        } else {
            timeLine = dailyTimeLine
            adapter = DailyListAdapter(context, timeLine.getResponse())
            recyclerView.adapter = adapter
        }
        dailyFgView.setDataRefresh(false)
    }

    override fun detachView() {
        super.detachView()

    }

}