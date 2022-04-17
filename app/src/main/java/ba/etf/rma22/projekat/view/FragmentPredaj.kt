package ba.etf.rma22.projekat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.statusAnkete
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentPredaj : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var anketa: Anketa = Anketa("", "", Date(122, 1, 1), Date(122, 1, 1),
        Date(122,1,1), 0, "1", 0F, statusAnkete.AKTIVAN_NIJE_URADEN)
    private lateinit var progresTekst: TextView
    private lateinit var dugmePredaj: Button


    fun getArgs(izabranaAnketa: Anketa): FragmentPredaj{
        val args = Bundle()
        val fragment = FragmentPredaj()
        anketa = izabranaAnketa
        fragment.arguments = args
        return fragment
    }
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
        return inflater.inflate(R.layout.fragment_predaj, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progresTekst = view.findViewById(R.id.progresTekst)
        dugmePredaj = view.findViewById(R.id.dugmePredaj)
        progresTekst.setText((anketa.progress*100).toString() + "%")
        dugmePredaj.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                anketa = Anketa(anketa.naziv, anketa.nazivIstrazivanja, anketa.datumPocetka, anketa.datumKraj,
                    anketa.datumRada, anketa.trajanje, anketa.nazivGrupe, anketa.progress, statusAnkete.AKTIVAN_URADEN)
                (activity as MainActivity).closeAnketa()
            }
        })
    }



    companion object {
        fun newInstance() = FragmentPredaj
    }
}