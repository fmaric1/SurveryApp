package ba.etf.rma22.projekat.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel

private const val ARG_PARAM1 = "param1"

class FragmentAnkete : Fragment() {
    private var param1: String? = null
    private lateinit var listener: (CharSequence)->Unit
    private lateinit var ankete: RecyclerView
    private lateinit var anketeAdapter: AnketaListAdapter
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<CharSequence>
    private var anketaListViewModel = AnketaListViewModel()

    override fun onCreate(savedInstantceState: Bundle?){
        super.onCreate(savedInstantceState)
        arguments?.let{
            param1 = it.getString(ARG_PARAM1)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_ankete, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ankete = view.findViewById(R.id.listaAnketa)
        spinner = view.findViewById(R.id.filterAnketa)

        adapter = ArrayAdapter.createFromResource(activity?.baseContext!!, R.array.spinner, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
        ankete.layoutManager = GridLayoutManager(activity, 2)
        anketeAdapter = AnketaListAdapter(listOf())
        ankete.adapter = anketeAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                anketeAdapter.updateAnkete(anketaListViewModel.getMyAnkete())
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when (position) {
                    0 -> anketeAdapter.updateAnkete(anketaListViewModel.getMyAnkete())
                    1 -> anketeAdapter.updateAnkete(anketaListViewModel.getAll())
                    2 -> anketeAdapter.updateAnkete(anketaListViewModel.getDone())
                    3 -> anketeAdapter.updateAnkete(anketaListViewModel.getFuture())
                    4 -> anketeAdapter.updateAnkete(anketaListViewModel.getNotTaken())
                }
            }


        }

    }

    override fun onResume() {
        (activity as MainActivity).refreshSecondFragmentBack()
        super.onResume()
    }
    companion object{
        fun newInstance(param1: String, listener: (CharSequence)->Unit) =
            FragmentAnkete().apply {
                this.listener = listener

            }
    }
}