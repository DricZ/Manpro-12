package com.example.appmanprobaru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class profile_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myImageView: ImageView = findViewById(R.id.dropdown)
        myImageView.bringToFront()
//        setContentView(R.layout.activity_profile_login)

//        val myButton: Button = findViewById(R.id.profile_detail_btn)

//        myButton.setOnClickListener {
//            myLayout.visibility = View.VISIBLE
//            myTextView.text = "Isi text view telah diubah"
//        }
    }
}