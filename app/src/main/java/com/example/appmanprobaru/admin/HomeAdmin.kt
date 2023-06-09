package com.example.appmanprobaru.admin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.appmanprobaru.HomeActivity
import com.example.appmanprobaru.R
import com.example.appmanprobaru.detail_event
import com.example.appmanprobaru.home_page_recyclerView_Data
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import eventListAdmin

class HomeAdmin : AppCompatActivity() {
    lateinit var _navbarAdmin : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_navbar_admin)

        val sharedPreferences = getSharedPreferences("SessionUser", Context.MODE_PRIVATE)


        if (!sharedPreferences.contains("id_user")) {
            val intent = Intent(this@HomeAdmin, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        loadFragment(btn_home_admin())

        _navbarAdmin = findViewById<BottomNavigationView>(R.id.navbarAdmin) as BottomNavigationView
        _navbarAdmin.setOnItemSelectedListener {
            when (it.itemId){
                R.id.bottom_navbar_homeadm ->{
                    Log.d("CEK FRAGMENT", "KE KLIK")
                    loadFragment(btn_home_admin())
                    true
                }
                R.id.bottom_navbar_people ->{
                    loadFragment(PeopleAdmin())
                    true
                }
                R.id.bottom_navbar_eventsadm ->{
                    loadFragment(eventListAdmin())
                    true
                }
                R.id.bottom_navbar_profileadm ->{
                    Log.d("CEK FRAGMENT", "KE KLIK")
                    loadFragment(btn_profile_activeadmin())
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
        transaction.replace(R.id.Main_fragment_admin, fragment)
        transaction.commit()
    }

    fun passID(data: String) {
        val bundle = Bundle()
        bundle.putString("id", data)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentDetail = eventDetail()
        fragmentDetail.arguments = bundle
        transaction.replace(R.id.Main_fragment_admin, fragmentDetail)
        transaction.commit()

    }
}