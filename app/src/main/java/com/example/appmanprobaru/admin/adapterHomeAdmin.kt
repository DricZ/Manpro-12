package com.example.appmanprobaru.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R

class adapterHomeAdmin(private val listForum : ArrayList<HomeEvent>) : RecyclerView.Adapter<adapterHomeAdmin.ListViewHolder>(){

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _ivImage : ImageView = itemView.findViewById(R.id.IVImg)
        var _tvTitle : TextView = itemView.findViewById(R.id.TVTitle)
        var _tvDate : TextView = itemView.findViewById(R.id.TVDate)
        var _tvTime : TextView = itemView.findViewById(R.id.TVTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_event, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var HomeEvent = listForum[position]

        holder._ivImage.setImageResource(HomeEvent.img)
        holder._tvTitle.setText(HomeEvent.title)
        holder._tvDate.setText(HomeEvent.date)
        holder._tvTime.setText(HomeEvent.time)
    }

    override fun getItemCount(): Int {
        return listForum.size
    }
}