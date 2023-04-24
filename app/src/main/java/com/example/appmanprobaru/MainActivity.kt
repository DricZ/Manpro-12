package com.example.appmanprobaru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val button_login = findViewById<Button>(R.id.btn_login)

        button_login.setOnClickListener {

        }

    }


}