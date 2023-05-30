package com.example.appmanprobaru

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.admin.adapterEventAdmin

class profile_event_rv_adapter(private val profle_page_data:ArrayList<profile_page_event_recyclerView_Data>):
    RecyclerView.Adapter<profile_event_rv_adapter.ListViewHolder>() {


    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var image : ImageView = itemView.findViewById(R.id.profile_layout_gambar)
        var text : TextView = itemView.findViewById(R.id.profile_layout_judul)

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.profile_page_event_rv_layout, parent, false)

        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profle_page_data.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val CurrentItem = profle_page_data[position]

        holder.text.text = CurrentItem.nama
        holder.text.text = CurrentItem.nama
    }


}