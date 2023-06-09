package ba.etf.rma22.projekat.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.AnketaRepository.Companion.updateProgressAnkete
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository

import kotlinx.coroutines.launch


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
    private var idAnketaTaken: Int = 0
    private lateinit var anketaListViewModel : AnketaListViewModel
    private var idPitanjaInt = 0

    public fun getArgs(pitanje: Pitanje, anketa: String, istrazivanje: String, anketaTaken: AnketaTaken?): FragmentPitanje{
        val args = Bundle()
        val fragment = FragmentPitanje()
        anketaListViewModel = AnketaListViewModel(requireActivity().application)
        idPitanja = pitanje.naziv
        pitanjeTekst = pitanje.tekstPitanja
        pitanjeOdgovori = listOf("","","")
        anketaNaziv = anketa
        istrazivanjeNaziv = istrazivanje
        if (anketaTaken != null) {
            idAnketaTaken = anketaTaken.id
            idPitanjaInt = anketaTaken.AnketumId
        }
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        updateBojuOdogovora(idPitanja)
        listaOdgovora.onItemClickListener = object :  AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(AnketaRepository.dajStatusAnkete(anketaNaziv) == statusAnkete.AKTIVAN_NIJE_URADEN) {
                    PitanjeAnketaRepository.updateOdgovor(idPitanja, position + 1)
                    lifecycleScope.launch {
                    OdgovorRepository.dodajNeposlani(idPitanjaInt, position + 1, idAnketaTaken)
                    }
                    updateBojuOdogovora(idPitanja, position + 1)
                    updateProgressAnkete(anketaNaziv, istrazivanjeNaziv)
                }
            }
        }
        dugmeZaustavi.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                lifecycleScope.launch{ anketaListViewModel.posaljiOdgovore()}
                (activity as MainActivity).closeAnketeUnfinished()
            }
        })


    }

    fun updateBojuOdogovora(idPitanja: String, pozicija: Int = 0) {
        //val odgovor = PitanjeAnketaRepository.dajOdgovor(idPitanja)

        var odgovor = pozicija
        var odgovori = anketaListViewModel.dajSveOdgovore()
        for(x in odgovori)
            if(x.idAnketaTaken == idAnketaTaken && x.id == idPitanjaInt)
                odgovor = x.odgovoreno
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

    override fun onResume() {
        updateBojuOdogovora(idPitanja)
        super.onResume()

    }





    companion object {
            fun newInstance(): FragmentPitanje = FragmentPitanje()

    }
}