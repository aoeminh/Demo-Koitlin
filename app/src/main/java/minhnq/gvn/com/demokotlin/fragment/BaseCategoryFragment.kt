package minhnq.gvn.com.demokotlin.fragment

import android.support.v4.app.Fragment

class BaseCategoryFragment(): Fragment() {

    companion object{
        val EXTRA_POSITION: String = "extra.position"
        val EXTRA_LIST_IMAGE: String = "extra.list.image"
    }
}