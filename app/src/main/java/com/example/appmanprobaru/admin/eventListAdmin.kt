package com.example.appmanprobaru.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appmanprobaru.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class eventListAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list_admin)

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
    }
}