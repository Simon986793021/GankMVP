package com.smart.gankmvp.main.gank

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.smart.gankmvp.R
import com.smart.gankmvp.base.BaseFragment

class GankFragment : BaseFragment<IGankView, GankFgPresenter>(), IGankView {

    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var recyclerView: RecyclerView


    override fun initView(view: View) {
        gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView = view.findViewById(R.id.content_list)
        recyclerView.layoutManager=gridLayoutManager
    }

    override fun createPresenter(): GankFgPresenter {
        return GankFgPresenter(this.context!!)
    }

    override fun createViewLayoutId(): Int {
        return R.layout.fragment_gank
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDataRefresh(true)
        mPresenter.getGankData()
        mPresenter.scrollRecycleView()
    }

    override fun requestDataRefresh() {
        super.requestDataRefresh()
        setDataRefresh(true)
        mPresenter.getGankData()
    }

    override fun setDataRefresh(refresh: Boolean?) {
        setRefresh(refresh!!)
    }

    override fun getLayoutManager(): GridLayoutManager {
        return gridLayoutManager
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView
    }

}