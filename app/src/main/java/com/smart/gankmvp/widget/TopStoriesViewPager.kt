package com.smart.gankmvp.widget

import android.content.Context
import android.os.Handler
import android.os.Message
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.smart.gankmvp.main.daily.TopStories
import com.smart.gankmvp.utils.ScreenUtils
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class TopStoriesViewPager (context: Context): RelativeLayout(context) {

    private var viewPager: ViewPager? = null
    private var listenner: ViewPagerClickListener? = null
    private var currentItem = 0// ImageViewpager当前页面的index
    private lateinit var images: ArrayList<ImageView>
    // 执行周期性或定时任务
    private var mScheduledExecutorService: ScheduledExecutorService? = null
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            viewPager!!.currentItem = currentItem
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
    }


   init {
       setView()
   }

    private fun setView() {
        viewPager = ViewPager(context!!)
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        viewPager!!.layoutParams = params

        val dotLayout = LinearLayout(context)
        val dotParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        dotParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        dotParams.setMargins(0, 0, 0, ScreenUtils.instance(context!!).dip2px(10))
        dotLayout.layoutParams = dotParams
        dotLayout.gravity = Gravity.CENTER_HORIZONTAL

        this.addView(viewPager)
        this.addView(dotLayout)

    }

    fun init(
        items: List<TopStories>, tv: TextView,
        clickListenner: ViewPagerClickListener
    ) {
        this.listenner = clickListenner
        images = ArrayList()

        for (i in items.indices) {
            val item = items[i]
            val mImageView = ImageView(
                context
            )
            val layoutParams = ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            mImageView.layoutParams = layoutParams
            mImageView.setOnClickListener { v ->
                if (null != listenner) {
                    listenner!!.onClick(item)
                }
            }

            // 得到屏幕的宽度
            val screenUtil = ScreenUtils.instance(this.context!!)
            val width = screenUtil.screenWidth

            Glide.with(context!!).load(item.image)
                .into(mImageView)
            images.add(mImageView)
        }

        viewPager!!.adapter = MyPagerAdapter(images)
        tv.setText(items[0].title)
        viewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                tv.setText(items[position].title)
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

            override fun onPageScrollStateChanged(arg0: Int) {}
        })

    }

    /**
     * 开启定时任务
     */
    fun startAutoRun() {
        mScheduledExecutorService = Executors
            .newSingleThreadScheduledExecutor()
        /**循环
         * 创建并执行一个在给定初始延迟后首次启用的定期操作， 后续操作具有给定的周期；也就是将在 initialDelay 后开始执行，
         * 然后在initialDelay+period 后执行，接着在 initialDelay + 2 * period 后执行， 依此类推
         */
        mScheduledExecutorService!!.scheduleAtFixedRate(
            ViewPagerTask(), 5,
            5, TimeUnit.SECONDS
        )
    }

    /**
     * 关闭定时任务
     */
    fun stopAutoRun() {
        if (mScheduledExecutorService != null) {
            mScheduledExecutorService!!.shutdown()
        }
    }

    /**
     * 发消息改变页数
     *
     * @author sujingbo
     */
    internal inner class ViewPagerTask : Runnable {

        override fun run() {
            if (images != null) {
                currentItem = (currentItem + 1) % images!!.size
                mHandler.obtainMessage().sendToTarget()
            }
        }
    }

    fun getResourceId(resourceName: String): Int {
        return context!!.resources.getIdentifier(
            resourceName,
            "drawable", context!!.packageName
        )
    }

    inner class MyPagerAdapter(private val views: ArrayList<ImageView>) : PagerAdapter() {

        override fun getCount(): Int {
            // return Integer.MAX_VALUE;
            return views.size
        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            if (views.size > 0 && views[position % views.size].parent != null) {
                (views[position % views.size].parent as ViewPager)
                    .removeView(views[position % views.size])
            }
            try {
                container.addView(
                    views[position % views.size], 0
                )
            } catch (e: Exception) {
            }

            return views[position % views.size]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(views[position % views.size])
        }

    }

    // 点击事件监听器接口
    interface ViewPagerClickListener {
        /**
         * item点击事件监听
         */
        fun onClick(item: TopStories)
    }

}