package com.example.appmanprobaru.admin

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.appmanprobaru.ChangePass
import com.example.appmanprobaru.R
import com.google.firebase.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.ktx.storage
import eventListAdmin
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

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
    private lateinit var imageUri : String
    private lateinit var imgclick : ImageView

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

        imgclick = view.findViewById(R.id.addevent_upimage)
        var title = view.findViewById<EditText>(R.id.addevent_title)
        val time = view.findViewById<Button>(R.id.addevent_time)
        val date = view.findViewById<Button>(R.id.addevent_date)
        val kategori = view.findViewById<Spinner>(R.id.addevent_kategori)
        val kategoriumur = view.findViewById<Spinner>(R.id.addevent_kategoriumur)
        var desc = view.findViewById<EditText>(R.id.addevent_desc)
        var location = view.findViewById<EditText>(R.id.addevent_alamat)
        var maxPeserta = view.findViewById<EditText>(R.id.addevent_maxpeserta)
        var addbutton = view.findViewById<Button>(R.id.btn_addevent)
        val itemskategori = arrayOf("Pilih Kategori 1","Harian", "Mingguan", "Bulanan", "Insidentil")
        val itemsum = arrayOf("Pilih Kategori 2","Umum", "Pemuda", "Remaja")

        val adapter = ArrayAdapter(view.context, R.layout.spinner_item_layout, itemskategori)
        kategori.adapter = adapter

        val adapterum = ArrayAdapter(view.context, R.layout.spinner_item_layout, itemsum)
        kategoriumur.adapter = adapterum


        imgclick.setOnClickListener {
            selectImageFromGallery(view)
        }

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




//        val storage = Firebase.storage("gs://manpro-12.appspot.com")
//
//        val storageRef = storage.reference
//        // Create a reference with an initial file path and name
//        val pathReference = storageRef.child("events/"+ UniqueJPG)

//        pathReference.putFile(imageUri.toUri())

//        pathReference.addOnSuccessListener { uri ->
//            Glide.with(requireContext())
//                .load(uri)
//                .into(event_image)
//        }

        addbutton.setOnClickListener {

            val dateString = date.text.toString() + " " + time.text.toString() +":00"

            if (title.text.toString() != "" &&
                desc.text.toString() != "" &&
                kategori.getSelectedItem().toString() != "Pilih Kategori 1" &&
                dateString != " :00" &&
                location.text.toString() != "" &&
                maxPeserta.text.toString() != "" &&
                kategoriumur.getSelectedItem().toString()  != "Pilih Kategori 2")
            {
                val db = Firebase.firestore
                val key = db.collection("YOUR_COLLECTION_NAME").document()
                val UniqueID = key.getId()
                val UniqueJPG = "$UniqueID.jpg"
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                val dates = formatter.parse(dateString)
                val dateObject = Date()
                val timestamp = Timestamp(dates!!)
                Log.d("TryingDate", dates.toString())
                Log.d("TryingDate", timestamp.toString())
                val dataInput = addEventDataClass(
                    title.text.toString(),
                    desc.text.toString(),
                    kategori.getSelectedItem().toString(),
                    timestamp,
                    location.text.toString(),
                    maxPeserta.text.toString(),
                    kategoriumur.getSelectedItem().toString(),
                    "",
                    true,
                    true
                )

                val a = db.collection("event").document(UniqueID).set(dataInput).addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Success Adding Event!",
                        Toast.LENGTH_SHORT
                    ).show()
                    val newFragment = eventListAdmin()
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.Main_fragment_admin, newFragment)
                    transaction.commit()
                }


            }
            else{
                Toast.makeText(
                    context,
                    "Please Fill All the Data First!",
                    Toast.LENGTH_SHORT
                ).show()
            }



        }

//        val dates = DateFormat.format("dd/MM/yyyy hh:mm:ss", "cal").toString()

    }



    fun selectImageFromGallery(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                val selectedImageUri = data?.data
                if (!requireActivity().isDestroyed) {
                    // Start a load with Glide
                    activity?.let {
                        Glide.with(it)
                            .load(selectedImageUri)
                            .into(imgclick)
                    }
                }
                // Use the selectedImageUri to display the selected image in an ImageView or upload it to a server
            } catch (e: Exception) {
                // Handle the exception here
                Log.e("MyFragment", "Error accessing selected image", e)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel any pending loads with Glide
        Glide.with(this).clear(imgclick)
    }
}