package ba.etf.rma22.projekat.view

import android.R.attr.button
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel
import ba.etf.rma22.projekat.data.models.Pitanje
import kotlinx.android.synthetic.main.fragment_istrazivanja.*
import kotlinx.android.synthetic.main.fragment_pitanje.view.*


private const val ARG_PARAM1 = "param1"

class FragmentPitanje : Fragment() {
    private var param1: String? = null
    private lateinit var nazivPitanja: TextView
    private lateinit var listaOdgovora: ListView
    private lateinit var dugmeZaustavi: Button
    private var anketaListViewModel = AnketaListViewModel()
    private lateinit var adapter: ArrayAdapter<String>
    private var pitanjeTekst: String = ""
    private var pitanjeOdgovori: List<String> = emptyList<String>()

    public fun getArgs(pitanje: Pitanje): FragmentPitanje{
        val args = Bundle()
        val fragment = FragmentPitanje()
        pitanjeTekst = pitanje.tekst
        pitanjeOdgovori = pitanje.opcije
        fragment.arguments = args
        return fragment
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pitanje, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nazivPitanja = view.findViewById(R.id.tekstPitanja)
        listaOdgovora = view.findViewById(R.id.odgovoriLista)
        dugmeZaustavi = view.findViewById(R.id.dugmeZaustavi)

        adapter = ArrayAdapter(
            activity?.baseContext!!,
            android.R.layout.simple_list_item_1,
            pitanjeOdgovori
        )
        nazivPitanja.setText(pitanjeTekst)
        listaOdgovora.adapter = adapter
        dugmeZaustavi.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                (activity as MainActivity).closeAnketa()
            }
        })




    }

    companion object {
            fun newInstance(): FragmentPitanje = FragmentPitanje()

    }
}