package com.example.appmanprobaru

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [btn_event_active.newInstance] factory method to
 * create an instance of this fragment.
 */
class btn_event_active : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var adapter: rvHome_Adapter
    private lateinit var recyclerViewHarian: RecyclerView
    private lateinit var recyclerViewMingguan: RecyclerView
    private lateinit var datalistHarian: ArrayList<event_page_recyclerView_Data>
    private lateinit var datalistMingguan: ArrayList<event_page_recyclerView_Data>
    private lateinit var imageId: Array<Int>
    private lateinit var titleId: Array<String>

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
        return inflater.inflate(R.layout.fragment_btn_event_active, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment btn_event_active.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            btn_event_active().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isiDataH()


        recyclerViewHarian = view.findViewById(R.id.rv_item_harian)
        recyclerViewMingguan = view.findViewById(R.id.rv_item_mingguan)


        recyclerViewHarian.adapter = rvEvent_Adapter(datalistHarian)
        recyclerViewMingguan.adapter = rvEvent_Adapter(datalistMingguan)

        for(i in datalistHarian.indices){
            println(datalistHarian[i])
        }


    }


    private fun isiDataH(){
        datalistHarian = arrayListOf()
        datalistMingguan = arrayListOf()

        imageId = arrayOf(
            R.drawable.mountain_dummy_square,
            R.drawable.icon_event,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square
        )

        titleId = arrayOf(
            "Gereja - gereja di seluruh dunia"
        )


            val rvEvent = event_page_recyclerView_Data(titleId[0], imageId[0])
            datalistMingguan.add(rvEvent)
            datalistHarian.add(rvEvent)





    }
}