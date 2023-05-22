
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmanprobaru.*
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
class btn_home_active : Fragment() {
    private lateinit var detail_event: Interface_Detail_Event
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: rvHome_Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist: ArrayList<home_page_recyclerView_Data>

    private lateinit var imageId: Array<Int>
    private lateinit var title: Array<String>
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
        recyclerView = view.findViewById(R.id.rv_item)
        val layoutManager = GridLayoutManager(context,2)
        val db = Firebase.firestore
        var dbevent = db.collection("event")
        var listdata = arrayListOf<home_page_recyclerView_Data>()
        dbevent.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    _id.add(document.id.toString())
                    _name.add(document.data["name"].toString())
                    _img.add(document.data["imgloc"].toString())
                    _desc.add(document.data["desc"].toString())
                    _category.add(document.data["category"].toString())
                    _date.add(document.data["date"].toString())
                    _location.add(document.data["location"].toString())
                    _maxPeserta.add(document.data["maxpeserta"].toString())
                    _kategoriPeserta.add(document.data["kategoripeserta"].toString())
//                    Log.d("GET DATA", "${document.id} => ${document.data}")
                }
                for (x in 0.._id.size-1){
                    val eventdata = home_page_recyclerView_Data(1, _name[x],_desc[x],_category[x],_date[x],_location[x],_maxPeserta[x],_kategoriPeserta[x], _img[x])
                    datalist.add(eventdata)
                }
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = rvHome_Adapter(datalist)


            }
            .addOnFailureListener { exception ->
                Log.w("GET DATA", "Error getting documents: ", exception)
            }



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


//        for (i in imageId.indices){
//            val rvhome = home_page_recyclerView_Data(imageId[0],title[0])
//            datalist.add(rvhome)
//        }


    }
}