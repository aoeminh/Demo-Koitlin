package minhnq.gvn.com.demokotlin.activity

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.database.ImageDBOpenHelper
import minhnq.gvn.com.demokotlin.fragment.BaseCategoryFragment
import minhnq.gvn.com.demokotlin.fragment.HomeFragment
import minhnq.gvn.com.demokotlin.fragment.ListImagFragment
import minhnq.gvn.com.demokotlin.connection.Connection as Connection

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    companion object{
        val SKY: Int = 1
        val BEACH: Int = 2
        val WATERFALL: Int = 3
        val DESERT: Int = 4
        val ANIMAL: Int = 5

        val FLOWER: Int = 6
        val EXTRA_CATEGORY: String = "extra.category"
    }

    var mNavigationView: NavigationView? = null
    var isSetWallpaperSucess: Boolean = true
    var connection: BroadcastReceiver? = null
    var mFragmentmanager: FragmentManager = supportFragmentManager
    var imageDBOpenHelper: ImageDBOpenHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerInternet()
        initView()
        addHomeFragment()
        initDB()

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connection)
    }

    override fun onBackPressed() {
        if(drawer_main.isDrawerOpen(GravityCompat.START)){
            drawer_main.closeDrawer(GravityCompat.START)
        }else{
            var count = mFragmentmanager.backStackEntryCount
            if(count == 0){
                super.onBackPressed()
            }else{
                mFragmentmanager.popBackStack()
            }
        }

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.navigation_sky -> replaceFragment(SKY)
            R.id.navigation_beach -> replaceFragment(BEACH)
            R.id.navigation_water_fall -> replaceFragment(WATERFALL)
            R.id.navigation_desert -> replaceFragment(DESERT)
            R.id.navigation_animal -> replaceFragment(ANIMAL)
            R.id.navigation_flower -> replaceFragment(FLOWER)

        }
        if(drawer_main.isDrawerOpen(GravityCompat.START)){
            drawer_main.closeDrawer(GravityCompat.START)
        }
        return  false
    }

    fun addHomeFragment(){
        val fragmentTransaction  = mFragmentmanager.beginTransaction()
        fragmentTransaction.add(R.id.frame_main, HomeFragment(),null)
        fragmentTransaction.commit()
    }

    fun replaceFragment(category: Int){
        var baseCategoryFragment = BaseCategoryFragment()
        val bundle = Bundle()
        bundle.putInt(EXTRA_CATEGORY, category)
        baseCategoryFragment.arguments = bundle
        ifExistFragment(BaseCategoryFragment.TAG)
        addFragment(baseCategoryFragment)

    }

    fun initView(){
        navigation_main.setNavigationItemSelectedListener(this)
    }


    fun addFragment(fragment: Fragment){
        val fragmentTransition = mFragmentmanager.beginTransaction()
        fragmentTransition.setCustomAnimations(R.anim.fragment_enter,R.anim.fragment_exit,R.anim.fragment_pop_enter,R.anim.fragment_pop_exit)
        fragmentTransition.add(R.id.frame_main,fragment)
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }
    fun registerInternet(){
        connection = Connection()
        val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connection,intent)
    }

    fun initDB(){
        imageDBOpenHelper = ImageDBOpenHelper(this,null)
    }

    fun ifExistFragment(tag: String){
        val listFragment = supportFragmentManager.fragments
        listFragment.forEach{
            if(tag.equals(BaseCategoryFragment.TAG)){
                if(it is BaseCategoryFragment){
                    supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
                }
            } else if (tag.equals(ListImagFragment.TAG)){
                if(it is ListImagFragment){
                    supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
                }
            }else if (tag.equals(ListImagFragment.TAG)){
                if(it is ListImagFragment){
                    supportFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
                }
            }
        }
    }

}
