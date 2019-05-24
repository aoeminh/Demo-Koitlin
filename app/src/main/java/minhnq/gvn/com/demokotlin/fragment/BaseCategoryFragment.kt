package minhnq.gvn.com.demokotlin.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_base_category.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.action.IOnItemClick
import minhnq.gvn.com.demokotlin.activity.MainActivity
import minhnq.gvn.com.demokotlin.adapter.BaseImageAdapter
import minhnq.gvn.com.demokotlin.contract.IListImagePresenter
import minhnq.gvn.com.demokotlin.contract.IListImageView
import minhnq.gvn.com.demokotlin.model.Image
import minhnq.gvn.com.demokotlin.presenter.ListImageHomePresenter
import minhnq.gvn.com.demokotlin.utils.ItemOffsetDecoration

class BaseCategoryFragment(): Fragment(),IListImageView,IOnItemClick {


    companion object{
        val EXTRA_POSITION: String = "extra.position"
        val EXTRA_LIST_IMAGE: String = "extra.list.image"
        val TAG: String ="BaseCategoryFragment"
        val HOLDITEM = 2
    }

    var toolbar: android.support.v7.widget.Toolbar? = null
    var mainActivity: MainActivity? =null
    var adapter: BaseImageAdapter? = null
    var categoryType: Int? =0
    var skip: Int = 0
    var take: Int = 10
    var isLoading: Boolean = false
    var isLoadMore: Boolean = false
    var presneter: IListImagePresenter? =null
    var listImage: ArrayList<Image?>? = arrayListOf()
    var progressDialog:ProgressDialog? =null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(activity is MainActivity){
            mainActivity = activity as MainActivity
        }

        if(arguments !=null){
            categoryType = arguments?.getInt(MainActivity.EXTRA_CATEGORY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_base_category,container,false)
        setHasOptionsMenu(true)
        listImage?.clear()
        presneter = ListImageHomePresenter(this)
        toolbar = view.findViewById(R.id.toolbar_base_category_fragment)
        mainActivity?.setSupportActionBar(toolbar)
        progressBar  = view.findViewById(R.id.progress_bar_base_category_fragment)
        if(listImage?.size == 0){
            progressBar?.visibility= View.VISIBLE
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initToolbar(categoryType)
        presneter?.getListImage(categoryType!!,skip,take)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()
        inflater?.inflate(R.menu.item_toolbar,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return super.onOptionsItemSelected(item)
    }
    override fun onResponseListImage(listImage: ArrayList<Image>?) {
        progressBar?.visibility = View.GONE
        adapter?.appendList(listImage)
        isLoading = false
        isLoadMore = !(listImage?.size!! < take)

    }

    override fun onClickItem(position: Int) {
        var listImagFragment = ListImagFragment()
        var bundle = Bundle()
        bundle.putParcelableArrayList(BaseCategoryFragment.EXTRA_LIST_IMAGE,listImage)
        bundle.putInt(BaseCategoryFragment.EXTRA_POSITION,position)
        listImagFragment.arguments  =bundle
        mainActivity?.ifExistFragment(ListImagFragment.TAG)
        mainActivity?.addFragment(listImagFragment)

    }

    fun initRecyclerView(){
        rv_base_category_fragment.visibility = View.VISIBLE
        adapter = BaseImageAdapter(mainActivity,listImage,this)
        val gridLayoutManager = GridLayoutManager(mainActivity,2)
        val itemOffsetDecoration = ItemOffsetDecoration(mainActivity,R.dimen.image_list_spacing)
        rv_base_category_fragment.addItemDecoration(itemOffsetDecoration)
        rv_base_category_fragment.layoutManager  = gridLayoutManager
        rv_base_category_fragment.adapter =adapter
        setScrollAction()
    }

    fun setScrollAction(){
        rv_base_category_fragment.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = rv_base_category_fragment.layoutManager as GridLayoutManager
                val position = gridLayoutManager.findLastVisibleItemPosition()
                var totalItemCount = gridLayoutManager.itemCount
                Log.d("postion", "$position")
                if(isLoadMore){
                    //if loading in progress not excite loadmore
                    if(!isLoading){
                        //condition to loadmore
                        if(totalItemCount  <= position + HOLDITEM){
                            skip+=take
                            isLoadMore =true
                            isLoading =true
                            presneter?.getListImage(categoryType!! ,skip,take)

                            Log.d("position","on loadmore" + " skip " + skip );

                        }
                    }
                }
            }
        })
    }


    fun initToolbar(category: Int?){
        when(category){
            MainActivity.SKY -> mainActivity?.supportActionBar?.title = resources.getString(R.string.toolbar_sky_fragment)
            MainActivity.BEACH -> mainActivity?.supportActionBar?.title = resources.getString(R.string.toolbar_beach_fragment)
            MainActivity.WATERFALL -> mainActivity?.supportActionBar?.title = resources.getString(R.string.toolbar_water_fall_fragment)
            MainActivity.DESERT -> mainActivity?.supportActionBar?.title = resources.getString(R.string.toolbar_desert_fragment)
            MainActivity.ANIMAL -> mainActivity?.supportActionBar?.title = resources.getString(R.string.toolbar_animal_fragment)
            MainActivity.FLOWER -> mainActivity?.supportActionBar?.title = resources.getString(R.string.toolbar_flower_fragment)

        }


    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }





}