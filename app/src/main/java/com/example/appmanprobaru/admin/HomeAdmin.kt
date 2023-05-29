package com.example.appmanprobaru.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R
import com.example.appmanprobaru.btn_live_active
import com.example.appmanprobaru.btn_profile_active
import com.example.appmanprobaru.home_page_recyclerView_Data
import com.example.appmanprobaru.rvHome_Adapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp

class HomeAdmin : AppCompatActivity() {
    private lateinit var _rvHomeEventAdmin: RecyclerView

    private lateinit var datalist: ArrayList<HomeEvent>

    private var _id : MutableList<String> = emptyList<String>().toMutableList()

    private var _name : MutableList<String> = emptyList<String>().toMutableList()
    private var _img : MutableList<String> = emptyList<String>().toMutableList()
    private var _desc : MutableList<String> = emptyList<String>().toMutableList()
    private var _location : MutableList<String> = emptyList<String>().toMutableList()
    private var _date : MutableList<String> = emptyList<String>().toMutableList()
    private var _category : MutableList<String> = emptyList<String>().toMutableList()
    private var _maxPeserta : MutableList<String> = emptyList<String>().toMutableList()
    private var _kategoriPeserta : MutableList<String> = emptyList<String>().toMutableList()
//    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_admin)
        datalist =  arrayListOf<HomeEvent>()

        _rvHomeEventAdmin = findViewById<RecyclerView>(R.id.rvHomeEventAdmin)
        var _navbarAdmin = findViewById<BottomNavigationView>(R.id.navbarAdmin) as BottomNavigationView
        _navbarAdmin.setOnItemSelectedListener {
            when (it.itemId){
                R.id.bottom_navbar_home->{
                    true
                }
                R.id.bottom_navbar_people->{
                    val intent = Intent(this@HomeAdmin, PeopleAdmin::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bottom_navbar_events ->{
                    val intent = Intent(this@HomeAdmin, eventListAdmin::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bottom_navbar_logout->{

                    true
                }
                else ->{
                    true
                }
            }
        }

        datainit()

    }
    private fun datainit(){
//        val layoutManager = GridLayoutManager(2)

        val db = Firebase.firestore
        var dbevent = db.collection("event")
        dbevent.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    _id.add(document.id.toString())
                    _name.add(document.data["name"].toString())
                    _img.add(document.data["imgloc"].toString())
                    _date.add((document.data["date"].toString()))
//                    Log.d("TimeStampssssss", document.data["date"].toString())
                }
                for (x in 0.._id.size-1){
                    val eventdata =HomeEvent(_id[x], _img[x],_name[x],_date[x].toString(),_date[x].toString())
                    datalist.add(eventdata)
                }
                _rvHomeEventAdmin.layoutManager = LinearLayoutManager(this)
                _rvHomeEventAdmin.adapter = adapterHomeAdmin(datalist)


            }
            .addOnFailureListener { exception ->
                Log.w("GET DATA", "Error getting documents: ", exception)
            }
    }
}