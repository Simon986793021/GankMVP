package com.smart.gankmvp.main.daily

import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.smart.gankmvp.base.BasePresenter
import com.smart.gankmvp.http.Api
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DailyFgPresenter(val context: Context) : BasePresenter<IDailyView>() {
    private lateinit var dailyView: IDailyView
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var next_pager: String
    private lateinit var has_more: String
    private var isLoadMore = false //是否加载更多
    private lateinit var timeLine: DailyTimeLine
    private lateinit var adapter: DailyListAdapter
    private var lastVisibleItem: Int = 0
    private val mHandler = Handler()
    fun getDailyTimeLine(number: String) {
        dailyView = this.getView()!!
        recyclerView = dailyView.getRecyclerView()
        linearLayoutManager = dailyView.getLayoutManager()

        Api.getDailyApiSingleton()!!.getDailyTimeLine(number)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ dailyTimeLine ->
                if (dailyTimeLine.meta.msg.equals("success")) {
                    disPlayDailyTimeLine(context, dailyTimeLine, recyclerView, dailyView)
                }
            }, ({ this.loadError(it) }))
    }

    private fun loadError(throwable: Throwable) {
        throwable.printStackTrace()
        dailyView.setDataRefresh(false)
        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show()
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
            if (dailyTimeLine.response.feeds == null) {
                adapter.updateLoadStatus(adapter.LOAD_NONE)
                dailyFgView.setDataRefresh(false)
                return
            } else {
                timeLine.response.feeds.addAll(dailyTimeLine.response.feeds)
            }
            adapter.notifyDataSetChanged()
        } else {
            timeLine = dailyTimeLine
            adapter = DailyListAdapter(context, timeLine.response)
            recyclerView.adapter = adapter
        }
        dailyFgView.setDataRefresh(false)
    }

    fun scrollRecycleView() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = linearLayoutManager
                        .findLastVisibleItemPosition()
                    if (linearLayoutManager.getItemCount() == 1) {
                        adapter.updateLoadStatus(adapter.LOAD_NONE)
                        return
                    }
                    if (lastVisibleItem + 1 == linearLayoutManager
                            .getItemCount()
                    ) {
                        adapter.updateLoadStatus(adapter.LOAD_PULL_TO)
                        if (has_more == "true") {
                            isLoadMore = true
                        }
                        adapter.updateLoadStatus(adapter.LOAD_MORE)
                        mHandler.postDelayed({ getDailyTimeLine(next_pager) }, 1000)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
            }
        })
    }

    override fun detachView() {
        super.detachView()
        mHandler.removeCallbacksAndMessages(null)
    }

}