package com.smart.gankmvp.main.gank

import android.content.Context
import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.smart.gankmvp.R
import com.smart.gankmvp.base.BaseRecyclerViewAdapter

class GankListAdapter(val context: Context) : BaseRecyclerViewAdapter<GankBean,GankListAdapter.GankMeiZhiViewHolder>(R.layout.item_gank_meizi){
    override fun convert(helper: GankMeiZhiViewHolder?, item: GankBean?) {
        if (helper != null) {
            helper.titleTextView.text = item!!.desc
            Glide.with(context).load(item.url).into(helper.meiziIv)
        }
    }

    inner class GankMeiZhiViewHolder(itemView:View) :BaseViewHolder(itemView){
        val cardView: CardView =itemView.findViewById(R.id.card_meizhi)
        val meiziIv:ImageView=itemView.findViewById(R.id.iv_meizhi)
        val titleTextView:TextView=itemView.findViewById(R.id.tv_meizhi_title)
    }



}