package minhnq.gvn.com.demokotlin.fragment

import android.media.audiofx.BassBoost
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import kotlinx.android.synthetic.main.fragment_list_image.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.activity.MainActivity
import minhnq.gvn.com.demokotlin.adapter.ListImageAdapter
import minhnq.gvn.com.demokotlin.model.Image

open class ListImagFragment(): Fragment() {

    var adapter: ListImageAdapter? =null
    var mainActivity: MainActivity? =null
    var position: Int? = null
    var image: Image? =null
    var isFavorite: Int =0
    var listImage: ArrayList<Image> = arrayListOf()
    var listImageFavorite: ArrayList<Image>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(activity is MainActivity){
            mainActivity = activity as MainActivity
        }
        if(arguments !=null){
            position = arguments!!.getInt(BaseCategoryFragment.EXTRA_POSITION)
            listImage = arguments!!.getParcelableArrayList(BaseCategoryFragment.EXTRA_LIST_IMAGE)
        }


        getListFavorite()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_list_image,container,false)
        setHasOptionsMenu(true)
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListImageAdapter(listImage,mainActivity!!.mFragmentmanager)
        view_pager_list_image_fragment.adapter = adapter
        view_pager_list_image_fragment.currentItem = position!!
        view_pager_list_image_fragment.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                getListFavorite()
                image = listImage[p0]
            }
        } )
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.item_toolbar_list_image,menu)

    }

    fun getListFavorite(){
        listImageFavorite = mainActivity?.imageDBOpenHelper?.getAllImage()
    }

    fun setUpToolbar(){
        var menu: Menu = toolbar_list_image_fragment.menu
        var menuItem: MenuItem = menu.findItem(R.id.item_favorite)
        if(listImageFavorite != null && image != null){
            listImageFavorite!!.forEach {
                if(it.imageName.equals(image!!.imageName)){
                    isFavorite = it.isFavorite;
                    if(isFavorite== 1){
                        menuItem.setIcon(R.drawable.ic_favorite_red);
                        return;

                    }else {
                        menuItem.setIcon(R.drawable.ic_favorite_white);

                        return;
                    }
                }else {
                    isFavorite = 0;
                    menuItem.setIcon(R.drawable.ic_favorite_white);
                }
            }

        }

    }


}