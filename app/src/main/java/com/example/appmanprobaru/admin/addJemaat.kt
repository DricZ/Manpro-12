package com.example.appmanprobaru.admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appmanprobaru.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addJemaat.newInstance] factory method to
 * create an instance of this fragment.
 */
class addJemaat : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var db: FirebaseFirestore
    private lateinit var jemaat_email: EditText
    private lateinit var jemaat_nama: EditText
    private lateinit var jemaat_username: EditText
    private lateinit var jemaat_notelp: EditText
    private lateinit var jemaat_alamat: EditText
    private lateinit var jemaat_password: EditText
    private lateinit var jemaat_confirm: EditText
    private lateinit var btn_addJemaat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_add_jemaat, container, false)

        jemaat_email = view.findViewById<EditText>(R.id.jemaat_email)
        jemaat_nama = view.findViewById<EditText>(R.id.jemaat_nama)
        jemaat_username = view.findViewById<EditText>(R.id.jemaat_username)
        jemaat_notelp = view.findViewById<EditText>(R.id.jemaat_noTelepon)
        jemaat_alamat = view.findViewById<EditText>(R.id.jemaat_alamat)
        jemaat_password = view.findViewById<EditText>(R.id.jemaat_password)
        jemaat_confirm = view.findViewById<EditText>(R.id.jemaat_confirmPassword)
        btn_addJemaat = view.findViewById<Button>(R.id.btn_addJemaat)

        // INIT DB
        db = FirebaseFirestore.getInstance()


        btn_addJemaat.setOnClickListener {
            signUp()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment btn_home_active.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addJemaat().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun signUp(){
        val email = jemaat_email.text.toString()
        val nama = jemaat_nama.text.toString()
        val username = jemaat_username.text.toString()
        val noTelp = jemaat_notelp.text.toString()
        val alamat = jemaat_alamat.text.toString()
        val password = jemaat_password.text.toString()
        val confirmPass = jemaat_confirm.text.toString()

        val dbAccount = db.collection("account")

        if (email.isBlank() || username.isBlank() || noTelp.isBlank() || alamat.isBlank() || password.isBlank() || confirmPass.isBlank()){
            Toast.makeText(context,"Semua kolom wajib di isi", Toast.LENGTH_SHORT ).show()
        }
        else if (password != confirmPass){
            Toast.makeText(context, "Password dan Confirm Password Tidak Sama", Toast.LENGTH_SHORT).show()
        }
        else{
            Log.d("CEK SBLM QUERY", "BISA MASHOK")
            dbAccount.whereEqualTo("email",  email.lowercase())
                .get().addOnSuccessListener{data ->
                    if(data.size() == 0){
                        Log.d("CEK QUERY", "BISA MASHOK")
                        val user = hashMapOf(
                            "username" to username.lowercase(),
                            "password" to password,
                            "email" to email.lowercase(),
                            "notelp" to noTelp,
                            "alamat" to alamat,
                            "nama" to nama,
                            "is_admin" to false
                        )
                        dbAccount.add(user).addOnSuccessListener {
                            Toast.makeText(context, "Penambahan Sukses", Toast.LENGTH_SHORT).show()
                            val newFragment = jemaatListAdmin()
                            val transaction = requireActivity().supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.Main_fragment_admin, newFragment)
                            transaction.commit()

                        }.addOnFailureListener{exception ->
                            Log.w("UP DATA", "Error getting documents: ", exception)
                        }
                    }else{
                        Toast.makeText(context, "Email Sudah Ada!", Toast.LENGTH_SHORT).show()
                    }


                }.addOnFailureListener { exception ->
                    Log.w("GET DATA", "Error getting documents: ", exception)
                }


        }
    }
}