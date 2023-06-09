package com.example.appmanprobaru.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R
import com.google.firebase.firestore.FirebaseFirestore

class adapterPeserta (private val listForum : ArrayList<people>) : RecyclerView.Adapter<adapterPeserta.ListViewHolder>(){

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(pos: Int, id: String)
        fun fPass(pos: Int)
//        fun cekFpass()
    }

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _tvName : TextView = itemView.findViewById(R.id.tvName)
        var _btnDelete : Button = itemView.findViewById(R.id.btnDelete)
        var _tvJemputan : TextView = itemView.findViewById(R.id.tvJemputan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_peserta, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var people = listForum[position]

        val db = FirebaseFirestore.getInstance()

        val dbpeople = db.collection("account").whereEqualTo("username", "true")

        holder._tvName.setText(people.name)
        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(position, people.id)
        }
    }

    override fun getItemCount(): Int {
        return listForum.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

}