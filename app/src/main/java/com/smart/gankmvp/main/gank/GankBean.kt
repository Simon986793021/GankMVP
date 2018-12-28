package com.smart.gankmvp.main.gank

import java.util.*

data class GankBean(
    var url: String,
    var type: String,
    var desc: String,
    var who: String,
    var used: Boolean,
    var createdAt: Date,
    var updatedAt: Date,
    var publishedAt: Date
)