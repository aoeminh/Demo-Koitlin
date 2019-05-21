package minhnq.gvn.com.demokotlin.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.activity.MainActivity
import minhnq.gvn.com.demokotlin.adapter.ViewPagerAdapter

class HomeFragment(): Fragment() {

    var mainActivity: MainActivity? =null
    var adapter: ViewPagerAdapter? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(activity is MainActivity){
            mainActivity = activity as MainActivity?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view: View = inflater.inflate(R.layout.fragment_home,container,false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFragment()

    }

    override fun onStart() {
        super.onStart()
    }


    fun addFragment(){
        adapter = ViewPagerAdapter(mainActivity?.mFragmentmanager)
        adapter?.addFragment(NewFragment(),"New")
        adapter?.addFragment(HotFragment(),"Hot")
        adapter?.addFragment(PopularFragment(),"Popular")
        viewpager_main.adapter = adapter
        tabLayout.setupWithViewPager(viewpager_main)
    }
}