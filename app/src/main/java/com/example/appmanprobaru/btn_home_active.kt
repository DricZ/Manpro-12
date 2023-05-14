
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.R
import com.example.appmanprobaru.home_page_recyclerView_Data
import com.example.appmanprobaru.rvHome_Adapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [btn_home_active.newInstance] factory method to
 * create an instance of this fragment.
 */
class btn_home_active : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: rvHome_Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist: ArrayList<home_page_recyclerView_Data>

    private lateinit var imageId: Array<Int>
    private lateinit var title: Array<String>


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
        return inflater.inflate(R.layout.fragment_btn_home_active, container, false)
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
            btn_home_active().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit()
        val layoutManager = GridLayoutManager(context,2)
        recyclerView = view.findViewById(R.id.rv_item)
        recyclerView.layoutManager = layoutManager
        adapter = rvHome_Adapter(datalist)
        recyclerView.adapter = adapter


    }


    private fun dataInit(){
        datalist =  arrayListOf<home_page_recyclerView_Data>()

        imageId = arrayOf(
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square,
            R.drawable.mountain_dummy_square
        )

        title = arrayOf(
            "Gereja - Gereja Diseluruh Dunia"
        )


        for (i in imageId.indices){
            val rvhome = home_page_recyclerView_Data(imageId[0],title[0])
            datalist.add(rvhome)
        }


    }
}