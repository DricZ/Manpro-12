
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
    private lateinit var home_active : HomeActivity
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var passingmodel: Interface_Detail_Event
    private lateinit var main : HomeActivity

    private lateinit var kategori: String
    private lateinit var model: home_page_recyclerView_Data

    private lateinit var btn_harian: Button
    private lateinit var btn_mingguan: Button
    private lateinit var btn_bulanan: Button
    private lateinit var btn_insidentil: Button
    private lateinit var btn_pemuda: Button
    private lateinit var btn_umum: Button
    private lateinit var btn_remaja: Button
    private lateinit var btn_all: Button

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
        val view = inflater.inflate(R.layout.fragment_btn_home_active, container, false)
        passingmodel = activity as Interface_Detail_Event
        main = activity as HomeActivity

        btn_harian = view.findViewById<Button>(R.id.button_harian)
        btn_mingguan = view.findViewById<Button>(R.id.button_mingguan)
        btn_bulanan = view.findViewById<Button>(R.id.button_bulanan)
        btn_insidentil = view.findViewById<Button>(R.id.button_Insidentil)
        btn_pemuda = view.findViewById<Button>(R.id.btn_pemuda)
        btn_umum = view.findViewById<Button>(R.id.btn_umum)
        btn_remaja = view.findViewById<Button>(R.id.btn_remaja)


        btn_harian.setOnClickListener {
            main.passModel(datalist[0])
        }

        btn_mingguan.setOnClickListener {
            main.passCategory("Mingguan")
//            btn_harian.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.btn_not_active)))
//            btn_mingguan.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.btn_primary)))
//            btn_bulanan.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.btn_not_active)))
//            btn_insidentil.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.btn_not_active)))
//            btn_pemuda.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.btn_not_active)))
//            btn_umum.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.btn_not_active)))
//            btn_remaja.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.btn_not_active)))


        }

        btn_bulanan.setOnClickListener {
            main.passCategory("Bulanan")
        }

        btn_insidentil.setOnClickListener {
            main.passCategory("Insidentil")
        }

        btn_pemuda.setOnClickListener {
            main.passCategory("Pemuda")
        }

        btn_umum.setOnClickListener {
            main.passCategory("Umum")
        }

        btn_remaja.setOnClickListener {
            main.passCategory("Remaja")
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

        btn_harian = view.findViewById(R.id.button_harian)
        btn_mingguan = view.findViewById(R.id.button_mingguan)
        btn_bulanan = view.findViewById(R.id.button_bulanan)
        btn_insidentil = view.findViewById(R.id.button_Insidentil)
        btn_pemuda = view.findViewById(R.id.btn_pemuda)
        btn_umum = view.findViewById(R.id.btn_umum)
        btn_remaja = view.findViewById(R.id.btn_remaja)
        btn_all = view.findViewById(R.id.button_all)
        recyclerView = view.findViewById(R.id.rv_item)
        home_active = activity as HomeActivity

        var category = ""

        val searchView = view.findViewById<SearchView>(R.id.search_bar_home)

        btn_harian.setOnClickListener {
            btn_harian.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_primary)))
            btn_harian.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)))

            btn_mingguan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_mingguan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_bulanan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_bulanan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_insidentil.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_insidentil.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_pemuda.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_pemuda.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_umum.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_umum.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_remaja.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_remaja.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_all.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_all.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            printEvent(recyclerView, "Harian")
            category = "Harian"

        }

        btn_mingguan.setOnClickListener {
            btn_harian.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_harian.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_mingguan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_primary)))
            btn_mingguan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)))

            btn_bulanan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_bulanan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_insidentil.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_insidentil.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_pemuda.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_pemuda.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_umum.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_umum.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_remaja.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_remaja.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_all.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_all.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            printEvent(recyclerView, "Mingguan")
            category = "Mingguan"

        }

        btn_bulanan.setOnClickListener {
            btn_harian.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_harian.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_mingguan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_mingguan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_bulanan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_primary)))
            btn_bulanan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)))

            btn_insidentil.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_insidentil.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_pemuda.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_pemuda.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_umum.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_umum.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_remaja.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_remaja.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_all.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_all.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            printEvent(recyclerView, "Bulanan")
            category = "Bulanan"
        }

        btn_insidentil.setOnClickListener {
            btn_harian.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_harian.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_mingguan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_mingguan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_bulanan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_bulanan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_insidentil.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_primary)))
            btn_insidentil.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)))

            btn_pemuda.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_pemuda.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_umum.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_umum.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_remaja.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_remaja.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_all.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_all.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            printEvent(recyclerView, "Insidentil")
            category = "Insidentil"
        }

        btn_pemuda.setOnClickListener {
            btn_harian.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_harian.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_mingguan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_mingguan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_bulanan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_bulanan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_insidentil.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_insidentil.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_pemuda.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_primary)))
            btn_pemuda.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)))

            btn_umum.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_umum.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_remaja.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_remaja.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_all.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_all.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            printEventUmur(recyclerView, "Pemuda")
        }

        btn_umum.setOnClickListener {
            btn_harian.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_harian.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_mingguan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_mingguan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_bulanan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_bulanan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_insidentil.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_insidentil.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_pemuda.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_pemuda.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_umum.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_primary)))
            btn_umum.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)))

            btn_remaja.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_remaja.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_all.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_all.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            printEventUmur(recyclerView, "Umum")
        }

        btn_remaja.setOnClickListener {
            btn_harian.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_harian.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_mingguan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_mingguan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_bulanan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_bulanan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_insidentil.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_insidentil.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_pemuda.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_pemuda.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_umum.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_umum.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_remaja.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_primary)))
            btn_remaja.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)))

            btn_all.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_all.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            printEventUmur(recyclerView, "Remaja")
        }

        btn_all.setOnClickListener {
            btn_harian.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_harian.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_mingguan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_mingguan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_bulanan.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_bulanan.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_insidentil.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_insidentil.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_pemuda.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_pemuda.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_umum.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_umum.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_remaja.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_not_active)))
            btn_remaja.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))

            btn_all.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.btn_primary)))
            btn_all.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.white)))

            printEvent(recyclerView)
            category = ""
        }

        initEvent(recyclerView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Create a MutableList and add the query to it
                val queryList = mutableListOf<String>()
                queryList.add(query)

                // Use the MutableList in your search function
                when (category) {
                    "" -> {
                        searchEventAll(recyclerView, queryList)
                    }
                    "Harian", "Mingguan", "Bulanan", "Tahunan" -> {
                        searchEvent(recyclerView, queryList, category)
                    }
                    else -> {
                        searchEventUmur(recyclerView, queryList, category)
                    }
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // This method is called when the text in the SearchView changes
                // Add your code here to run a function when the text changes
//                searchEvent(recyclerView, newText, category)

                // Create a MutableList and add the query to it
                val queryList = mutableListOf<String>()
                queryList.add(newText)

                // Use the MutableList in your search function
                when (category) {
                    "" -> {
                        searchEventAll(recyclerView, queryList)
                    }
                    "Harian", "Mingguan", "Bulanan", "Tahunan" -> {
                        searchEvent(recyclerView, queryList, category)
                    }
                    else -> {
                        searchEventUmur(recyclerView, queryList, category)
                    }
                }

                return false
            }
        })


    }

    private fun searchEvent(recyclerView: RecyclerView, query: MutableList<String>, kategori: String) {
        val layoutManager = GridLayoutManager(context, 2)
        val predicate: (home_page_recyclerView_Data) -> Boolean = { item ->
            query.any { it.lowercase() in item.Name.lowercase() }
        }
        val predicate2: (home_page_recyclerView_Data) -> Boolean = { item ->
            item.category == kategori
        }

        val datalistFilter = datalist.filter(predicate2)
        val datalistFilter2 = datalistFilter.filter(predicate)

        recyclerView.layoutManager = layoutManager
        adapter = rvHome_Adapter(datalistFilter2 as ArrayList<home_page_recyclerView_Data>)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : rvHome_Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                println("TOASTNYA DISINI")

                home_active.passModel(datalistFilter2[position])
            }
        })

    }

    private fun searchEventUmur(recyclerView: RecyclerView, query: MutableList<String>, kategori: String) {
        val layoutManager = GridLayoutManager(context, 2)
        val predicate: (home_page_recyclerView_Data) -> Boolean = { item ->
            query.any { it.lowercase() in item.Name.lowercase() }
        }
        val predicate2: (home_page_recyclerView_Data) -> Boolean = { item ->
            item.kategoriPeserta == kategori
        }

        val datalistFilter = datalist.filter(predicate2)
        val datalistFilter2 = datalistFilter.filter(predicate)

        recyclerView.layoutManager = layoutManager
        adapter = rvHome_Adapter(datalistFilter2 as ArrayList<home_page_recyclerView_Data>)
        adapter.setOnItemClickListener(object : rvHome_Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                println("TOASTNYA DISINI")

                home_active.passDetail(datalistFilter2[position])
            }
        })
    }

    private fun searchEventAll(recyclerView: RecyclerView, query: MutableList<String>) {
        val layoutManager = GridLayoutManager(context, 2)

        val predicate: (home_page_recyclerView_Data) -> Boolean = { item ->
            query.any { it.lowercase() in item.Name.lowercase() }
        }

        val datalistFilter = datalist.filter(predicate)

        recyclerView.layoutManager = layoutManager
        adapter = rvHome_Adapter(datalistFilter as ArrayList<home_page_recyclerView_Data>)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : rvHome_Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                println("TOASTNYA DISINI")

                home_active.passDetail(datalistFilter[position])
            }
        })

    }


    private fun printEvent(recyclerView: RecyclerView, kategori: String) {
        val layoutManager = GridLayoutManager(context, 2)

        val predicate: (home_page_recyclerView_Data) -> Boolean = { item ->
            item.category == kategori
        }

        val datalistFilter = datalist.filter(predicate)

        recyclerView.layoutManager = layoutManager
        adapter = rvHome_Adapter(datalistFilter as ArrayList<home_page_recyclerView_Data>)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : rvHome_Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                println("TOASTNYA DISINI")

                home_active.passDetail(datalistFilter[position])
            }
        })
    }

    private fun printEventUmur(recyclerView: RecyclerView, kategoriPeserta: String) {
        val layoutManager = GridLayoutManager(context, 2)
        val predicate: (home_page_recyclerView_Data) -> Boolean = { item ->
            item.kategoriPeserta == kategoriPeserta
        }

        val datalistFilter = datalist.filter(predicate)

        recyclerView.layoutManager = layoutManager
        adapter = rvHome_Adapter(datalistFilter as ArrayList<home_page_recyclerView_Data>)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : rvHome_Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                println("TOASTNYA DISINI")
                home_active.passDetail(datalistFilter[position])

            }
        })
    }

    private fun printEvent(recyclerView: RecyclerView) {
        val layoutManager = GridLayoutManager(context, 2)

        recyclerView.layoutManager = layoutManager
        adapter = rvHome_Adapter(datalist)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : rvHome_Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                println("TOASTNYA DISINI")
                home_active.passDetail(datalist[position])

            }
        })
    }

    private fun initEvent(recyclerView: RecyclerView) {
        val layoutManager = GridLayoutManager(context, 2)
        val db = Firebase.firestore
        val dbevent = db.collection("event")
        dbevent.get()
            .addOnSuccessListener { documents ->
                datalist.clear()
                _id.clear()
                _name.clear()
                _img.clear()
                _desc.clear()
                _category.clear()
                _date.clear()
                _location.clear()
                _maxPeserta.clear()
                _kategoriPeserta.clear()
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
                for (x in 0.._id.size - 1) {
                    val eventdata = home_page_recyclerView_Data(
                        _id[x],
                        _name[x],
                        _desc[x],
                        _category[x],
                        _date[x],
                        _location[x],
                        _maxPeserta[x],
                        _kategoriPeserta[x],
                        _img[x]
                    )
                    datalist.add(eventdata)
                }
                recyclerView.layoutManager = layoutManager
                adapter = rvHome_Adapter(datalist)
                recyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : rvHome_Adapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        println("TOASTNYA DISINI")
                        home_active.passDetail(datalist[position])
                    }
                })
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