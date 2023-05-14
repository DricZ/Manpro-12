package com.example.appmanprobaru

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var button_login: Button
    private lateinit var forgot_pass : TextView
    private lateinit var sign_up : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        button_login = findViewById<Button>(R.id.btn_login)
        et_email = findViewById<EditText>(R.id.editEmail)
        et_password = findViewById<EditText>(R.id.editTextPassword)
        forgot_pass = findViewById<TextView>(R.id.textView_forgotpass)
        sign_up = findViewById<TextView>(R.id.tv_NewUser)

       sign_up.text = Html.fromHtml("<font color=${Color.BLACK}>New user? </font>" +
       "<font color=${Color.BLUE}>Create an Account. </font>")

        forgot_pass.setOnClickListener {

        }
        sign_up.setOnClickListener {
            val signUp = Intent(this, SignUpActivity::class.java)
            startActivity(signUp)
            finish()
        }



        button_login.setOnClickListener {
            login()
        }


    }

    private fun login(){

        val email = et_email.text.toString()
        val pass = et_password.text.toString()
        if (email.isBlank() || pass.isBlank()){
            Toast.makeText(this, "Username and Password cant be blank", Toast.LENGTH_SHORT).show()
        }
        else if (email == "tes123" && pass == "123"){
            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if (email != "tes123" && pass != "123"){
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()

        }
    }


}