package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.R

private const val ARG_PARAM3 = "param3"

class FragmentPoruka : Fragment() {
    private var param3: String? = null
    private lateinit var tvPoruka: TextView
    private var arg1: String = ""
    private var arg2: String = ""
    private var tekstPoruke: String = ""

    public fun getArgs(g: String, ist: String, vrsta: Int): FragmentPoruka {
        val args = Bundle()
        val fragment = FragmentPoruka()
        arg1=g
        arg2 = ist
        if(vrsta == 0)
        tekstPoruke = "Uspješno ste upisani u grupu $arg1 istrazivanja $arg2!"
        else if(vrsta == 1)
            tekstPoruke = "Završili ste anketu $arg1 u okviru istraživanja $arg2"
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstantceState: Bundle?){
        super.onCreate(savedInstantceState)
        arguments?.let{
            param3 = it.getString(ARG_PARAM3)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_poruka, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvPoruka = view.findViewById(R.id.tvPoruka)
        tvPoruka.setText(tekstPoruke)

    }
    companion object {
        fun newInstance(): FragmentPoruka = FragmentPoruka()
    }
}