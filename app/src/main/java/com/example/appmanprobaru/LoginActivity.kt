package com.example.appmanprobaru

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.appmanprobaru.admin.HomeAdmin
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    private var _userN: MutableList<String> = emptyList<String>().toMutableList()
    private var _nama: MutableList<String> = emptyList<String>().toMutableList()
    private var _pass: MutableList<String> = emptyList<String>().toMutableList()
    private var _id: MutableList<String> = emptyList<String>().toMutableList()
    private var _isAdmin: MutableList<String> = emptyList<String>().toMutableList()
    private var _email: MutableList<String> = emptyList<String>().toMutableList()
    private var _alamat: MutableList<String> = emptyList<String>().toMutableList()
    private var _nohp: MutableList<String> = emptyList<String>().toMutableList()
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var button_login: Button
    private lateinit var forgot_pass: TextView
    private lateinit var sign_up: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        button_login = findViewById<Button>(R.id.btn_login)
        et_email = findViewById<EditText>(R.id.editEmail)
        et_password = findViewById<EditText>(R.id.editTextPassword)
        forgot_pass = findViewById<TextView>(R.id.textView_forgotpass)
        sign_up = findViewById<TextView>(R.id.tv_NewUser)

        sign_up.text = Html.fromHtml(
            "<font color=${Color.BLACK}>New user? </font>" +
                    "<font color=${Color.BLUE}>Create an Account. </font>"
        )

        forgot_pass.setOnClickListener {

        }
        sign_up.setOnClickListener {
            val signUp = Intent(this, SignUpActivity::class.java)
            startActivity(signUp)
            finish()
        }


        db = FirebaseFirestore.getInstance()
        var dbAccount = db.collection("account")

        dbAccount.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    _userN.add(document.data["username"].toString())
                    _pass.add(document.data["password"].toString())
                    _nama.add(document.data["nama"].toString())
                    _email.add(document.data["email"].toString())
                    _alamat.add(document.data["alamat"].toString())
                    _nohp.add(document.data["notelp"].toString())
                    _id.add(document.id.toString())
                    _isAdmin.add(document.data["is_admin"].toString())
                    Log.d("CEK DATA", "CEK ${_userN}")
                    Log.d("GET DATA", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("GET DATA", "Error getting documents: ", exception)
            }

        button_login.setOnClickListener {
            login()
        }


    }

    private fun login() {
        var cek = false
        val email = et_email.text.toString().lowercase()
        val pass = et_password.text.toString()
        val sharedPreferences = getSharedPreferences("SessionUser", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Username and Password cant be blank", Toast.LENGTH_SHORT).show()
        } else {
            for (x in 0.._userN.size - 1) {
                if (email.equals(_userN[x].toString()) and pass.equals(_pass[x].toString())) {
                    cek = true
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Success!",
                        Toast.LENGTH_SHORT
                    ).show()

                    editor.putString("id_user", _id[x].toString())
                    editor.putString("username_user", _userN[x].toString())
                    editor.putString("nama_user", _nama[x].toString())
                    editor.putString("email_user", _email[x].toString())
                    editor.putString("nohp_user", _nohp[x].toString())
                    editor.putString("alamat_user", _alamat[x].toString())
                    editor.apply()
                    if (_isAdmin[x] == "true") {
                        val eIntent = Intent(this@LoginActivity, HomeAdmin::class.java)
                        startActivity(eIntent)
                    } else {
                        val eIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(eIntent)
                    }

                }
            }
            if (!cek) {
                Toast.makeText(
                    this@LoginActivity,
                    "Email atau Password Salah!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}

