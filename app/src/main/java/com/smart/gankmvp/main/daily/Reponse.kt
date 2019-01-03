package com.smart.gankmvp.main.daily

data class Reponse(
    var banner: List<Daily>,
    var feeds: List<Daily>,
    var headLine: HeadLine,
    var options: Options,
    var has_more: String,
    var last_key: String
)