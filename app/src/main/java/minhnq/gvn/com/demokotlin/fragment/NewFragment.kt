package minhnq.gvn.com.demokotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class NewFragment(): BaseFragment() {
    override fun getTypeFragment(): Int =FRAGMENT_NEW

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}