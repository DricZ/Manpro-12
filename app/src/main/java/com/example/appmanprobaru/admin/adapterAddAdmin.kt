package com.example.appmanprobaru.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R

class adapterAddAdmin (private val listForum : ArrayList<people>) : RecyclerView.Adapter<adapterAddAdmin.ListViewHolder>(){

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _tvName : TextView = itemView.findViewById(R.id.tvName)
        var _btnAdd : Button = itemView.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_people, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var people = listForum[position]

        holder._tvName.setText(people.name)
        holder._btnAdd.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return listForum.size
    }
}