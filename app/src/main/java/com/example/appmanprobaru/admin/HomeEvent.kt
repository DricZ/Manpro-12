package com.example.appmanprobaru.admin

import com.google.firebase.Timestamp

data class HomeEvent(
    var id:String,
    var img:String,
    var title:String,
    var date:String,
    var time:String,
    var timestamp: Timestamp,
    var status: Boolean
)
