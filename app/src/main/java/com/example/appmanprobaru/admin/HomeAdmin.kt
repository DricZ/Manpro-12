package com.example.appmanprobaru.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeAdmin : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_admin)

        val _rvHomeEventAdmin = findViewById<RecyclerView>(R.id.rvHomeEventAdmin)
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

    }
}