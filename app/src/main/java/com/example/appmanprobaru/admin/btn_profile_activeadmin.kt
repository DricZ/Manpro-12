package com.example.appmanprobaru.admin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.appmanprobaru.HomeActivity
import com.example.appmanprobaru.LoginActivity
import com.example.appmanprobaru.R
import com.example.appmanprobaru.SignUpActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [btn_profile_activeadmin.newInstance] factory method to
 * create an instance of this fragment.
 */
class btn_profile_activeadmin : Fragment() {
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
            btn_profile_activeadmin().apply {
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

        val _profile = view.findViewById<CardView>(R.id.detail_profile)
        val _event = view.findViewById<CardView>(R.id.detail_event)
        val _isiprofile = view.findViewById<CardView>(R.id.isi_profile)
        val _isievent = view.findViewById<CardView>(R.id.isi_event)

        val _dropdown = view.findViewById<ImageView>(R.id.dropdown)
        val _dropdown11 = view.findViewById<ImageView>(R.id.dropdown11)
        val _dropup = view.findViewById<ImageView>(R.id.dropup)
        val _dropup11 = view.findViewById<ImageView>(R.id.dropup11)

        val sharedPreferences = activity?.getSharedPreferences("SessionUser", Context.MODE_PRIVATE)

        if (sharedPreferences?.contains("id_user") == true) {
            _nama.setText(sharedPreferences.getString("nama_user","null"))
            _username.setText(sharedPreferences.getString("username_user","null"))
            _email.setText(sharedPreferences.getString("email_user","null"))
            _alamat.setText(sharedPreferences.getString("alamat_user","null"))
            _nohp.setText(sharedPreferences.getString("nohp_user","null"))

            _profile.setOnClickListener {
                if (_isiprofile.visibility == View.VISIBLE){
                    _isiprofile.visibility = View.GONE
                    _dropdown.visibility = View.VISIBLE
                    _dropup.visibility = View.INVISIBLE
                } else{
                    _isiprofile.visibility = View.VISIBLE
                    _dropdown.visibility = View.INVISIBLE
                    _dropup.visibility = View.VISIBLE
                }
            }

            _event.setOnClickListener {
                if (_isievent.visibility == View.VISIBLE){
                    _isievent.visibility = View.GONE
                    _dropdown11.visibility = View.VISIBLE
                    _dropup11.visibility = View.INVISIBLE
                } else{
                    _isievent.visibility = View.VISIBLE
                    _dropdown11.visibility = View.INVISIBLE
                    _dropup11.visibility = View.VISIBLE
                }
            }
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
            val sharedPreferences = activity?.getSharedPreferences("SessionUser",
                Context.MODE_PRIVATE
            )
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