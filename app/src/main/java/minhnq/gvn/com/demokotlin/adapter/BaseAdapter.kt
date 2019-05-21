package minhnq.gvn.com.demokotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestCoordinator
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.row_item_base_fragment.view.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.model.Image

class BaseAdapter(var context: Context?, var listImage: ArrayList<Image>) :
    RecyclerView.Adapter<BaseAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.row_item_base_fragment, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindImage(listImage[position])

    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bindImage(image: Image) {
            context?.let {
                Glide.with(it).load(image.imageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false

                    }

                }).apply(RequestOptions.centerCropTransform().placeholder(R.drawable.default_image)).into(view.im_row_item_fragment1)
                Log.d("tag", "aasdsd")
            }
        }
    }

    fun appendList(listImage: ArrayList<Image>?){
        val indes: Int =this.listImage.size
        listImage?.let { this.listImage.addAll(it) }
        notifyDataSetChanged()
    }
}

