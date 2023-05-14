package com.example.appmanprobaru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    private lateinit var et_email: EditText
    private lateinit var et_username: EditText
    private lateinit var et_notelp: EditText
    private lateinit var et_alamat: EditText
    private lateinit var et_password: EditText
    private lateinit var et_confirm: EditText
    private lateinit var button_signup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        et_email = findViewById<EditText>(R.id.et_email)
        et_username = findViewById<EditText>(R.id.et_username)
        et_notelp = findViewById<EditText>(R.id.et_noTelepon)
        et_alamat = findViewById<EditText>(R.id.et_alamat)
        et_password = findViewById<EditText>(R.id.et_password)
        et_confirm = findViewById<EditText>(R.id.et_confirmPassword)
        button_signup = findViewById<Button>(R.id.btn_signup)


        button_signup.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        var passFlag = false
        val email = et_email.text.toString()
        val username = et_username.text.toString()
        val noTelp = et_notelp.text.toString()
        val alamat = et_alamat.text.toString()
        val password = et_password.text.toString()
        val confirmPass = et_confirm.text.toString()

        if (email.isBlank() || username.isBlank() || noTelp.isBlank() || alamat.isBlank() || password.isBlank() || confirmPass.isBlank()){
            Toast.makeText(this,"Semua kolom wajib di isi", Toast.LENGTH_SHORT ).show()
        }
        else if (password != confirmPass){
            Toast.makeText(this, "Password dan ConfirmPassword tidak sama", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "SignUp Sukses", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}