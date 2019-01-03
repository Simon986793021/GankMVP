package com.smart.gankmvp.base

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

abstract class BaseRecyclerViewAdapter<T,R: BaseViewHolder>(val layout:Int) : BaseQuickAdapter<T, R>(layout)