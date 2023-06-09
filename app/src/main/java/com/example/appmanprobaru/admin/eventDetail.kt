package com.example.appmanprobaru.admin

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmanprobaru.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.Calendar

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
    private lateinit var _rvHomeEventAdmin: RecyclerView

    private lateinit var datalist: ArrayList<people>

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
        datalist = arrayListOf<people>()
        _rvHomeEventAdmin = view.findViewById<RecyclerView>(R.id.rvPeserta)

        val imgclick = view.findViewById<ImageView>(R.id.addevent_upimage)
        var title = view.findViewById<TextView>(R.id.tvTitle)
        val time = view.findViewById<TextView>(R.id.tvTimeEvent)
        val date = view.findViewById<TextView>(R.id.tvDateEvent)

        var desc = view.findViewById<EditText>(R.id.addevent_desc)
        var location = view.findViewById<EditText>(R.id.addevent_alamat)
        var maxPeserta = view.findViewById<EditText>(R.id.addevent_maxpeserta)


        val db = Firebase.firestore
        var dbevent = db.collection("event").document(id!!)
        dbevent.get()
            .addOnSuccessListener { document ->
                title.text = (document.data?.get("name").toString())

                var img = (document.data?.get("imgloc").toString())
                var dates = (((document.data?.get("date") as com.google.firebase.Timestamp).toDate()
                    .toString()))
                val arrayDate: List<String> = dates!!.split(" ")
                val datetext = arrayDate[1] + " " + arrayDate[2] + " " + arrayDate[5]
                val timetext = arrayDate[3]
                date.text = datetext
                time.text = timetext
                desc.setText(document.data?.get("desc").toString())
                location.setText(document.data?.get("location").toString())
                maxPeserta.setText(document.data?.get("maxPeserta").toString())

                val storage = Firebase.storage("gs://manpro-12.appspot.com")

                val storageRef = storage.reference

                // Create a reference with an initial file path and name
                val pathReference = storageRef.child("events/"+ img).downloadUrl

                pathReference.addOnSuccessListener { uri ->
                    Glide.with(requireContext())
                        .load(uri)
                        .into(imgclick)
                }
            }
        var dbregist = db.collection("registration").whereEqualTo("eventID", id!!)
            .get().addOnSuccessListener { documents ->
                for(doc in documents){
                    var dbacc = db.collection("account").document(doc.data["accountID"].toString()).get()
                        .addOnSuccessListener { document ->

                            val eventpeople = people(
                                document.data?.get("nama").toString(),
                                document.id
                            )
                            datalist.add(eventpeople)
                            _rvHomeEventAdmin.layoutManager = LinearLayoutManager(context)
                            _rvHomeEventAdmin.adapter = adapterPeserta(datalist)
                        }
                }
            }
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