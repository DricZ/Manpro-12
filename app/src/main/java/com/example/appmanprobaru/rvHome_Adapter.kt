package com.example.appmanprobaru

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class rvHome_Adapter(private val home_page_recyclerView_Data: ArrayList<home_page_recyclerView_Data>): RecyclerView.Adapter<rvHome_Adapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }


    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_box_layout,
        parent, false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val currentItem =  home_page_recyclerView_Data[position]
//        holder.image.setImageResource(currentItem.titleImage)
        holder.title.text = currentItem.Name


//      GET IMAGE DR STORAGE FIREBASE KE LOCAL. UDA PAKE CACHEING
        if (currentItem.img.equals("")){

        }else{
            // Dapatkan referensi storage yang sesuai dengan path gambar Anda di Firebase Storage
            val storage = Firebase.storage("gs://manpro-12.appspot.com")

            val storageRef = storage.reference

            // Create a reference with an initial file path and name
            val pathReference = storageRef.child("events/"+currentItem.img).downloadUrl

            pathReference.addOnSuccessListener { uri ->
                Glide.with(holder.itemView.context)
                    .load(uri)
                    .into(holder.image)
            }
                .addOnFailureListener{
                    err ->
                    Log.d("ERROR GET", err.toString())
                }
        }


    }

    override fun getItemCount(): Int {
        return home_page_recyclerView_Data.size
    }

    class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.rvHome_image)
        val title: TextView = itemView.findViewById(R.id.rvHome_Title)
        val card: CardView = itemView.findViewById(R.id.rvHome_card)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }
        }


    }
}