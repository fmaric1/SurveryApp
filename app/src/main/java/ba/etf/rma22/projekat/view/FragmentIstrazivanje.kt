package ba.etf.rma22.projekat


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel
import ba.etf.rma22.projekat.view.FragmentPoruka
import java.util.*


private const val ARG_PARAM2 = "param2"

class FragmentIstrazivanje : Fragment() {
    private var upisiPressed: Boolean = false
    private var param2: String? = null
    private lateinit var listener: (CharSequence)->Unit
    private lateinit var godinaSpinner: Spinner
    private lateinit var istrazivanjeSpinner: Spinner
    private lateinit var grupaSpinner: Spinner
    private lateinit var upisDugme: Button
    private var anketaListViewModel = AnketaListViewModel()

    override fun onCreate(savedInstantceState: Bundle?){
        super.onCreate(savedInstantceState)
        arguments?.let{
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_istrazivanja, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        godinaSpinner = view.findViewById(R.id.odabirGodina)
        istrazivanjeSpinner = view.findViewById(R.id.odabirIstrazivanja)
        grupaSpinner = view.findViewById(R.id.odabirGrupa)
        upisDugme = view.findViewById(R.id.dodajIstrazivanjeDugme)
        var godine = listOf<String>(" ", "1", "2", "3", "4", "5")
        godinaSpinner.adapter = ArrayAdapter<String>(
                activity?.baseContext!!,
                android.R.layout.simple_spinner_item,
                godine
        )
        godinaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                val istrazivanja: ArrayList<String> = ArrayList()
                if(position ==0)
                    upisDugme.isEnabled = false
                else{
                    var data = anketaListViewModel.getIstrazivanjaByGodina(position)

                    istrazivanja.add("")
                    for (x in data) {
                        if(!anketaListViewModel.getUpisanaIstrazivanja().contains(x))
                            istrazivanja.add(x.naziv)
                    }

                    istrazivanjeSpinner.adapter = ArrayAdapter<String>(
                            activity?.baseContext!!,
                            android.R.layout.simple_spinner_item,
                            istrazivanja
                    )
                    grupaSpinner.adapter = null
                    istrazivanjeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                        ) {
                            val grupe: ArrayList<String> = ArrayList()

                            var data1 = anketaListViewModel.getGrupe(istrazivanja[position])

                            grupe.add("")
                            for (x in data1) {
                                grupe.add(x.naziv)
                            }
                            grupaSpinner.adapter = ArrayAdapter<String>(
                                    activity?.baseContext!!,
                                    android.R.layout.simple_spinner_item,
                                    grupe
                            )
                            grupaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    TODO("Not yet implemented")
                                }

                                override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                ) {
                                    if(position == 0)
                                        upisDugme.isEnabled = false
                                    else{
                                        upisDugme.isEnabled = true
                                        upisDugme.setOnClickListener {
                                            val godinaOdabir: Int = godinaSpinner.selectedItemPosition
                                            val istrazivanjeOdabir: Int = istrazivanjeSpinner.selectedItemPosition
                                            val grupaOdabir: Int = grupaSpinner.selectedItemPosition
                                            upisiPressed = true
                                            if(grupaSpinner.selectedItemPosition > 0) {
                                                anketaListViewModel.upisiStudenta(
                                                        godinaOdabir.toString(),
                                                        data[istrazivanjeOdabir - 1],
                                                        data1[grupaOdabir - 1]
                                                )
                                            }
                                            val fragment = FragmentPoruka.newInstance()
                                            fragment.getArgs(data1[grupaOdabir - 1].naziv, data[istrazivanjeOdabir - 1].naziv)


                                            (activity as MainActivity).refreshSecondFragment(fragment)


                                        }
                                    }
                                }


                            }


                        }

                    }
                }
            }


        }



    }
    companion object{
        fun newInstance(param1: String, listener: (CharSequence) -> Unit) =
                FragmentIstrazivanje().apply {
                    this.listener = listener
                }
    }

}