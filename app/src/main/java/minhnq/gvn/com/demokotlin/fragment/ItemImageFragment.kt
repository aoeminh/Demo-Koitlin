package minhnq.gvn.com.demokotlin.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_item_image.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.activity.MainActivity
import minhnq.gvn.com.demokotlin.model.Image
import minhnq.gvn.com.demokotlin.utils.GetImageAsyncTask

class ItemImageFragment(): Fragment(),View.OnClickListener,GetImageAsyncTask.IGetBitmap {


    var imageItem: Image? = null
    var progressBar: ProgressBar? = null
    var mainActivity: MainActivity? = null

    companion object{
        var EXTRA_IMAGE = "extra.image"
        fun newInstance(image: Image): ItemImageFragment{
            var itemImageFragment = ItemImageFragment()
            var bundle = Bundle()
            bundle.putParcelable(EXTRA_IMAGE,image)
            itemImageFragment.arguments = bundle
            return itemImageFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments !=null){
            imageItem  = arguments!!.getParcelable(EXTRA_IMAGE)

        }

        if(activity is MainActivity){
            mainActivity = activity as MainActivity
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_item_image,container,false)
        progressBar = view.findViewById(R.id.progressbar_item_image)
        return view
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        image_set_background.setOnClickListener(this)
        if(imageItem !=null){
            Glide.with(activity!!).load(imageItem!!.imageUrl)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar?.visibility = View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar?.visibility = View.GONE
                        return false                    }

                }).apply(RequestOptions.centerCropTransform().placeholder(R.drawable.default_image))
                .into(img_detail_fragment)
        }
    }

    override fun onClick(v: View?) {
        if(checkConnect()){
            if(mainActivity!!.isSetWallpaperSuccess){
                mainActivity?.isSetWallpaperSuccess = false
                val getImageAsyncTask = GetImageAsyncTask(mainActivity!!,this)
                getImageAsyncTask.execute(imageItem?.imageUrl)
                Toast.makeText(mainActivity,"Setting Wallpaper in progress.Please wait!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(mainActivity,"Setting Wallpaper in progress.Please wait!", Toast.LENGTH_SHORT).show()

            }

        }else{
            Toast.makeText(mainActivity,"No connection internet", Toast.LENGTH_SHORT).show()

        }

    }

    override fun getBitmapSuccess(isSuccess: Boolean) {
        mainActivity?.isSetWallpaperSuccess = true
    }

    fun checkConnect():Boolean{
        val connectivityManager: ConnectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo = connectivityManager.activeNetworkInfo
        return networkInfo.isConnected
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

    }
}
