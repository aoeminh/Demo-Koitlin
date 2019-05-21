package minhnq.gvn.com.demokotlin.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_base.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.activity.MainActivity
import minhnq.gvn.com.demokotlin.adapter.BaseAdapter
import minhnq.gvn.com.demokotlin.contract.IListImageHomePresenter
import minhnq.gvn.com.demokotlin.contract.IListImageHomeView
import minhnq.gvn.com.demokotlin.model.Image
import minhnq.gvn.com.demokotlin.presenter.ListImageHomePresenter
import minhnq.gvn.com.demokotlin.utils.ItemOffsetDecoration
import android.widget.AbsListView.OnScrollListener as OnScrollListener1

open abstract class BaseFragment() : Fragment(),IListImageHomeView{

    companion object {
        var FRAGMENT_NEW: Int = 1
        var FRAGMENT_HOT: Int = 1
        var FRAGMENT_POPULAR: Int = 1
    }

    var listImage: ArrayList<Image> = arrayListOf()
    var skip: Int = 0
    var take: Int = 10
    var isLoading: Boolean = false
    var isLoadMore: Boolean = false
    var mainActivity: MainActivity? = null
    var fragmentType: Int = 0
    var adapter: BaseAdapter? = null
    var presenter:IListImageHomePresenter? = null
    var progressDialog:ProgressDialog? =null
    var progressBar: ProgressBar?=null
    var recyclerView: RecyclerView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity is MainActivity) {
            mainActivity = activity as MainActivity
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_base, container, false)
        setHasOptionsMenu(true)
        fragmentType = getTypeFragment()
        progressBar = view.findViewById(R.id.progress_bar_base_fragment)
        recyclerView = view.findViewById(R.id.rv_base_fragment)
        if(listImage.size==0){
            progressBar?.visibility = View.VISIBLE
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        presenter = ListImageHomePresenter(this)
        presenter?.getListImageHome(fragmentType,skip,take)
    }

    abstract fun getTypeFragment(): Int

    override fun onResponseListImageHome(listImage: ArrayList<Image>?) {
        progressBar?.visibility = View.GONE
        adapter?.appendList(listImage)
        hideDiaLog()
        //isLoadMore = listImage?.size != 0
    }

    fun setRecyclerView() {
        rv_base_fragment.visibility = View.VISIBLE
        adapter = BaseAdapter(mainActivity, listImage)
        val gridLayoutManager = GridLayoutManager(activity,2)
        val itemDecoration = ItemOffsetDecoration(activity,R.dimen.image_list_spacing)
        rv_base_fragment.addItemDecoration(itemDecoration)
        rv_base_fragment.layoutManager = gridLayoutManager
        rv_base_fragment.adapter=adapter
//        setScrollAction()
    }

    fun setScrollAction(){
        rv_base_fragment.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = GridLayoutManager(activity,2)
                val position = gridLayoutManager.findLastVisibleItemPosition()
                if(isLoadMore){
                    //if loading in progress not excite loadmore
                    if(!isLoading){
                        //condition to loadmore
                        if(listImage.size -1 == position){
                            showDialog()
                            skip+=take
                            isLoadMore =true
                            isLoading =true
                            Log.d("position","on loadmore" + " skip " + skip );

                        }
                    }
                }
            }
        })
    }

    fun showDialog(){
        progressDialog = ProgressDialog(activity)
        progressDialog?.setMessage(resources.getString(R.string.base_fragment_progress_bar_message))
        progressDialog?.setCancelable(true)
        progressDialog?.show()
    }

    fun hideDiaLog(){
        if(progressDialog !=null){
            progressDialog?.dismiss()
        }
    }
}