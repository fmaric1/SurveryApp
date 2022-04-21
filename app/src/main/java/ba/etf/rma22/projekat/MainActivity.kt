package ba.etf.rma22.projekat



import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.view.FragmentAnkete
import ba.etf.rma22.projekat.view.FragmentPoruka
import ba.etf.rma22.projekat.view.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_ankete.*


class MainActivity : AppCompatActivity() {


    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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