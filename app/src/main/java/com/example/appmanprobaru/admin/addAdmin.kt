package com.example.appmanprobaru.admin

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
import com.example.appmanprobaru.LoginActivity
import com.example.appmanprobaru.R
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addAdmin.newInstance] factory method to
 * create an instance of this fragment.
 */
class addAdmin : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var db: FirebaseFirestore
    private lateinit var admin_email: EditText
    private lateinit var admin_nama: EditText
    private lateinit var admin_username: EditText
    private lateinit var admin_notelp: EditText
    private lateinit var admin_alamat: EditText
    private lateinit var admin_password: EditText
    private lateinit var admin_confirm: EditText
    private lateinit var btn_addAdmin: Button

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
        val view = inflater.inflate(R.layout.activity_add_admin, container, false)

        admin_email = view.findViewById<EditText>(R.id.admin_email)
        admin_nama = view.findViewById<EditText>(R.id.admin_nama)
        admin_username = view.findViewById<EditText>(R.id.admin_username)
        admin_notelp = view.findViewById<EditText>(R.id.admin_noTelepon)
        admin_alamat = view.findViewById<EditText>(R.id.admin_alamat)
        admin_password = view.findViewById<EditText>(R.id.admin_password)
        admin_confirm = view.findViewById<EditText>(R.id.admin_confirmPassword)
        btn_addAdmin = view.findViewById<Button>(R.id.btn_addAdmin)

        // INIT DB
        db = FirebaseFirestore.getInstance()


        btn_addAdmin.setOnClickListener {
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
            addAdmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun signUp(){
        val email = admin_email.text.toString()
        val nama = admin_nama.text.toString()
        val username = admin_username.text.toString()
        val noTelp = admin_notelp.text.toString()
        val alamat = admin_alamat.text.toString()
        val password = admin_password.text.toString()
        val confirmPass = admin_confirm.text.toString()

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
                            "is_admin" to true,
                            "fpass" to false
                        )
                        dbAccount.add(user).addOnSuccessListener {
                            Toast.makeText(context, "Penambahan Sukses", Toast.LENGTH_SHORT).show()
                            val newFragment = adminListAdmin()
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