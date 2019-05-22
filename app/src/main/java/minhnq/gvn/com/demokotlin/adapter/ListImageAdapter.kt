package minhnq.gvn.com.demokotlin.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import minhnq.gvn.com.demokotlin.fragment.ItemImageFragment
import minhnq.gvn.com.demokotlin.model.Image

class ListImageAdapter(var list: ArrayList<Image>,var fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment {
        return ItemImageFragment.newInstance(list[p0])
    }

    override fun getCount(): Int {
        return list.size    }
}