package com.smart.gankmvp.main.gank

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.smart.gankmvp.R
import com.smart.gankmvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_gank.*

class GankFragment : BaseFragment<IGankView, GankFgPresenter>(),IGankView{

    private lateinit var gridLayoutManager: GridLayoutManager

    override fun initView(view: View) {
        gridLayoutManager= GridLayoutManager(context,2)
        content_list.layoutManager=gridLayoutManager
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

    override fun setDataRefresh(refresh: Boolean?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutManager(): GridLayoutManager {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecyclerView(): RecyclerView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}