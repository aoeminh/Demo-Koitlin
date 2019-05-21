package minhnq.gvn.com.demokotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager?): FragmentPagerAdapter(fm) {

    var listFragment: ArrayList<Fragment> = arrayListOf()
    var listTitle :ArrayList<String>  = arrayListOf()

    override fun getItem(p0: Int): Fragment {
        return listFragment[p0]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    fun addFragment( fragment: Fragment, title: String){
        listFragment.add(fragment)
        listTitle.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle[position]
    }
}