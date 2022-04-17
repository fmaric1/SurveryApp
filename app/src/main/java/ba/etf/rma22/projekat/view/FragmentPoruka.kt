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
    private var grupa: String = ""
    private var istrazivanje: String = ""

    public fun getArgs(g: String, ist: String): FragmentPoruka {
        val args = Bundle()
        val fragment = FragmentPoruka()
        grupa=g
        istrazivanje = ist
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

        var text: String =
            "Uspješno ste upisani u grupu $grupa istrazivanja $istrazivanje!"
        tvPoruka.setText(text)

    }
    companion object {
        fun newInstance(): FragmentPoruka = FragmentPoruka()
    }
}