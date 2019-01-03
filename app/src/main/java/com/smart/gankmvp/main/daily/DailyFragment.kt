package com.smart.gankmvp.main.daily

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.smart.gankmvp.R
import com.smart.gankmvp.base.BaseFragment

class DailyFragment : BaseFragment<IDailyView, DailyFgPresenter>(), IDailyView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun initView(view: View) {
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.content_list)
    }

    override fun createPresenter(): DailyFgPresenter {
        return DailyFgPresenter()
    }

    override fun createViewLayoutId(): Int {
        return R.layout.fragment_daily
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.getDailyTimeLine("0")
    }

    override fun requestDataRefresh() {
        super.requestDataRefresh()
        setDataRefresh(true)
        mPresenter.getDailyTimeLine("0")
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }

    override fun setDataRefresh(refresh: Boolean) {
        setRefresh(refresh)
    }

    override fun getLayoutManager(): LinearLayoutManager {
        return linearLayoutManager
    }

}