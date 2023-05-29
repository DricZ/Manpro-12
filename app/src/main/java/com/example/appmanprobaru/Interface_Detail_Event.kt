package com.example.appmanprobaru

interface Interface_Detail_Event {
    fun passData( titleImage: Int, Name:String, desc:String, category:String, date:String, location:String, maxPeserta:String, img: String)

    fun passModel(model: home_page_recyclerView_Data)
    fun passCategory(kategori: String)

    fun passDetail(model:home_page_recyclerView_Data)

}