package ba.etf.rma22.projekat



import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import ba.etf.rma22.projekat.view.FragmentAnkete
import ba.etf.rma22.projekat.view.FragmentPoruka
import ba.etf.rma22.projekat.view.ViewPagerAdapter
import kotlinx.coroutines.launch
import java.lang.Exception


class MainActivity : AppCompatActivity() {


    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val anketaListViewModel = AnketaListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            lifecycleScope.launch { anketaListViewModel.dajPodatkeSaWebServisa() }
        }catch (e: Exception){

        }
        setContentView(R.layout.activity_main)
        val uri: Uri? = getIntent().getData()
        if(uri != null ){

            val payload: String? = intent.getStringExtra("payload")
            if(payload != null )
            AccountRepository.postaviHash(payload)
            try {
                lifecycleScope.launch { anketaListViewModel.dajPodatkeSaWebServisa() }
            }catch (e: Exception){

            }

        }

        viewPager = findViewById(R.id.pager)
        val fragments =
            mutableListOf(
                    FragmentAnkete(),
                    FragmentIstrazivanje()
            )

        viewPager.offscreenPageLimit = 2
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragments, lifecycle)
        viewPager.adapter = viewPagerAdapter


    }



    public fun refreshSecondFragment(fragment: Fragment) {
        Handler(Looper.getMainLooper()).postDelayed({
            viewPagerAdapter.refreshFragment(1, fragment)
        }, 0)
        Handler(Looper.getMainLooper()).postDelayed({
            viewPagerAdapter.refreshFragment(0, FragmentAnkete())
        }, 0)
    }
    public fun refreshSecondFragmentBack(){
        Handler(Looper.getMainLooper()).postDelayed({
            viewPagerAdapter.refreshFragment(1,FragmentIstrazivanje())
        }, 0)
    }

    public fun openAnketa(fragmentiPitanja: List<Fragment>){
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentiPitanja.toMutableList(), lifecycle)
        viewPager.adapter = viewPagerAdapter
        viewPager.setCurrentItem(1)
        viewPager.setCurrentItem(0)
    }
    public fun closeAnketeUnfinished(){
        val fragments =
                mutableListOf(
                        FragmentAnkete(),
                        FragmentIstrazivanje()
                )
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragments, lifecycle)
        viewPager.adapter = viewPagerAdapter
    }
    public fun closeAnketa(nazivAnkete: String, nazivIstrazivanja: String){
        val fragment = FragmentPoruka()
        fragment.getArgs(nazivAnkete, nazivIstrazivanja, 1)
        val fragments =
            mutableListOf(
                FragmentAnkete(),
                fragment
            )
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragments, lifecycle)
        viewPager.adapter = viewPagerAdapter
        viewPager.setCurrentItem(1)
    }


}