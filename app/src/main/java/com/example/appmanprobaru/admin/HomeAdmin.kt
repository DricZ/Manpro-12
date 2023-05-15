package com.example.appmanprobaru.admin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R

class HomeAdmin : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_admin)

        val _rvHomeEventAdmin = findViewById<RecyclerView>(R.id.rvHomeEventAdmin)

    }
}