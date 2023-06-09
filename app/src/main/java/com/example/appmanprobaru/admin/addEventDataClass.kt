package com.example.appmanprobaru.admin

import com.google.firebase.Timestamp

data class addEventDataClass(
    val Name:String,
    val desc:String,
    val category:String,
    val date:Timestamp,
    val location:String,
    val maxPeserta:String,
    val kategoriPeserta:String,
    val imgloc:String,
    val status:Boolean,
    val adminPickup:Boolean
)
