package com.example.appmanprobaru.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.appmanprobaru.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class PeopleAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_admin)

        var _btnAdmin = findViewById<Button>(R.id.btnAdmin)
        var _btnJemaat = findViewById<Button>(R.id.btnJemaat)

        _btnAdmin.setOnClickListener {
            val intent = Intent(this@PeopleAdmin, adminListAdmin::class.java)
            startActivity(intent)
        }
        _btnJemaat.setOnClickListener {
            val intent = Intent(this@PeopleAdmin, jemaatListAdmin::class.java)
            startActivity(intent)
        }
        var _navbarAdmin = findViewById<BottomNavigationView>(R.id.navbarAdmin) as BottomNavigationView
        _navbarAdmin.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_navbar_home -> {
                    val intent = Intent(this@PeopleAdmin, HomeAdmin::class.java)
                    startActivity(intent)
                    true
                }

                R.id.bottom_navbar_people -> {

                    true
                }

                R.id.bottom_navbar_events -> {
                    val intent = Intent(this@PeopleAdmin, eventListAdmin::class.java)
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
}