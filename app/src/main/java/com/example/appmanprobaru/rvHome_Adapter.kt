package com.example.appmanprobaru

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class rvHome_Adapter(private val home_page_recyclerView_Data: ArrayList<home_page_recyclerView_Data>): RecyclerView.Adapter<rvHome_Adapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_box_layout,
        parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem =  home_page_recyclerView_Data[position]
//        holder.image.setImageResource(currentItem.titleImage)
        holder.title.text = currentItem.Name
    }

    override fun getItemCount(): Int {
        return home_page_recyclerView_Data.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.rvHome_image)
        val title: TextView = itemView.findViewById(R.id.rvHome_Title)


    }
}