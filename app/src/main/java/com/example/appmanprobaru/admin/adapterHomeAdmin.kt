package com.example.appmanprobaru.admin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.car.ui.toolbar.`ToolbarControllerAdapterV1$ToolbarAdapterState$$ExternalSyntheticStaticInterfaceCall0`
import com.bumptech.glide.Glide
import com.example.appmanprobaru.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class adapterHomeAdmin(private val listForum : ArrayList<HomeEvent>) : RecyclerView.Adapter<adapterHomeAdmin.ListViewHolder>(){

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun clicked(pos: Int, id: String)
    }

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _ivImage : ImageView = itemView.findViewById(R.id.IVImg)
        var _tvTitle : TextView = itemView.findViewById(R.id.TVTitle)
        var _tvDate : TextView = itemView.findViewById(R.id.TVDate)
        var _tvTime : TextView = itemView.findViewById(R.id.TVTime)
        var _card : ConstraintLayout = itemView.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_event, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var HomeEvent = listForum[position]

//        holder._ivImage.setImageResource(HomeEvent.img)
        holder._tvTitle.setText(HomeEvent.title)
        holder._tvDate.setText(HomeEvent.date)
        holder._tvTime.setText(HomeEvent.time)
        holder._card.setOnClickListener {
            onItemClickCallback.clicked(position, HomeEvent.id)
        }


        val storage = Firebase.storage("gs://manpro-12.appspot.com")

        val storageRef = storage.reference

        // Create a reference with an initial file path and name
        val pathReference = storageRef.child("events/"+HomeEvent.img).downloadUrl

        pathReference.addOnSuccessListener { uri ->
            Glide.with(holder.itemView.context)
                .load(uri)
                .into(holder._ivImage)
        }

    }

    override fun getItemCount(): Int {
        return listForum.size
    }

    fun setOnItemClickCallback(onItemClickCallback: adapterHomeAdmin.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}