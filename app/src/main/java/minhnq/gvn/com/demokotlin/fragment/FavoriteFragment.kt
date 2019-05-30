package minhnq.gvn.com.demokotlin.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import kotlinx.android.synthetic.main.fragment_favorite.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.action.IOnItemClick
import minhnq.gvn.com.demokotlin.action.OnLongClickItem
import minhnq.gvn.com.demokotlin.activity.MainActivity
import minhnq.gvn.com.demokotlin.adapter.BaseImageAdapter
import minhnq.gvn.com.demokotlin.model.Image
import minhnq.gvn.com.demokotlin.utils.ItemOffsetDecoration

class FavoriteFragment(): Fragment(), IOnItemClick,OnLongClickItem {


    companion object{
        val TAG: String = "FavoriteFragment"
    }
    var mainActivity: MainActivity? = null
    var favoriteList: ArrayList<Image?>? = arrayListOf()
    var toolbar: android.support.v7.widget.Toolbar? = null
    var adapter : BaseImageAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(activity is MainActivity){
            mainActivity = activity as MainActivity
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_favorite,container,false)
        setHasOptionsMenu(true)
        toolbar = view.findViewById(R.id.toolbar_favorite_fragment)
        mainActivity?.setSupportActionBar(toolbar)
        mainActivity?.supportActionBar?.setDisplayShowHomeEnabled(true)
        mainActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener({
            activity?.onBackPressed()
        })

        favoriteList = mainActivity?.imageDBOpenHelper?.getAllImage()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.item_tool_bar_favorite,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.item_delete){
            val alertDialogBuilder = AlertDialog.Builder(mainActivity!!)
            alertDialogBuilder.setMessage(getString(R.string.message_delete_all_image))
            alertDialogBuilder.setCancelable(true)
            alertDialogBuilder.setPositiveButton(getString(R.string.text_button_yes)){
                dialog, which ->  
                Toast.makeText(activity,getString(R.string.message_delete_success),Toast.LENGTH_SHORT).show()
                mainActivity?.imageDBOpenHelper?.deleteAll()
                favoriteList?.clear()
                adapter?.notifyDataSetChanged()
            }
            
            alertDialogBuilder.setNegativeButton(getString(R.string.text_button_no)){
                dialog, which ->  dialog.dismiss()
            }
            if(favoriteList?.size != 0){
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }




        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerview()
    }

    fun initRecyclerview(){
        adapter = BaseImageAdapter(mainActivity,favoriteList,this,this)
        var gridLayoutManager = GridLayoutManager(mainActivity,2)
        var itemDecoration = ItemOffsetDecoration(mainActivity,R.dimen.image_list_spacing)
        rv_favorite_fragment.addItemDecoration(itemDecoration)
        rv_favorite_fragment.layoutManager = gridLayoutManager
        rv_favorite_fragment.adapter =adapter

    }

    override fun onClickItem(position: Int) {
        var listImagFragment = ListImagFragment()
        var bundle = Bundle()
        bundle.putParcelableArrayList(BaseCategoryFragment.EXTRA_LIST_IMAGE,favoriteList)
        bundle.putInt(BaseCategoryFragment.EXTRA_POSITION,position)
        listImagFragment.arguments  =bundle
        mainActivity?.addFragmentFavorite(listImagFragment)

    }

    override fun onLongClickItem(position: Int) {
        Log.d("onlong","long")
        deleteImageDialog(position)


    }
    fun deleteImageDialog(position: Int){
        val alerdialogBuilder = AlertDialog.Builder(mainActivity!!)
        alerdialogBuilder.setMessage(getString(R.string.message_delete_image))
        alerdialogBuilder.setCancelable(true)
        alerdialogBuilder.setPositiveButton(R.string.text_button_yes){dialog, which ->
            mainActivity?.imageDBOpenHelper?.deleteImage(favoriteList?.get(position)!!)
            favoriteList?.remove(favoriteList?.get(position)!!)
            adapter?.notifyDataSetChanged()
         }
        alerdialogBuilder.setNegativeButton(R.string.text_button_no){dialog, which ->

            dialog.dismiss()
        }

        var alertDialog = alerdialogBuilder.create()
        alertDialog.show()


    }




}