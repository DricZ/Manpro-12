import android.content.DialogInterface
import android.content.Intent
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
import com.example.appmanprobaru.admin.HomeAdmin
import com.example.appmanprobaru.admin.HomeEvent
import com.example.appmanprobaru.admin.adapterEventAdmin
import com.example.appmanprobaru.admin.addEvent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class eventListAdmin : Fragment() {
    private lateinit var _rvHomeEventAdmin: RecyclerView

    private lateinit var datalist: ArrayList<HomeEvent>

    private var _id: MutableList<String> = emptyList<String>().toMutableList()

    private var _name: MutableList<String> = emptyList<String>().toMutableList()
    private var _img: MutableList<String> = emptyList<String>().toMutableList()
    private var _desc: MutableList<String> = emptyList<String>().toMutableList()
    private var _location: MutableList<String> = emptyList<String>().toMutableList()
    private var _date: MutableList<String> = emptyList<String>().toMutableList()
    private var _category: MutableList<String> = emptyList<String>().toMutableList()
    private var _maxPeserta: MutableList<String> = emptyList<String>().toMutableList()
    private var _kategoriPeserta: MutableList<String> = emptyList<String>().toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_event_list_admin, container, false)
        datalist = arrayListOf<HomeEvent>()

        _rvHomeEventAdmin = view.findViewById<RecyclerView>(R.id.rvEvent)
        val _btnAddAdmin = view.findViewById<Button>(R.id.addAdmin)

        _btnAddAdmin.setOnClickListener {
            val newFragment = addEvent()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.Main_fragment_admin, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        datainit()
        return view
    }

    private fun datainit() {
// val layoutManager = GridLayoutManager(2)

        val db = Firebase.firestore
        var dbevent = db.collection("event")
        dbevent.get()
            .addOnSuccessListener { documents ->
                datalist.clear()
                _id.clear()
                _name.clear()
                _img.clear()
                _date.clear()
                for (document in documents) {
                    _id.add(document.id.toString())
                    _name.add(document.data["name"].toString())
                    _img.add(document.data["imgloc"].toString())
                    _date.add(
                        ((document.data["date"] as com.google.firebase.Timestamp).toDate().toString())
                    )
                }
                for (x in 0.._id.size - 1) {
                    val arrayDate: List<String> = _date[x].split(" ")
                    val eventdata = HomeEvent(
                        _id[x],
                        _img[x],
                        _name[x],
                        arrayDate[1] + " " + arrayDate[2] + " " + arrayDate[5],
                        arrayDate[3] + " " + arrayDate[4]
                    )
                    datalist.add(eventdata)

                }
                val adapterE = adapterEventAdmin(datalist)
                _rvHomeEventAdmin.layoutManager = LinearLayoutManager(context)
                _rvHomeEventAdmin.adapter = adapterEventAdmin(datalist)

                adapterE.setOnItemClickCallback(object : adapterEventAdmin.OnItemClickCallback{
                    override fun delData(pos: Int) {
                        AlertDialog.Builder(context!!)
                            .setTitle("HAPUS DATA")
                            .setMessage("APAKAH BENAR DATA "+datalist.get(pos).title+" akan dihapus ?")
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
