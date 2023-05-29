package com.example.appmanprobaru.admin

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.HomeActivity
import com.example.appmanprobaru.Interface_Detail_Event
import com.example.appmanprobaru.R
import com.example.appmanprobaru.home_page_recyclerView_Data
import com.example.appmanprobaru.rvHome_Adapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [btn_home_active.newInstance] factory method to
 * create an instance of this fragment.
 */

class btn_home_admin : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var _rvHomeEventAdmin: RecyclerView

    private lateinit var datalist: ArrayList<HomeEvent>

    private var _id : MutableList<String> = emptyList<String>().toMutableList()

    private var _name : MutableList<String> = emptyList<String>().toMutableList()
    private var _img : MutableList<String> = emptyList<String>().toMutableList()
    private var _desc : MutableList<String> = emptyList<String>().toMutableList()
    private var _location : MutableList<String> = emptyList<String>().toMutableList()
    private var _date : MutableList<String> = emptyList<String>().toMutableList()
    private var _category : MutableList<String> = emptyList<String>().toMutableList()
    private var _maxPeserta : MutableList<String> = emptyList<String>().toMutableList()
    private var _kategoriPeserta : MutableList<String> = emptyList<String>().toMutableList()

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
        val view = inflater.inflate(R.layout.fragment_btn_home_active_admin, container, false)

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
            btn_home_admin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datalist =  arrayListOf<HomeEvent>()

        _rvHomeEventAdmin = view.findViewById<RecyclerView>(R.id.rvHomeEventAdmin)

        datainit()

    }

    private fun datainit(){
//        val layoutManager = GridLayoutManager(2)

        val db = Firebase.firestore
        var dbevent = db.collection("event")
        dbevent.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    _id.add(document.id.toString())
                    _name.add(document.data["name"].toString())
                    _img.add(document.data["imgloc"].toString())
                    _date.add(((document.data["date"] as com.google.firebase.Timestamp).toDate().toString()))
//                    Log.d("TimeStampssssss", document.data["date"].toString())
                }
                for (x in 0.._id.size-1){
                    val arrayDate: List<String> =_date[x].split(" ")
                    val eventdata =HomeEvent(_id[x], _img[x],_name[x],arrayDate[1] + " "+arrayDate[2] + " " + arrayDate[5],arrayDate[3] + " "+arrayDate[4])
                    datalist.add(eventdata)
                }
                _rvHomeEventAdmin.layoutManager = LinearLayoutManager(this.context)
                _rvHomeEventAdmin.adapter = adapterHomeAdmin(datalist)


            }
            .addOnFailureListener { exception ->
                Log.w("GET DATA", "Error getting documents: ", exception)
            }
    }


}