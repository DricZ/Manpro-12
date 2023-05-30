package com.example.appmanprobaru.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R

class adapterEventAdmin (private val listForum : ArrayList<HomeEvent>) : RecyclerView.Adapter<adapterEventAdmin.ListViewHolder>(){

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(pos: Int)
    }
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var tvName : TextView = itemView.findViewById(R.id.tvName)
        var tvDate : TextView = itemView.findViewById(R.id.tvDate)
        var tvTime : TextView = itemView.findViewById(R.id.tvTime)
        var _btnDelete : Button = itemView.findViewById<Button>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_event_admin, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var HomeEvent = listForum[position]

        holder.tvName.setText(HomeEvent.title)
        holder.tvDate.setText(HomeEvent.date)
        holder.tvTime.setText(HomeEvent.time)
        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(position)
        }
    }

    override fun getItemCount(): Int {
        return listForum.size
    }

    fun setOnItemClickCallback(onItemClickCallback: adapterEventAdmin.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}