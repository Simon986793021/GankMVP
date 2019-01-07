package com.smart.gankmvp.base

import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.MenuItem
import com.smart.gankmvp.R

abstract class BaseActivity<V, T : BasePresenter<V>> : AppCompatActivity() {

    private var mPresenter: T? = null
    protected var mAppBar: AppBarLayout? = null
    protected var mToolbar: Toolbar? = null
    private var mRefreshLayout: SwipeRefreshLayout? = null

    /**
     * 判断子Activity是否需要刷新功能
     *
     * @return false
     */

    fun isSetRefresh(): Boolean? {
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //允许为空，不是所有都要实现MVP模式
        if (createPresenter() != null) {
            mPresenter = createPresenter()
            mPresenter!!.attachView(this as V)
        }
        setContentView(provideContentViewId())//布局

        mAppBar = findViewById(R.id.app_bar_layout)
        mToolbar = this.findViewById(R.id.toolbar)
        if (mToolbar != null && mAppBar != null) {
            setSupportActionBar(mToolbar) //把Toolbar当做ActionBar给设置
            if (canBack()) {
                val actionBar = supportActionBar
                actionBar?.setDisplayHomeAsUpEnabled(true)//设置ActionBar一个返回箭头，主界面没有，次级界面有
            }
            if (Build.VERSION.SDK_INT >= 21) {
                mAppBar!!.setElevation(10.6f)//Z轴浮动
            }
        }

        if (this.isSetRefresh()!!) {
            setupSwipeRefresh()
        }
    }

    private fun setupSwipeRefresh() {
        mRefreshLayout = findViewById(R.id.swipe_refresh)
        if (mRefreshLayout != null) {
            mRefreshLayout!!.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2, R.color.refresh_progress_3
            )
            mRefreshLayout!!.setProgressViewOffset(
                true, 0, TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources.displayMetrics).toInt()
            )
            mRefreshLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { this.requestDataRefresh() })
        }
    }

    fun requestDataRefresh() {}


    fun setRefresh(requestDataRefresh: Boolean) {
        if (mRefreshLayout == null) {
            return
        }
        if (!requestDataRefresh) {
            mRefreshLayout!!.postDelayed({
                mRefreshLayout?.isRefreshing = false
            }, 1000)
        } else {
            mRefreshLayout!!.isRefreshing = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 此时android.R.id.home即为返回箭头
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    /**
     * 判断当前 Activity 是否允许返回
     * 主界面不允许返回，次级界面允许返回
     *
     * @return false
     */
    fun canBack(): Boolean {
        return false
    }

    protected abstract fun createPresenter(): T?

    protected abstract fun provideContentViewId(): Int //用于引入布局文件

}
