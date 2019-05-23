package minhnq.gvn.com.demokotlin.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_base.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.action.IOnItemClick
import minhnq.gvn.com.demokotlin.activity.MainActivity
import minhnq.gvn.com.demokotlin.adapter.BaseImageAdapter
import minhnq.gvn.com.demokotlin.contract.IListImagePresenter
import minhnq.gvn.com.demokotlin.contract.IListImageView
import minhnq.gvn.com.demokotlin.model.Image
import minhnq.gvn.com.demokotlin.presenter.ListImageHomePresenter
import minhnq.gvn.com.demokotlin.utils.ItemOffsetDecoration
import android.widget.AbsListView.OnScrollListener as OnScrollListener1

open abstract class BaseFragment() : Fragment(),IListImageView,IOnItemClick{

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
    var adapter: BaseImageAdapter? = null
    var presenter:IListImagePresenter? = null
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
        presenter?.getListImage(fragmentType,skip,take)
    }

    abstract fun getTypeFragment(): Int

    override fun onResponseListImage(listImage: ArrayList<Image>?) {
        progressBar?.visibility = View.GONE
        adapter?.appendList(listImage)
        hideDiaLog()
        isLoading = false
        isLoadMore = !(listImage?.size!! < take)
    }

    override fun onClickItem(position: Int) {
        var listImagFragment = ListImagFragment()
        var bundle = Bundle()
        bundle.putParcelableArrayList(BaseCategoryFragment.EXTRA_LIST_IMAGE,listImage)
        bundle.putInt(BaseCategoryFragment.EXTRA_POSITION,position)
        listImagFragment.arguments  =bundle
        mainActivity?.addFragment(listImagFragment)


    }

    fun setRecyclerView() {
        rv_base_fragment.visibility = View.VISIBLE
        adapter = BaseImageAdapter(mainActivity, listImage,this)
        val gridLayoutManager = GridLayoutManager(activity,2)
        val itemDecoration = ItemOffsetDecoration(activity,R.dimen.image_list_spacing)
        rv_base_fragment.addItemDecoration(itemDecoration)
        rv_base_fragment.layoutManager = gridLayoutManager
        rv_base_fragment.adapter=adapter
        setScrollAction()
    }

    fun setScrollAction(){
        rv_base_fragment.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = rv_base_fragment.layoutManager as GridLayoutManager
                val position = gridLayoutManager.findLastVisibleItemPosition()
                if( isLoadMore){
                    //if loading in progress not excite loadmore
                    if(!isLoading){
                        //condition to loadmore
                        if(listImage.size -1 == position){
                            showDialog()
                            skip+=take
                            isLoadMore =true
                            isLoading =true
                            presenter?.getListImage(fragmentType ,skip,take)
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