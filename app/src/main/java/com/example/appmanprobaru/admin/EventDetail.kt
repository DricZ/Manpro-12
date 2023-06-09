package com.example.appmanprobaru.admin

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.appmanprobaru.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addEvent.newInstance] factory method to
 * create an instance of this fragment.
 */
class eventDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val SELECT_IMAGE_REQUEST_CODE = 1

    private var id: String? =""

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
        val view = inflater.inflate(R.layout.fragment_event_detail_admin, container, false)
        id = arguments?.getString("id", "Nan")
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
        val title = view.findViewById<EditText>(R.id.addevent_title)
        val time = view.findViewById<Button>(R.id.addevent_time)
        val date = view.findViewById<Button>(R.id.addevent_date)
        val kategori = view.findViewById<Spinner>(R.id.addevent_kategori)
        val kategoriumur = view.findViewById<Spinner>(R.id.addevent_kategoriumur)
        val desc = view.findViewById<EditText>(R.id.addevent_desc)
        val location = view.findViewById<EditText>(R.id.addevent_alamat)
        val maxPeserta = view.findViewById<EditText>(R.id.addevent_maxpeserta)

//        Log.d("CEK ID", id.toString())

        val itemskategori = arrayOf("Pilih Kategori 1","Harian", "Mingguan", "Bulanan", "Insidentil")
        val itemsum = arrayOf("Pilih Kategori 2","Umum", "Pemuda", "Remaja", "Dewasa")

        val adapter = ArrayAdapter(view.context, R.layout.spinner_item_layout, itemskategori)
        kategori.adapter = adapter

        val adapterum = ArrayAdapter(view.context, R.layout.spinner_item_layout, itemsum)
        kategoriumur.adapter = adapterum


//        imgclick.setOnClickListener {
//            selectImageFromGallery(view)
//        }

//        date.setOnClickListener {
//            val calendar = Calendar.getInstance()
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            val datePickerDialog = DatePickerDialog(
//                view.context,
//                { _, selectedYear, selectedMonth, selectedDay ->
//                    date.setText("$selectedDay/$selectedMonth/$selectedYear")
//                },
//                year,
//                month,
//                day
//            )
//            datePickerDialog.show()
//        }
//
//        time.setOnClickListener {
//            val calendar = Calendar.getInstance()
//            val hour = calendar.get(Calendar.HOUR_OF_DAY)
//            val minute = calendar.get(Calendar.MINUTE)
//
//            val timePickerDialog = TimePickerDialog(
//                view.context,
//                { _, selectedHour, selectedMinute ->
//                    // Do something with the selected time
//                    time.setText("$selectedHour:$selectedMinute")
//                },
//                hour,
//                minute,
//                true
//            )
//            timePickerDialog.show()
//        }


        val db = Firebase.firestore
        db.collection("event").document(id.toString()).get().addOnSuccessListener{ document ->

            title.setText(document.getString("name"))
            val timestamp = document.getTimestamp("date")
            if (timestamp != null) {
                val dates = timestamp.toDate()
                val calendar = Calendar.getInstance()
                calendar.time = dates
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val month = calendar.get(Calendar.MONTH)
                val year = calendar.get(Calendar.YEAR)
//                val times = calendar.get(Calendar.)
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)
                val second = calendar.get(Calendar.SECOND)
                // Gunakan nilai hour, minute, dan second di sini

                date.text = "$day/$month/$year"
                time.text = "$hour:$minute:$second"
            }



            val kategoriValue = document.getString("category")
            val kategoriumurValue = document.getString("kategoriPeserta")

            val kategoriAdapter = kategori.adapter
            val kategoriumurAdapter = kategoriumur.adapter

            for (i in 0 until kategoriAdapter.count) {
                if (kategoriAdapter.getItem(i) == kategoriValue) {
                    kategori.setSelection(i)
                    break
                }
            }

            for (i in 0 until kategoriumurAdapter.count) {
                if (kategoriumurAdapter.getItem(i) == kategoriumurValue) {
                    kategoriumur.setSelection(i)
                    break
                }
            }

            desc.setText(document.getString("desc").toString())
            location.setText(document.getString("location").toString())
            maxPeserta.setText(document.getString("maxPeserta").toString())

            Log.d("CEK IMG", document.getString("imgloc").toString())

            val storage = Firebase.storage("gs://manpro-12.appspot.com")

            val storageRef = storage.reference

            // Create a reference with an initial file path and name
            val pathReference = storageRef.child("events/"+ document.getString("imgloc").toString()).downloadUrl

            pathReference.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(imgclick)
            }

        }

//        val key = db.collection("YOUR_COLLECTION_NAME").document()
//        val UniqueID = key.getId()
//        val dataInput = addEventDataClass(title, desc,  )
//        val a = db.collection("event").document(UniqueID).set(dataInput)

    }



//    fun selectImageFromGallery(view: View) {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val selectedImageUri = data?.data
//            // Use the selectedImageUri to display the selected image in an ImageView or upload it to a server
//        }
//    }
}