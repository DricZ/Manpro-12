package com.example.appmanprobaru

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import btn_home_active
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [btn_profile_active.newInstance] factory method to
 * create an instance of this fragment.
 */
class btn_profile_active : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val sharedPreferences = activity?.getSharedPreferences("SessionUser", Context.MODE_PRIVATE)

        return if (sharedPreferences?.contains("id_user") == true) {
            // return another view if there is data in SessionUser SharedPreferences
            inflater.inflate(R.layout.activity_profile_login, container, false)
        } else {
            inflater.inflate(R.layout.fragment_btn_profile_active, container, false)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment btn_profile_active.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            btn_profile_active().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _signupbtn = view.findViewById<Button>(R.id.signup_btn)
        val _signinbtn = view.findViewById<Button>(R.id.signin_btn)
        val _logout = view.findViewById<Button>(R.id.button_logut)

        val _nama = view.findViewById<TextView>(R.id.profile_name)
        val _username = view.findViewById<TextView>(R.id.profile_username)
        val _email = view.findViewById<TextView>(R.id.profile_email)
        val _alamat = view.findViewById<TextView>(R.id.profile_alamat)
        val _nohp = view.findViewById<TextView>(R.id.profile_nohp)

        val sharedPreferences = activity?.getSharedPreferences("SessionUser", Context.MODE_PRIVATE)

        if (sharedPreferences?.contains("id_user") == true) {
            _nama.setText(sharedPreferences.getString("nama_user","null"))
            _username.setText(sharedPreferences.getString("username_user","null"))
            _email.setText(sharedPreferences.getString("email_user","null"))
            _alamat.setText(sharedPreferences.getString("alamat_user","null"))
            _nohp.setText(sharedPreferences.getString("nohp_user","null"))

        } else {

        }

        _signinbtn?.setOnClickListener {
            val eIntent = Intent(view.context, LoginActivity::class.java)
            startActivity(eIntent)
        }

        _signupbtn?.setOnClickListener {
            val eIntent = Intent(view.context, SignUpActivity::class.java)
            startActivity(eIntent)
        }

        _logout?.setOnClickListener {
            val sharedPreferences = activity?.getSharedPreferences("SessionUser", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.clear()?.apply()
            Toast.makeText(
                context,
                "Logout Success!",
                Toast.LENGTH_SHORT
            ).show()
            val eIntent = Intent(view.context, HomeActivity::class.java)
            startActivity(eIntent)
        }
    }
}