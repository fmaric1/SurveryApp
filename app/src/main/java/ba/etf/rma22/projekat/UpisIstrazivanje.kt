package ba.etf.rma22.projekat


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel
import java.util.*

class UpisIstrazivanje : AppCompatActivity() {

    private lateinit var godinaSpinner: Spinner
    private lateinit var istrazivanjeSpinner: Spinner
    private lateinit var grupaSpinner: Spinner
    private lateinit var upisDugme: Button
    private var anketaListViewModel = AnketaListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_istrazivanja)
        godinaSpinner = findViewById(R.id.odabirGodina)
        istrazivanjeSpinner = findViewById(R.id.odabirIstrazivanja)
        grupaSpinner = findViewById(R.id.odabirGrupa)
        upisDugme = findViewById(R.id.dodajIstrazivanjeDugme)
        var godine = listOf<String>(" ", "1", "2", "3", "4", "5")
        godinaSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, godine)
        godinaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val istrazivanja: ArrayList<String> = ArrayList()
                if(position ==0)
                    upisDugme.isEnabled = false
                else{
                    var data = anketaListViewModel.getIstrazivanjaByGodina(position)

                    istrazivanja.add("")
                    for (x in data) {
                        istrazivanja.add(x.naziv)
                    }

                    istrazivanjeSpinner.adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, istrazivanja)
                    grupaSpinner.adapter = null
                    istrazivanjeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val grupe: ArrayList<String> = ArrayList()

                            var data1 = anketaListViewModel.getGrupe(istrazivanja[position])

                            grupe.add("")
                            for (x in data1) {
                                grupe.add(x.naziv)
                            }
                            grupaSpinner.adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, grupe)
                            grupaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    TODO("Not yet implemented")
                                }

                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                    if(position == 0)
                                        upisDugme.isEnabled = false
                                    else{
                                        upisDugme.isEnabled = true
                                        upisDugme.setOnClickListener {
                                            val godinaOdabir: Int = godinaSpinner.selectedItemPosition
                                            val istrazivanjeOdabir: Int = istrazivanjeSpinner.selectedItemPosition
                                            val grupaOdabir: Int = grupaSpinner.selectedItemPosition
                                            if(grupaSpinner.selectedItemPosition > 0) {
                                                anketaListViewModel.upisiStudenta(godinaOdabir.toString(), data[istrazivanjeOdabir-1], data1[grupaOdabir-1])
                                            }
                                            val intent: Intent = getIntent()
                                            val resultIntent: Intent = Intent()
                                            setResult(Activity.RESULT_OK, resultIntent)
                                            finish()
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

}