package ba.etf.rma22.projekat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel
import ba.etf.rma22.projekat.view.AnketaListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var ankete: RecyclerView
    private lateinit var anketeAdapter: AnketaListAdapter
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<CharSequence>
    private lateinit var upis: FloatingActionButton
    private var anketaListViewModel = AnketaListViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ankete = findViewById(R.id.listaAnketa)
        spinner = findViewById(R.id.filterAnketa)
        upis = findViewById(R.id.upisDugme)

        adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
        ankete.layoutManager = GridLayoutManager(this,2)
        anketeAdapter = AnketaListAdapter(listOf())
        ankete.adapter = anketeAdapter
        spinner.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                anketeAdapter.updateAnkete(anketaListViewModel.getMyAnkete())
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when(position){
                    0 ->   anketeAdapter.updateAnkete(anketaListViewModel.getMyAnkete())
                    1 ->   anketeAdapter.updateAnkete(anketaListViewModel.getAll())
                    2 ->   anketeAdapter.updateAnkete(anketaListViewModel.getDone())
                    3 ->   anketeAdapter.updateAnkete(anketaListViewModel.getFuture())
                    4 ->   anketeAdapter.updateAnkete(anketaListViewModel.getNotTaken())
                }
            }


        }
        upis.setOnClickListener {
            val intent = Intent(this, UpisIstrazivanje::class.java)
            startActivityForResult(intent, 1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                anketeAdapter.updateAnkete(anketaListViewModel.getMyAnkete())
            }
        }



    }
}
