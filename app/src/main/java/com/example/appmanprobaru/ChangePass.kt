package com.example.appmanprobaru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import btn_home_active
import com.example.appmanprobaru.admin.adminListAdmin
import com.google.firebase.firestore.FirebaseFirestore

class ChangePass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)

        val _editPass = findViewById<EditText>(R.id.editPass)
        val _confirmPass = findViewById<EditText>(R.id.confirmPass)
        val _btn_login = findViewById<Button>(R.id.btn_login)

        // Get the Intent that started this Activity
        val intent = getIntent()

        // Get the Bundle from the Intent
        val bundle = intent.getExtras()

        // Get the data from the Bundle
        val id_user = bundle?.getString("id_user")
        val lokasi_awal = bundle?.getString("lokasi_awal")
        val isAdmin = bundle?.getString("user_isAdmin")

        val db = FirebaseFirestore.getInstance()
        val dbAccount = db.collection("account")


        val docRef = dbAccount.document(id_user.toString())

        if (_editPass.text.toString().isBlank() || _confirmPass.text.toString().isBlank() ) {
            Toast.makeText(this, "Password and Password Confirm cant be blank", Toast.LENGTH_SHORT).show()
        } else if(_editPass.text.toString() != _confirmPass.text.toString()){
            Toast.makeText(this, "Password and Password Confirm tidak sama", Toast.LENGTH_SHORT).show()
        } else{
            // Update the value of a field
            docRef.update(mapOf(
                "password" to _editPass.text.toString()
            )).addOnSuccessListener {
                Toast.makeText(this@ChangePass, "Password Updated", Toast.LENGTH_SHORT).show()

                if(lokasi_awal == "jemaat"){
                    docRef.update(mapOf(
                        "fpass" to false
                    )).addOnSuccessListener {
                        val newFragment = btn_home_active()
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.Main_fragment, newFragment)
                        transaction.commit()
                    }

                }else{
                    val newFragment = adminListAdmin()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.Main_fragment_admin, newFragment)
                    transaction.commit()
                }


            }.addOnFailureListener { exception ->
                Log.w("UP DATA", "Error getting documents: ", exception)
            }
        }
    }
}