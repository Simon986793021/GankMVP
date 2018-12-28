package com.smart.gankmvp.main.gank

import android.content.Context
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.smart.gankmvp.R
import com.smart.gankmvp.base.BasePresenter
import com.smartcentury.kcwork.http.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import rx.Observable
import rx.functions.Action1
import rx.functions.Func2
import rx.schedulers.Schedulers
import java.util.*

class GankFgPresenter(context: Context) : BasePresenter<IGankView>() {
    private lateinit var iGankView: IGankView
    private lateinit var recyclerview: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var gankListAdapter: GankListAdapter
    private var list: MutableList<GankBean>? = null
    private var page = 1
    private var lastVisibleItem: Int = 0
    private var isLoadMore = false // 是否加载过更多
    private val mHandler = Handler()


    fun getGankData() {
        iGankView = this!!.getView()!!
        if (iGankView != null) {
            recyclerview = iGankView.getRecyclerView()
            gridLayoutManager = iGankView.getLayoutManager()

            if (isLoadMore) {
                page = page + 1
            }

            io.reactivex.Observable.zip(
                Api.apiServer.getMeizhiData(page),
                Api.apiServer.getVideoData(page),
                Func2<T1, T2, R> { meizhi, video -> this.creatDesc(meizhi, video) }
        }
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { meizhi1 -> displayMeizhi(context  , meizhi1.getResults(), iGankView, recyclerview) },
                    Action1<Throwable> { this.loadError(it) })
        }
    }

    private fun loadError(throwable: Throwable) {
        throwable.printStackTrace()
        IGankView.setDataRefresh(false)
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show()
    }

    private fun displayMeizhi(
        context: Context,
        meiZhiList: List<Gank>?,
        gankFgView: IGankFgView,
        recyclerView: RecyclerView
    ) {
        if (isLoadMore) {
            if (meiZhiList == null) {
                gankFgView.setDataRefresh(false)
                return
            } else {
                list.addAll(meiZhiList)
            }
            adapter.notifyDataSetChanged()
        } else {
            list = meiZhiList
            adapter = GankListAdapter(context, list)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        gankFgView.setDataRefresh(false)
    }

    fun scrollRecycleView() {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = layoutManager
                        .findLastVisibleItemPosition()
                    if (layoutManager.getItemCount() == 1) {
                        return
                    }
                    if (lastVisibleItem + 1 == layoutManager
                            .getItemCount()
                    ) {
                        gankFgView.setDataRefresh(true)
                        isLoadMore = true
                        mHandler.postDelayed({ getGankData() }, 1000)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
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
    private fun getVideoDesc(publishedAt: Date, results: List<Gank>): String {
        var videoDesc = ""
        for (i in results.indices) {
            val video = results[i]
            if (video.getPublishedAt() == null) video.setPublishedAt(video.getCreatedAt())
            if (DateUtils.isSameDate(publishedAt, video.getPublishedAt())) {
                videoDesc = video.getDesc()
                break
            }
        }
        return videoDesc
    }


}