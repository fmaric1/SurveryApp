package ba.etf.rma22.projekat



import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.ViewModel.AnketaListViewModel
import ba.etf.rma22.projekat.data.models.AppDatabase
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import ba.etf.rma22.projekat.view.FragmentAnkete
import ba.etf.rma22.projekat.view.FragmentPoruka
import ba.etf.rma22.projekat.view.ViewPagerAdapter
import kotlinx.coroutines.launch
import java.lang.Exception


class MainActivity : AppCompatActivity() {


    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var  anketaListViewModel : AnketaListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        anketaListViewModel = AnketaListViewModel(application)
        anketaListViewModel.postaviContext()
        if(isNetworkAvailable(applicationContext)) {
            try {
                lifecycleScope.launch { anketaListViewModel.dajPodatkeSaWebServisa() }
            } catch (e: Exception) {

            }
        }
        else {
            lifecycleScope.launch{anketaListViewModel.ucitajIzBaze()}
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

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
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