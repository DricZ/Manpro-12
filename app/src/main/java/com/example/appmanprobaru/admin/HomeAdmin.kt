package com.example.appmanprobaru.admin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R
import com.example.appmanprobaru.btn_event_active
import com.example.appmanprobaru.btn_live_active
import com.example.appmanprobaru.btn_profile_active
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
                R.id.bottom_navbar_live->{

                    true
                }
                R.id.bottom_navbar_events ->{

                    true
                }
                R.id.bottom_navbar_profile->{

                    true
                }
                else ->{
                    true
                }
            }
        }

    }
}