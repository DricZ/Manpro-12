package com.example.appmanprobaru

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detail_event.newInstance] factory method to
 * create an instance of this fragment.
 */
class detail_event : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var name: String? =""
    private var id: String? =""
    private var desc: String? =""
    private var category: String? =""
    private var date: String? = ""
    private var location: String? = ""
    private var maxPeserta: String? = ""
    private var kategoriPeserta: String? = ""
    private var img: String? = ""

    private lateinit var event_image: ImageView
    private lateinit var event_title: TextView
    private lateinit var event_tanggal: TextView
    private lateinit var event_durasi: TextView
    private lateinit var event_deskripsi: TextView
    private lateinit var event_datar: Button

    private lateinit var alertDialog: AlertDialog
    private lateinit var alertJemput: AlertDialog

    private lateinit var pop_daftar_No: Button
    private lateinit var pop_daftar_Yes: Button
    private lateinit var pop_jemput_No: Button
    private lateinit var pop_jemput_Yes: Button





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
        val view = inflater.inflate(R.layout.fragment_detail_event, container, false)

        name = arguments?.getString("Name", "Nan")
        id = arguments?.getString("id", "Nan")
        desc = arguments?.getString("desc", "Nan")
        category = arguments?.getString("category","Nan")
        date = arguments?.getString("date", "Nan")
        location = arguments?.getString("location", "Nan")
        maxPeserta = arguments?.getString("maxPeserta", "Nan")
        kategoriPeserta = arguments?.getString("kategoriPeserta", "Nan")
        img = arguments?.getString("img", "Nan")

        event_image = view.findViewById(R.id.detail_event_image)
        event_title = view.findViewById(R.id.detail_event_title)
        event_durasi = view.findViewById(R.id.detail_event_durasi)
        event_tanggal = view.findViewById(R.id.detail_event_date)
        event_deskripsi = view.findViewById(R.id.detail_event_deskripsi)
        event_datar = view.findViewById(R.id.detail_daftar_sekarang)

        event_datar.setOnClickListener {
            showKonfirmasiDaftar()
        }
        if (id != null){
            val db = Firebase.firestore
            var dbevent = db.collection("event").document(id!!)
            dbevent.get()
                .addOnSuccessListener { document ->

                    name = (document.data?.get("name").toString())
                    img = (document.data?.get("imgloc").toString())
                    date = (((document.data?.get("date") as com.google.firebase.Timestamp).toDate()
                        .toString()))
                    event_title.text = name
                    event_deskripsi.text = desc
                    event_tanggal.text = date
                    val storage = Firebase.storage("gs://manpro-12.appspot.com")

                    val storageRef = storage.reference

                    // Create a reference with an initial file path and name
                    val pathReference = storageRef.child("events/"+ img).downloadUrl

                    pathReference.addOnSuccessListener { uri ->
                        Glide.with(requireContext())
                            .load(uri)
                            .into(event_image)
                    }
                }
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
         * @return A new instance of fragment detail_event.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detail_event().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun showKonfirmasiDaftar() {
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.pop_menu_daftar,null)

        pop_daftar_No = dialogView.findViewById(R.id.pop_menu_daftar_No)
        pop_daftar_Yes = dialogView.findViewById(R.id.pop_menu_daftar_Yes)

        pop_daftar_No.setOnClickListener {
            alertDialog.hide()
        }

        pop_daftar_Yes.setOnClickListener {
            showKonfirmasiJemput()
        }

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(dialogView)

        alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    fun showKonfirmasiJemput(){
        alertDialog.hide()
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.pop_menu_jemput, null)

        pop_daftar_No = dialogView.findViewById(R.id.pop_menu_jemput_No)
        pop_daftar_Yes = dialogView.findViewById(R.id.pop_menu_jemput_Yes)
        val db = Firebase.firestore
        val sharedPreferences = context?.getSharedPreferences("SessionUser", MODE_PRIVATE)
        val idsss = sharedPreferences?.getString("id_user", "Noid")

        pop_daftar_No.setOnClickListener {
            val data = registData(idsss!!, id!!, false)


            db.collection("registration").document(id!!).set(data)
            alertJemput.hide()

        }

        pop_daftar_Yes.setOnClickListener {
            val data = registData(idsss!!, id!!, true)
            db.collection("registration").document(id!!).set(data)
            alertJemput.hide()

        }
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(dialogView)

        alertJemput = dialogBuilder.create()
        alertJemput.show()
    }
}