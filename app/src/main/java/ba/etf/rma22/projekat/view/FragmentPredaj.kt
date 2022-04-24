package ba.etf.rma22.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.statusAnkete
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import java.time.LocalDate
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentPredaj : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var anketa: Anketa = Anketa("", "", Date(122, 1, 1), Date(122, 1, 1),
            Date(122, 1, 1), 0, "1", 0F, statusAnkete.AKTIVAN_NIJE_URADEN)
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
        anketa.progress = AnketaRepository.dajProgress(anketa.naziv)
        var progressInt:Int = 0
        if(anketa.progress<0.1)
            progressInt = 0
        else if(anketa.progress>=0.1 && anketa.progress<0.3)
            progressInt = 20
        else if(anketa.progress>=0.3 && anketa.progress<0.5)
            progressInt = 40
        else if(anketa.progress>=0.5 && anketa.progress<0.7)
            progressInt = 60
        else if(anketa.progress>=0.7 && anketa.progress<1.0)
            progressInt = 80
        else if(anketa.progress>=1.0)
            progressInt = 100

        progresTekst.setText(progressInt.toString() + "%")
        dugmePredaj.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (anketa.status == statusAnkete.AKTIVAN_NIJE_URADEN) {
                    AnketaRepository.updateStatusAnkete(anketa.naziv)
                    AnketaRepository.updateDatumRada(anketa.naziv)
                }
                (activity as MainActivity).closeAnketa(anketa.naziv, anketa.nazivIstrazivanja)
            }
        })
    }
    fun updateProgress(){
        var progressInt: Int = 0
        if(anketa.progress<0.1)
            progressInt = 0
        else if(anketa.progress>=0.1 && anketa.progress<0.3)
            progressInt = 20
        else if(anketa.progress>=0.3 && anketa.progress<0.5)
            progressInt = 40
        else if(anketa.progress>=0.5 && anketa.progress<0.7)
            progressInt = 60
        else if(anketa.progress>=0.7 && anketa.progress<1.0)
            progressInt = 80
        else if(anketa.progress>=1.0)
            progressInt = 100
        progresTekst.setText(progressInt.toString() + "%")

        refreshFragment(context)


    }

    private fun refreshFragment(context: Context?) {
        val fm = (context as? AppCompatActivity)?.supportFragmentManager
        fm?.let {
            val cf = fragmentManager?.findFragmentById(R.id.fragment_predaj)
            cf?.let {
                val ft = fragmentManager?.beginTransaction()
                ft?.detach(it)
                ft?.attach(it)
                ft?.commit()
            }
        }
    }

    override fun onResume() {
        updateProgress()
        super.onResume()
    }

    companion object {
        fun newInstance() = FragmentPredaj
    }
}