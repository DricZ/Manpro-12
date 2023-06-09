package com.example.appmanprobaru.admin

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmanprobaru.R
import com.google.firebase.Timestamp
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
    private var filter:Boolean = true

    private lateinit var datalist: ArrayList<peopleeventDetail>
    private lateinit var datalistAll: ArrayList<peopleeventDetail>


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
    fun sort(){
        datalist.clear()
        for (doc in datalistAll){
            if (filter) {
                if (doc.needPickup) {
                    datalist.add(doc)
                }
            }
            else {
                datalist.add(doc)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datalist = arrayListOf<peopleeventDetail>()
        datalistAll = arrayListOf<peopleeventDetail>()
        _rvHomeEventAdmin = view.findViewById<RecyclerView>(R.id.rvPeserta)

        val imgclick = view.findViewById<ImageView>(R.id.addevent_upimage)
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val time = view.findViewById<TextView>(R.id.tvDateEvent)
        val date = view.findViewById<TextView>(R.id.tvTimeEvent)
//        val kategori = view.findViewById<TextView>(R.id.addevent_kategori)
//        val kategoriumur = view.findViewById<TextView>(R.id.addevent_kategoriumur)
        val desc = view.findViewById<EditText>(R.id.addevent_desc)
        val location = view.findViewById<EditText>(R.id.addevent_alamat)
        val maxPeserta = view.findViewById<EditText>(R.id.addevent_maxpeserta)

        var _btnFilter = view.findViewById<Button>(R.id.btnFilter)
        _btnFilter.setOnClickListener {
            if (filter){
                filter = false
                _btnFilter.setText("Jemputan")
                sort()
                _rvHomeEventAdmin.layoutManager = LinearLayoutManager(context)
                _rvHomeEventAdmin.adapter = adapterPeople(datalist)
            }else{
                filter = true
                _btnFilter.setText("All")
                sort()
                _rvHomeEventAdmin.layoutManager = LinearLayoutManager(context)
                _rvHomeEventAdmin.adapter = adapterPeople(datalist)

            }
        }
        val db = Firebase.firestore
        db.collection("event").document(id.toString()).get().addOnSuccessListener { document ->

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
        val buttonsimpan = view.findViewById<Button>(R.id.btnSimpan)
        buttonsimpan.setOnClickListener {

            val db = Firebase.firestore
            var dbevent = db.collection("event")
            var dbregist = db.collection("event").document(id!!).get()
                .addOnSuccessListener { document ->
                    var data = addEventDataClass(document.data?.get("name").toString(),desc.text.toString(), document.data?.get("category").toString(),document.data?.get("date") as Timestamp, location.text.toString(), maxPeserta.text.toString(), document.data?.get("kategoriPeserta").toString(), document.data?.get("imgloc").toString(), document.data?.get("status") as Boolean, document.data?.get("adminPickup") as Boolean )
                    dbevent.document(id!!).set(data)
                }


            Toast.makeText(
                context,
                "Success Edit Event!",
                Toast.LENGTH_SHORT
            ).show()



            desc.setText(document.getString("desc").toString())
            location.setText(document.getString("location").toString())
            maxPeserta.setText(document.getString("maxPeserta").toString())

            Log.d("CEK IMG", document.getString("imgloc").toString())

            val storage = Firebase.storage("gs://manpro-12.appspot.com")

            val storageRef = storage.reference

            // Create a reference with an initial file path and name
            val pathReference =
                storageRef.child("events/" + document.getString("imgloc").toString()).downloadUrl

            pathReference.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(imgclick)
            }
        }
        var dbregist = db.collection("registration").whereEqualTo("eventID", id!!)
            .get().addOnSuccessListener { documents ->
                for(doc in documents){
                    var dbacc = db.collection("account").document(doc.data["accountID"].toString()).get()
                        .addOnSuccessListener { document ->

                            val eventpeople = peopleeventDetail(
                                document.data?.get("nama").toString(),
                                document.id,
                                document.data?.get("notelp").toString(),
                                doc.data["need_pickup"] as Boolean
                            )
                            datalist.add(eventpeople)
                            datalistAll.add(eventpeople)
                            _rvHomeEventAdmin.layoutManager = LinearLayoutManager(context)
                            _rvHomeEventAdmin.adapter = adapterPeople(datalist)
                        }
                }
            }
        imgclick.setOnClickListener {
            selectImageFromGallery(view)
        }

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