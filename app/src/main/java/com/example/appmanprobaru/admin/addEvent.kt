package com.example.appmanprobaru.admin

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.appmanprobaru.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar
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
        val time = view.findViewById<Button>(R.id.addevent_time)
        val date = view.findViewById<Button>(R.id.addevent_date)
        val kategori = view.findViewById<Spinner>(R.id.addevent_kategori)
        val kategoriumur = view.findViewById<Spinner>(R.id.addevent_kategoriumur)
        var alamat = view.findViewById<EditText>(R.id.addevent_maxpeserta)
        var desc = view.findViewById<EditText>(R.id.addevent_desc)
        var location = view.findViewById<EditText>(R.id.addevent_alamat)
        var maxPeserta = view.findViewById<EditText>(R.id.addevent_maxpeserta)
        var image = view.findViewById<EditText>(R.id.addevent_upimage)

        val itemskategori = arrayOf("Pilih Kategori 1","Harian", "Mingguan", "Bulanan", "Insidentil")
        val itemsum = arrayOf("Pilih Kategori 2","Umum", "Pemuda", "Remaja")

        val adapter = ArrayAdapter(view.context, R.layout.spinner_item_layout, itemskategori)
        kategori.adapter = adapter

        val adapterum = ArrayAdapter(view.context, R.layout.spinner_item_layout, itemsum)
        kategoriumur.adapter = adapterum


        date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                view.context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    date.setText("$selectedDay/$selectedMonth/$selectedYear")
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        time.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                view.context,
                { _, selectedHour, selectedMinute ->
                    // Do something with the selected time
                    time.setText("$selectedHour:$selectedMinute")
                },
                hour,
                minute,
                true
            )
            timePickerDialog.show()
        }


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