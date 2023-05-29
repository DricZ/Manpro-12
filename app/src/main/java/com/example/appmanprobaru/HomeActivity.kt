package com.example.appmanprobaru

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import btn_home_active
import com.example.appmanprobaru.admin.HomeAdmin
import com.example.appmanprobaru.admin.PeopleAdmin
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity(), Interface_Detail_Event {
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_with_navbar)

        val sharedPreferences = getSharedPreferences("SessionUser", Context.MODE_PRIVATE)

        if (sharedPreferences?.getString("role_user", "") == "true") {
            val intent = Intent(this@HomeActivity, HomeAdmin::class.java)
            startActivity(intent)
            finish()
        }

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
                    loadFragment(btn_about_us_active())
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

    override fun passData(
        titleImage: Int,
        Name: String,
        desc: String,
        category: String,
        date: String,
        location: String,
        maxPeserta: String,
        img: String
    ) {
        val bundle = Bundle()
        bundle.putInt("titleImage",titleImage)
        bundle.putString("name", Name)
        bundle.putString("description", desc)
        bundle.putString("category", category)
        bundle.putString("date", date)
        bundle.putString("location", location)
        bundle.putString("maxPeserta", maxPeserta)
        bundle.putString("img", img)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentDetail = detail_event()
        fragmentDetail.arguments = bundle
        transaction.replace(R.id.Main_fragment,fragmentDetail)
        transaction.commit()
    }

    override fun passModel(model: home_page_recyclerView_Data) {
        val bundle = Bundle()
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentDetail = detail_event()
        fragmentDetail.arguments = bundle
        transaction.replace(R.id.Main_fragment,fragmentDetail)
        transaction.commit()
    }
    override fun passCategory(kategori: String) {
        val bundle = Bundle()
        bundle.putString("Kategori", kategori)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentHome = btn_home_active()
        fragmentHome.arguments = bundle
        transaction.replace(R.id.Main_fragment, fragmentHome)
        transaction.commit()
    }

    override fun passDetail(model: home_page_recyclerView_Data) {
        val bundle = Bundle()
        bundle.putString("Name",model.Name)
        bundle.putInt("titleImage", model.titleImage)
        bundle.putString("desc", model.desc)
        bundle.putString("category", model.category)
        bundle.putString("date", model.date)
        bundle.putString("location", model.location)
        bundle.putString("maxPeserta", model.maxPeserta)
        bundle.putString("kategoriPeserta", model.kategoriPeserta)
        bundle.putString("img", model.img)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentDetail = detail_event()
        fragmentDetail.arguments = bundle
        transaction.replace(R.id.Main_fragment, fragmentDetail)
        transaction.commit()

    }


}

