package com.example.appmanprobaru.admin

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
 * Use the [adminListAdmin.newInstance] factory method to
 * create an instance of this fragment.
 */


class adminListAdmin : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var _rvtAdminListAdmin: RecyclerView

    private lateinit var datalist: ArrayList<people>

    private var _id: MutableList<String> = emptyList<String>().toMutableList()

    private var _name: MutableList<String> = emptyList<String>().toMutableList()



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
        val view = inflater.inflate(R.layout.activity_admin_list_admin, container, false)

        datalist = arrayListOf<people>()

        _rvtAdminListAdmin = view.findViewById<RecyclerView>(R.id.rvAdmin)
        val _btnAddAdmin = view.findViewById<Button>(R.id.addAdmin)

        _btnAddAdmin.setOnClickListener {
            val newFragment = addAdmin()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.Main_fragment_admin, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        datainit()

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
            adminListAdmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun datainit() {

        val db = Firebase.firestore
        val dbpeople = db.collection("account").whereEqualTo("is_admin", true)
        dbpeople.get()
            .addOnSuccessListener { documents ->
                datalist.clear()
                _name.clear()
                _id.clear()

                for (document in documents) {
                    _name.add(document.data["nama"].toString())
                    _id.add(document.id.toString())

                }
                for (x in 0.._id.size - 1) {
                    val peopleData = people(
                        _name[x]
                    )
                    datalist.add(peopleData)
                }
                val adapterP = adapterPeople(datalist)
                _rvtAdminListAdmin.layoutManager = LinearLayoutManager(context)
                _rvtAdminListAdmin.adapter = adapterP
                adapterP.setOnItemClickCallback(object : adapterPeople.OnItemClickCallback{
                    override fun delData(pos: Int) {

                        AlertDialog.Builder(context!!)
                            .setTitle("HAPUS DATA")
                            .setMessage("APAKAH BENAR DATA "+datalist.get(pos).name+" akan dihapus ?")
                            .setPositiveButton(
                                "HAPUS",
                                DialogInterface.OnClickListener{ dialog, which ->

                                }
                            )
                            .setNegativeButton(
                                "BATAL",
                                DialogInterface.OnClickListener{ dialog, which ->
                                    Toast.makeText(
                                        context!!,
                                        "DATA BATAL DIHAPUS",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                            .show()
                    }
                })


            }
            .addOnFailureListener { exception ->
                Log.w("GET DATA", "Error getting documents: ", exception)
            }
    }
}