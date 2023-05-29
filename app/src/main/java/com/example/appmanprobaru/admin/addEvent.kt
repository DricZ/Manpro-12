package com.example.appmanprobaru.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.appmanprobaru.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class addEvent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        var _navbarAdmin = findViewById<BottomNavigationView>(R.id.navbarAdmin) as BottomNavigationView
        var title = findViewById<EditText>(R.id.title_event)
        var time = findViewById<EditText>(R.id.dafevt_nama)
        var date = findViewById<EditText>(R.id.et_username)
        var noTelp = findViewById<EditText>(R.id.et_noTelepon)
        var alamat = findViewById<EditText>(R.id.et_alamat)

        _navbarAdmin.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_navbar_home -> {
                    val intent = Intent(this@addEvent, HomeAdmin::class.java)
                    startActivity(intent)
                    true
                }

                R.id.bottom_navbar_people -> {
                    val intent = Intent(this@addEvent, PeopleAdmin::class.java)
                    startActivity(intent)
                    true
                }

                R.id.bottom_navbar_events -> {
                    val intent = Intent(this@addEvent, eventListAdmin::class.java)
                    startActivity(intent)
                    true
                }

                R.id.bottom_navbar_logout -> {

                    true
                }

                else -> {
                    true
                }
            }


        }

    }

//    val db = Firebase.firestore
//    val key = db.collection("YOUR_COLLECTION_NAME").document()
//    val UniqueID = key.getId()
//    val dataInput = addEventDataClass(title, )
//    val a = db.collection("event").document(UniqueID).set(dataInput)
//

}