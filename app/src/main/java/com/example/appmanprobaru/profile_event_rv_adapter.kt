package com.example.appmanprobaru

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class profile_event_rv_adapter(private val profle_page_data:ArrayList<profile_page_event_recyclerView_Data>):
    RecyclerView.Adapter<profile_event_rv_adapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListenerProfile


    interface OnItemClickListenerProfile{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:OnItemClickListenerProfile){
        this.mListener = listener
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_page_event_rv_layout,
            parent, false)
        return profile_event_rv_adapter.ViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return profle_page_data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val CurrentItem = profle_page_data[position]

        holder.nama.text = CurrentItem.nama
    }

    class ViewHolder(itemView: View, listener: OnItemClickListenerProfile): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.profile_layout_gambar)
        val nama: TextView =  itemView.findViewById(R.id.profile_layout_judul)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }
}