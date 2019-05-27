package minhnq.gvn.com.demokotlin.fragment

import android.media.audiofx.BassBoost
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.*
import kotlinx.android.synthetic.main.fragment_list_image.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.activity.MainActivity
import minhnq.gvn.com.demokotlin.adapter.ListImageAdapter
import minhnq.gvn.com.demokotlin.model.Image

open class ListImagFragment(): Fragment() {

    companion object{
        val TAG: String = "ListImagFragment"
    }
    var adapter: ListImageAdapter? =null
    var mainActivity: MainActivity? =null
    var position: Int? = null
    var image: Image? =null
    var isFavorite: Int =0
    var listImage: ArrayList<Image> = arrayListOf()
    var listImageFavorite: ArrayList<Image?>? = arrayListOf()
    var toolbar: Toolbar? =null

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
        toolbar= view.findViewById(R.id.toolbar_list_image_fragment)
        mainActivity?.setSupportActionBar(toolbar)
        image =listImage[position!!]
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
                setUpToolbar()
            }
        } )
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.item_toolbar_list_image,menu)
        setUpToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //set icon
        if (item?.itemId == R.id.item_favorite) {
        if (isFavorite == 0) {
            isFavorite = 1
            item.setIcon(R.drawable.ic_favorite_red)
        } else {
            isFavorite = 0
            item.setIcon(R.drawable.ic_favorite_white)
        }

        //save in db
        if (image != null) {
            image?.isFavorite = isFavorite
            var isExist: Boolean = false
            listImageFavorite?.forEach {
                if (it!!.imageName.equals(image?.imageName)) {
                    if (isFavorite == 0) {
                        isExist = true
                        mainActivity?.imageDBOpenHelper?.deleteImage(image!!)
                    }
                }
            }
            if (!isExist) {
                if (isFavorite == 1) {
                    mainActivity?.imageDBOpenHelper?.addImage(image!!)
                }
            }
        }
    }



        return super.onOptionsItemSelected(item)
    }

    fun getListFavorite(){
        listImageFavorite = mainActivity?.imageDBOpenHelper?.getAllImage()
    }

    fun setUpToolbar(){
        var menu: Menu = toolbar!!.menu
        var menuItem: MenuItem = menu.findItem(R.id.item_favorite)
        if(listImageFavorite != null && image != null){
            listImageFavorite!!.forEach {
                if(it!!.imageName.equals(image!!.imageName)){
                    isFavorite = it!!.isFavorite;
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