package com.example.appmanprobaru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import btn_home_active
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_with_navbar)
        loadFragment(btn_home_active())
        bottomNav = findViewById<BottomNavigationView>(R.id.bot_nav_static) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.bottom_navbar_home->{
                    loadFragment(btn_home_active())
                    true
                }
                R.id.bottom_navbar_live->{
                    loadFragment(btn_live_active())
                    true
                }
                R.id.bottom_navbar_events ->{
                    true
                }
                R.id.bottom_navbar_profile->{
                    loadFragment(btn_profile_active())
                    true
                }
                else ->{
                    true
                }
            }
        }

    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Main_fragment,fragment)
        transaction.commit()
    }

}

