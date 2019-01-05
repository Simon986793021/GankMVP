package com.smart.gankmvp.main.gank

import android.content.Context
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.smart.gankmvp.base.BasePresenter
import com.smart.gankmvp.http.Api
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class GankFgPresenter(val context: Context) : BasePresenter<IGankView>() {
    private lateinit var iGankView: IGankView
    private lateinit var recyclerview: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var gankListAdapter: GankListAdapter
    private var list: ArrayList<GankBean>? = null
    private var page = 1
    private var lastVisibleItem: Int = 0
    private var isLoadMore = false // 是否加载过更多
    private val mHandler = Handler()


    fun getGankData() {
        iGankView = this.getView()!!
        if (iGankView != null) {
            recyclerview = iGankView.getRecyclerView()
            gridLayoutManager = iGankView.getLayoutManager()

            if (isLoadMore) {
                page = page + 1
            }

            Observable.zip(
                Api.getGankApiSingleton()!!.getMeizhiData(page),
                Api.getGankApiSingleton()!!.getVideoData(page)
            ) { meizhi, video -> this.creatDesc(meizhi as MeiZiBean, video as VideoBean) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { meizhi1 -> displayMeizhi(context, meizhi1.results, iGankView, recyclerview) },
                    { this.loadError(it) })
        }
    }

    private fun loadError(throwable: Throwable) {
        throwable.printStackTrace()
        iGankView.setDataRefresh(false)
        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show()
    }

    private fun displayMeizhi(
        context: Context,
        meiZhiList: ArrayList<GankBean>?,
        gankFgView: IGankView,
        recyclerView: RecyclerView
    ) {
        Log.i("SIMON",meiZhiList?.toString())
        if (isLoadMore) {
            if (meiZhiList == null) {
                gankFgView.setDataRefresh(false)
                return
            } else {
                list!!.addAll(meiZhiList)
            }
            gankListAdapter.addData(list!!)
           // gankListAdapter.notifyDataSetChanged()
        } else {
            list = meiZhiList
            gankListAdapter = GankListAdapter(context)
            recyclerView.adapter = gankListAdapter
            gankListAdapter.setNewData(list)
        }
        gankFgView.setDataRefresh(false)
    }

    fun scrollRecycleView() {
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = gridLayoutManager
                        .findLastVisibleItemPosition()
                    if (gridLayoutManager.getItemCount() == 1) {
                        return
                    }
                    if (lastVisibleItem + 1 == gridLayoutManager
                            .getItemCount()
                    ) {
                        iGankView.setDataRefresh(true)
                        isLoadMore = true
                        mHandler.postDelayed({ getGankData() }, 1000)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
            }
        })
    }


    /**
     * MeiZhi = list , gankmeizhi = 福利
     *
     * @param meizhi list
     * @param video  list
     * @return
     */
    private fun creatDesc(meizhi: MeiZiBean, video: VideoBean): MeiZiBean {
        for (gankmeizhi in meizhi.results) {
            gankmeizhi.desc = gankmeizhi.desc + " " +
                    getVideoDesc(gankmeizhi.publishedAt, video.results)
        }
        return meizhi
    }

    //匹配同一天的福利描述和视频描述
    private fun getVideoDesc(publishedAt: Date, results: List<GankBean>): String {
        var videoDesc = ""
        for (i in results.indices) {
            val video = results[i]
            video.publishedAt = (video.createdAt)
            if (com.smart.gankmvp.utils.DateUtils.isSameDate(publishedAt, video.publishedAt)) {
                videoDesc = video.desc
                break
            }
        }
        return videoDesc
    }


}