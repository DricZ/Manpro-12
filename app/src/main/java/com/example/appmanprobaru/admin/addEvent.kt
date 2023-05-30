package com.example.appmanprobaru.admin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.appmanprobaru.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addEvent.newInstance] factory method to
 * create an instance of this fragment.
 */
class addEvent : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val SELECT_IMAGE_REQUEST_CODE = 1

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
        val view = inflater.inflate(R.layout.activity_add_event, container, false)

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
            addEvent().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgclick = view.findViewById<ImageView>(R.id.addevent_upimage)

        imgclick.setOnClickListener {
            selectImageFromGallery(view)
        }

        //        var _navbarAdmin = findViewById<BottomNavigationView>(R.id.navbarAdmin) as BottomNavigationView
        var title = view.findViewById<EditText>(R.id.addevent_title)
        var time = view.findViewById<EditText>(R.id.addevent_time)
        var date = view.findViewById<EditText>(R.id.addevent_date)
        var desc = view.findViewById<EditText>(R.id.addevent_desc)
        var location = view.findViewById<EditText>(R.id.addevent_alamat)
        var category = view.findViewById<EditText>(R.id.addevent_kategori)
        var kategoriUmur = view.findViewById<EditText>(R.id.addevent_kategoriumur)
        var maxPeserta = view.findViewById<EditText>(R.id.addevent_maxpeserta)
        var image = view.findViewById<EditText>(R.id.addevent_upimage)

//        _navbarAdmin.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.bottom_navbar_home -> {
//                    val intent = Intent(this@addEvent, HomeAdmin::class.java)
//                    startActivity(intent)
//                    true
//                }
//
//                R.id.bottom_navbar_people -> {
//                    val intent = Intent(this@addEvent, PeopleAdmin::class.java)
//                    startActivity(intent)
//                    true
//                }
//
//                R.id.bottom_navbar_events -> {
//                    val intent = Intent(this@addEvent, eventListAdmin::class.java)
//                    startActivity(intent)
//                    true
//                }
//
//                R.id.bottom_navbar_logout -> {
//
//                    true
//                }
//
//                else -> {
//                    true
//                }
//            }
//
//
//        }


//        val db = Firebase.firestore
//        val key = db.collection("YOUR_COLLECTION_NAME").document()
//        val UniqueID = key.getId()
//        val dataInput = addEventDataClass(title, desc,  )
//        val a = db.collection("event").document(UniqueID).set(dataInput)

    }



    fun selectImageFromGallery(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Use the selectedImageUri to display the selected image in an ImageView or upload it to a server
        }
    }
}