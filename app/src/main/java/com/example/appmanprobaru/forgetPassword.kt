package com.example.appmanprobaru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import btn_home_active
import com.example.appmanprobaru.admin.adminListAdmin
import com.google.firebase.firestore.FirebaseFirestore

class forgetPassword : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        val _editEmail = findViewById<EditText>(R.id.editEmail)
        val _btn_forget = findViewById<Button>(R.id.btn_forget)

        db = FirebaseFirestore.getInstance()

        _btn_forget.setOnClickListener {
            val dbAccount = db.collection("account")

            dbAccount.whereEqualTo("username",  _editEmail.text.toString().lowercase())
                .get().addOnSuccessListener{documents ->
                    for (document in documents) {
                        val documentId = document.id
                        // Do something with the document ID

                        val docRef = dbAccount.document(documentId)

                        // Update the value of a field
                        docRef.update(mapOf(
                            "fpass" to true
                        )).addOnSuccessListener {
                            Toast.makeText(this@forgetPassword, "Forget Password Request Sent", Toast.LENGTH_SHORT).show()
                            val eIntent = Intent(this, HomeActivity::class.java)
                            startActivity(eIntent)
                            finish()

                        }.addOnFailureListener { exception ->
                            Log.w("UP DATA", "Error getting documents: ", exception)
                        }
                    }


                }.addOnFailureListener { exception ->
                    Log.w("GET DATA", "Error getting documents: ", exception)
                }
        }



    }
}