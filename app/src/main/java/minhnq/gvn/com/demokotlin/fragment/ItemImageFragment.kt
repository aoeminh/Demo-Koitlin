package minhnq.gvn.com.demokotlin.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class ItemImageFragment(): Fragment(),View.OnClickListener {

    var imageItem: Image? = null

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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_item_image,container,false)
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
                        progressbar_item_image.visibility = View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar_item_image.visibility = View.GONE
                        return false                    }

                }).apply(RequestOptions.centerCropTransform().placeholder(R.drawable.default_image))
                .into(img_detail_fragment)
        }
    }
    override fun onClick(v: View?) {


    }


}
