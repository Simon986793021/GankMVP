package com.smart.gankmvp.main.daily

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.smart.gankmvp.R
import com.smart.gankmvp.utils.ScreenUtils
import com.smart.gankmvp.widget.TopStoriesViewPager
import java.util.ArrayList

class DailyListAdapter(val context: Context, private val response: Reponse) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var status = 1
    private var feed_position: Int = 0
    private val TYPE_TOP = -1
    private val TYPE_FOOTER = -2
    private val TYPE_HEADLINE = -3
    val LOAD_MORE = 0
    val LOAD_PULL_TO = 1
    val LOAD_NONE = 2
    val LOAD_END = 3


    override fun getItemViewType(position: Int): Int {
        if (response.banner != null) {
            if (position == 0) {
                return TYPE_TOP
            } else if (response.headLine.post != null) {
                if (position == 1) {
                    return TYPE_HEADLINE
                } else if (position + 1 == itemCount) {
                    return TYPE_FOOTER
                } else {
                    feed_position = 2
                    return position
                }
            } else if (position + 1 == itemCount) {
                return TYPE_FOOTER
            } else {
                feed_position = 1
                return position
            }
        } else return if (position + 1 == itemCount) {
            TYPE_FOOTER
        } else {
            position
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is TopStoriesViewHolder) {
            holder.vp_top_stories!!.startAutoRun()
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        if (holder is TopStoriesViewHolder) {
            holder.vp_top_stories!!.stopAutoRun()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_TOP) {
            val rootView = View.inflate(parent.context, R.layout.item_zhihu_top_stories, null)
            return TopStoriesViewHolder(rootView)
        } else if (viewType == TYPE_HEADLINE) {
            val rootView = View.inflate(parent.context, R.layout.item_daily_headline, null)
            return HeadlineViewHolder(rootView)
        } else if (viewType == TYPE_FOOTER) {
            val view = View.inflate(parent.context, R.layout.activity_view_footer, null)
            return FooterViewHolder(view)
        } else {
            val daily = response.feeds.get(viewType - feed_position)
            when (daily.type) {
                0 -> {
                    val rootView = View.inflate(parent.context, R.layout.item_daily_feed_0, null)
                    return Feed_0_ViewHolder(rootView)
                }
                1 -> {
                    val rootView = View.inflate(parent.context, R.layout.item_daily_feed_1, null)
                    return Feed_1_ViewHolder(rootView)
                }
                2 -> {
                    val rootView = View.inflate(parent.context, R.layout.item_daily_feed_0, null)
                    return Feed_0_ViewHolder(rootView)
                }
                else -> {
                    val rootView = View.inflate(parent.context, R.layout.item_empty, null)
                    return EmptyViewHolder(rootView)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterViewHolder) {
            holder.bindItem()
        } else if (holder is TopStoriesViewHolder) {
            holder.bindItem(response.banner)
        } else if (holder is HeadlineViewHolder) {
            holder.bindItem(response.headLine)
        } else if (holder is Feed_1_ViewHolder) {
            val daily = response.feeds.get(position - feed_position)
            if (daily.type === 1) {
                holder.bindItem(daily)
            }
        } else if (holder is Feed_0_ViewHolder) {
            val daily = response.feeds.get(position - feed_position)
            if (daily.type == 0) {
                holder.bindItem(daily)
            } else if (daily.type == 2) {
                holder.bindItem(daily)
            }
        } else if (holder is EmptyViewHolder) {

        }
    }

    override fun getItemCount(): Int {
        return response.getListSize() + 1
    }

    /**
     * type = Empty
     */
    internal inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * footer view
     */
    internal inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_load_prompt: TextView? = null
        var progress: ProgressBar? = null

        init {
            val params = LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtils.instance(context).dip2px(40)
            )
            itemView.layoutParams = params
        }

        fun bindItem() {
            when (status) {
                LOAD_MORE -> {
                    progress!!.visibility = View.VISIBLE
                    tv_load_prompt!!.text = "正在加载..."
                    itemView.visibility = View.VISIBLE
                }
                LOAD_PULL_TO -> {
                    progress!!.visibility = View.GONE
                    tv_load_prompt!!.text = "上拉加载更多"
                    itemView.visibility = View.VISIBLE
                }
                LOAD_NONE -> {
                    println("LOAD_NONE----")
                    progress!!.visibility = View.GONE
                    tv_load_prompt!!.text = "已无更多加载"
                }
                LOAD_END -> itemView.visibility = View.GONE
                else -> {
                }
            }
        }
    }


    /**
     * TopStories
     */
    internal inner class TopStoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var vp_top_stories: TopStoriesViewPager? = null
        var tv_top_title: TextView? = null
        var tv_tag: TextView? = null

        init {
            tv_tag!!.visibility = View.GONE
        }

        fun bindItem(banners: List<Daily>) {

            val topList = ArrayList<TopStories>()
//            for (d in banners) {
//                val t = TopStories()
//                t.image = (d.image)
//                t.title = (d.post.title)
//                t.url = (d.post.appview)
//                topList.add(t)
//            }

//            vp_top_stories!!.init(topList, tv_top_title, { item ->
//                val intent = GankWebActivity.newIntent(context, item.getUrl())
//                context.startActivity(intent)
//            }
//            )
        }
    }

    /**
     * headline
     */
    internal inner class HeadlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_headline_1: TextView? = null
        var tv_headline_2: TextView? = null
        var tv_headline_3: TextView? = null
        var card_headline: CardView? = null


        fun bindItem(daily: Daily) {
            val headLines = daily.list
            tv_headline_1!!.setText(headLines.get(0).description)
            tv_headline_2!!.setText(headLines.get(1).description)
            tv_headline_3!!.setText(headLines.get(2).description)

//            card_headline!!.setOnClickListener { v ->
//                val intent = GankWebActivity.newIntent(context, daily.post.appview)
//                context.startActivity(intent)
        }
    }

    /**
     * feed_0
     */
    internal inner class Feed_1_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_feed_1_title: TextView? = null
        var tv_feed_1_type: TextView? = null
        var iv_feed_1_type_icon: ImageView? = null
        var iv_feed_1_icon: ImageView? = null
        var card_feed_1: CardView? = null

        init {

            val screenUtil = ScreenUtils.instance(context)
            card_feed_1!!.layoutParams =
                    LinearLayout.LayoutParams(screenUtil.screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        fun bindItem(daily: Daily) {
            tv_feed_1_title!!.text = daily.post.title
            tv_feed_1_type!!.text = daily.post.category.title
            Glide.with(context).load(daily.post.category.image_lab)
                .into(this.iv_feed_1_type_icon!!)
            Glide.with(context).load(daily.image).into(this.iv_feed_1_icon!!)

//            card_feed_1!!.setOnClickListener { v ->
//                val intent = GankWebActivity.newIntent(context, daily.getPost().getAppview())
//                context.startActivity(intent)
//            }
        }
    }

    /**
     * feed_1
     */
    internal inner class Feed_0_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_feed_0_icon: ImageView? = null
        var tv_feed_0_title: TextView? = null
        var tv_feed_0_desc: TextView? = null
        var iv_feed_0_type: ImageView? = null
        var tv_Feed_0_type: TextView? = null
        var card_layout: CardView? = null

        init {

            val screenUtil = ScreenUtils.instance(context)
            card_layout!!.layoutParams =
                    LinearLayout.LayoutParams(screenUtil.screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        fun bindItem(daily: Daily) {
            tv_feed_0_title!!.text = daily.post.title
            tv_feed_0_desc!!.text = daily.post.description
            tv_Feed_0_type!!.text = daily.post.category.title
            Glide.with(context).load(daily.image).into(this.iv_feed_0_icon!!)
            if (daily.type == 0) {
                Glide.with(context).load(R.drawable.feed_0_icon).into(this.iv_feed_0_type!!)
//                card_layout!!.setOnClickListener { v ->
//                    val intent = DailyFeedActivity.newIntent(
//                        context,
//                        daily.getPost().getId(),
//                        daily.getPost().getDescription(),
//                        daily.getPost().getTitle(),
//                        daily.getImage()
//                    )
//                    context.startActivity(intent)
//                }
            } else if (daily.type== 2) {
                Glide.with(context).load(R.drawable.feed_1_icon).into(this.iv_feed_0_type!!)
                card_layout!!.setOnClickListener { v ->
//                    val intent = GankWebActivity.newIntent(context, daily.getPost().getAppview())
//                    context.startActivity(intent)
                }
            }


        }
    }

    // change recycler state
    fun updateLoadStatus(status: Int) {
        this.status = status
        notifyDataSetChanged()
    }

    companion object {
        private val TYPE_TOP = -1
        private val TYPE_FOOTER = -2
        private val TYPE_HEADLINE = -3
        val LOAD_MORE = 0
        val LOAD_PULL_TO = 1
        val LOAD_NONE = 2
        val LOAD_END = 3
    }
}
