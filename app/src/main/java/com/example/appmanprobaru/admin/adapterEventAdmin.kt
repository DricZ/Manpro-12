package com.example.appmanprobaru.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R

class adapterEventAdmin (private val listForum : ArrayList<HomeEvent>) : RecyclerView.Adapter<adapterEventAdmin.ListViewHolder>(){

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _tvTitle : TextView = itemView.findViewById(R.id.TVTitle)
        var _tvDate : TextView = itemView.findViewById(R.id.TVDate)
        var _tvTime : TextView = itemView.findViewById(R.id.TVTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_event_admin, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var HomeEvent = listForum[position]

        holder._tvTitle.setText(HomeEvent.title)
        holder._tvDate.setText(HomeEvent.date)
        holder._tvTime.setText(HomeEvent.time)
    }

    override fun getItemCount(): Int {
        return listForum.size
    }
}