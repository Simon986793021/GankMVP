package com.smart.gankmvp.main.daily

data class Reponse(
    var banner: MutableList <Daily>,
    var feeds: MutableList <Daily>,
    var headLine: Daily,
    var options: Options,
    var has_more: String,
    var last_key: String
) {

    fun getListSize(): Int {
        var size = 0
        if (banner != null && feeds != null) {
            size = feeds.size + 1
        }
        if (headLine.post != null) {
            size = size + 1
        }
        return size
    }
}