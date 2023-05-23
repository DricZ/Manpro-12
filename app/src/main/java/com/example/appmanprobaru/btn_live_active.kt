package com.example.appmanprobaru

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [btn_live_active.newInstance] factory method to
 * create an instance of this fragment.
 */
class btn_live_active : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


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
        return inflater.inflate(R.layout.fragment_btn_live_active, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment btn_live_active.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            btn_live_active().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = view.findViewById<Spinner>(R.id.pilihanstream)

        val items = arrayOf("GKA Gloria NE", "Nazareth Teens")
        val adapter = CustomSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val yt_player = view.findViewById<WebView>(R.id.yt_player)
        val _playyt = view.findViewById<Button>(R.id.playyt)


        yt_player.settings.javaScriptEnabled = true

        _playyt.setOnClickListener {
            var videoId = ""

            // KALAU GKA GLORIA
            if (spinner.selectedItem.toString() == "GKA Gloria NE"){
                videoId = "OB0Z9lpguqQ"

            // KALAU SATUNYA
            }else if (spinner.selectedItem.toString() == "Nazareth Teens"){
                videoId = "GJ7U1pWuXzk"
            }

            val html = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" frameborder=\"0\" allowfullscreen></iframe>"
            yt_player.loadData(html, "text/html", "utf-8")
        }

    }
}

class CustomSpinnerAdapter(context: Context, textViewResourceId: Int, objects: Array<String>) : ArrayAdapter<Any>(context, textViewResourceId, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        (view as TextView).setTextColor(Color.BLACK)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        view.setBackgroundColor(Color.WHITE)
        (view as TextView).setTextColor(Color.BLACK)
        return view
    }
}