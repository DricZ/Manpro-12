package com.example.appmanprobaru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationItemView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_btn_active_page)

        val bottomNav = findViewById<BottomNavigationItemView>(R.id.bot_nav_static)
    }
}