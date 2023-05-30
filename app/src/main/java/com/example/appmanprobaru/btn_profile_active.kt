package com.example.appmanprobaru

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import btn_home_active
import com.bumptech.glide.Glide
import com.example.appmanprobaru.admin.HomeEvent
import com.example.appmanprobaru.admin.adapterEventAdmin
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

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
    private lateinit var  name:String
    private lateinit var img: String

    private var _id: MutableList<String> = emptyList<String>().toMutableList()

    private var _name: String = ""
    private var _img: String = ""
    private var _desc: MutableList<String> = emptyList<String>().toMutableList()
    private var _location: MutableList<String> = emptyList<String>().toMutableList()
    private var _date: MutableList<String> = emptyList<String>().toMutableList()
    private var _category: MutableList<String> = emptyList<String>().toMutableList()
    private var _maxPeserta: MutableList<String> = emptyList<String>().toMutableList()
    private var _kategoriPeserta: MutableList<String> = emptyList<String>().toMutableList()
//    private lateinit var rvview: RecyclerView

    private lateinit var adapter: rvHome_Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist: ArrayList<profile_page_event_recyclerView_Data>
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
            datalist = arrayListOf<profile_page_event_recyclerView_Data>()

            recyclerView = view.findViewById<RecyclerView>(R.id.profile_rv_event)

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



//            val date: String

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

            val db = Firebase.firestore
            val idss = sharedPreferences?.getString("id_user", "")
            Log.d("HAHAHAHAHAHAHAHA", idss!!)

            var dbregist = db.collection("registration").whereEqualTo("accountID", idss)
            dbregist.get()
                .addOnSuccessListener { documents ->
                    for (document in documents){
                        Log.d("HAHAHAHAHAHAHAHA", document.toString())
                        var eventid = document.data["eventID"].toString()
                            val dbevent = db.collection("event").document(eventid).get()
                                .addOnSuccessListener { Doc ->
                                    var name =(Doc.data?.get("name").toString())
                                    var img = (Doc.data?.get("imgloc").toString())
                                    val eventdata = profile_page_event_recyclerView_Data(
                                        Doc.id,
                                        img,
                                        name
                                    )
                                    Log.d("AAAAAAAAAAAAAA", eventdata.toString())
                                    datalist.add(eventdata)
                                    recyclerView.layoutManager = LinearLayoutManager(context)
                                    recyclerView.adapter = profile_event_rv_adapter(datalist)
                            }


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
            val sharedPreferences = activity?.getSharedPreferences("SessionUser", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.clear()?.apply()
            Toast.makeText(
                context,
                "Logout Success!",
                Toast.LENGTH_SHORT
            ).show()
            val eIntent = Intent(view.context, HomeActivity::class.java)
            eIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(eIntent)
        }
    }

    fun isidata(){




    }
}