package com.smart.gankmvp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smart.gankmvp.R

abstract class BaseFragment<V, T : BasePresenter<V>> : Fragment() {
    protected lateinit var mPresenter: T
    private lateinit var mRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        mPresenter.attachView(this as V)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(createViewLayoutId(), container, false)
        initView(view)
        setupSwipeRefresh(view)
        return view
    }

    private fun setupSwipeRefresh(rootView: View) {
        mRefreshLayout = rootView.findViewById(R.id.swipe_refresh)
        if (mRefreshLayout != null) {
            mRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2, R.color.refresh_progress_3
            )
            mRefreshLayout.setProgressViewOffset(
                true, 0, TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources.displayMetrics).toInt()
            )
            mRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { this.requestDataRefresh() })
        }
    }

    private fun requestDataRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    protected abstract fun initView(view: View)
    protected abstract fun createPresenter(): T
    protected abstract fun createViewLayoutId(): Int
}