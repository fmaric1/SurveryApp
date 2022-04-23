package ba.etf.rma22.projekat.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.statusAnkete
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.AnketaRepository.Companion.updateProgressAnkete
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import kotlinx.android.synthetic.main.anketa_item.*
import kotlinx.android.synthetic.main.fragment_istrazivanja.*
import kotlinx.android.synthetic.main.fragment_pitanje.view.*


private const val ARG_PARAM1 = "param1"

class FragmentPitanje : Fragment() {
    private var param1: String? = null
    private lateinit var nazivPitanja: TextView
    private lateinit var listaOdgovora: ListView
    private lateinit var dugmeZaustavi: Button
    private lateinit var adapter: ArrayAdapter<String>
    private var pitanjeTekst: String = ""
    private var pitanjeOdgovori: List<String> = emptyList<String>()
    private var anketaNaziv: String = ""
    private var istrazivanjeNaziv: String = ""
    private var idPitanja: String = ""

    public fun getArgs(pitanje: Pitanje, anketa: String, istrazivanje: String): FragmentPitanje{
        val args = Bundle()
        val fragment = FragmentPitanje()
        idPitanja = pitanje.naziv
        pitanjeTekst = pitanje.tekst
        pitanjeOdgovori = pitanje.opcije
        anketaNaziv = anketa
        istrazivanjeNaziv = istrazivanje
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
        val view =  inflater.inflate(R.layout.fragment_pitanje, container, false)
        // Inflate the layout for this fragment
        nazivPitanja = view.findViewById(R.id.tekstPitanja)
        dugmeZaustavi = view.findViewById(R.id.dugmeZaustavi)
        listaOdgovora = view.findViewById(R.id.odgovoriLista)
        adapter = ArrayAdapter(
                activity?.baseContext!!,
                android.R.layout.simple_list_item_1,
                pitanjeOdgovori
        )

        nazivPitanja.setText(pitanjeTekst)
        listaOdgovora.adapter = adapter
        updateBojuOdogovora(idPitanja)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaOdgovora.onItemClickListener = object :  AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(AnketaRepository.dajStatusAnkete(anketaNaziv) == statusAnkete.AKTIVAN_NIJE_URADEN) {
                    PitanjeAnketaRepository.updateOdgovor(idPitanja, position + 1)
                    updateBojuOdogovora(idPitanja)
                    updateProgressAnkete(anketaNaziv, istrazivanjeNaziv)
                }
            }
        }

        dugmeZaustavi.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                (activity as MainActivity).closeAnketeUnfinished()
            }
        })





    }

    fun updateBojuOdogovora(idPitanja: String) {
        val odgovor = PitanjeAnketaRepository.dajOdgovor(idPitanja)
        var brojac: Int = 1

        for(x in listaOdgovora){
            var tv =  x.findViewById<TextView>(android.R.id.text1)
            if(brojac != odgovor)
                tv.setTextColor(Color.parseColor("#FF000000"))
            else
                tv.setTextColor(Color.parseColor("#0000FF"))
            brojac++
        }
    }


    companion object {
            fun newInstance(): FragmentPitanje = FragmentPitanje()

    }
}