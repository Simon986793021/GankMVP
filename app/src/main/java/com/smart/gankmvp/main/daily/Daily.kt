package com.smart.gankmvp.main.daily

data class Daily (var image:String,
                  var type:Int,
                  var post:Post,
                  var headline_genre:String,
                  var list:List<HeadLine>)