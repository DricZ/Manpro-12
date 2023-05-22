package com.example.appmanprobaru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        val _signupbtn = findViewById<Button>(R.id.signup_btn)
        val _signinbtn = findViewById<Button>(R.id.signin_btn)

        _signinbtn.setOnClickListener {
            val eIntent = Intent(this@profile, LoginActivity::class.java)
            startActivity(eIntent)
        }

        _signupbtn.setOnClickListener {
            val eIntent = Intent(this@profile, SignUpActivity::class.java)
            startActivity(eIntent)
        }
    }
}