package com.example.appmanprobaru.admin

import android.R.string
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class eventListAdmin : AppCompatActivity() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list_admin)
        datalist =  arrayListOf<HomeEvent>()

        _rvHomeEventAdmin = findViewById<RecyclerView>(R.id.rvEvent)
        val _btnAddAdmin = findViewById<Button>(R.id.addAdmin)

        _btnAddAdmin.setOnClickListener {
            val intent = Intent(this@eventListAdmin, addEvent::class.java)
            startActivity(intent)
        }

        var _navbarAdmin = findViewById<BottomNavigationView>(R.id.navbarAdmin) as BottomNavigationView
        _navbarAdmin.setOnItemSelectedListener {
            when (it.itemId){
                R.id.bottom_navbar_home->{
                    val intent = Intent(this@eventListAdmin, HomeAdmin::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bottom_navbar_people->{
                    val intent = Intent(this@eventListAdmin, PeopleAdmin::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bottom_navbar_events ->{

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
                    _date.add(((document.data["date"] as com.google.firebase.Timestamp).toDate().toString()))
                }
                for (x in 0.._id.size-1){
                    val arrayDate: List<String> =_date[x].split(" ")
                    val eventdata =HomeEvent(_id[x], _img[x],_name[x],arrayDate[1] + " "+arrayDate[2] + " " + arrayDate[5],arrayDate[3] + " "+arrayDate[4])
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