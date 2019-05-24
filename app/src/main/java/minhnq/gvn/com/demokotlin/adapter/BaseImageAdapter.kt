package minhnq.gvn.com.demokotlin.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.loadmore.view.*
import kotlinx.android.synthetic.main.row_item_base_fragment.view.*
import minhnq.gvn.com.demokotlin.R
import minhnq.gvn.com.demokotlin.action.IOnItemClick
import minhnq.gvn.com.demokotlin.model.Image
import java.lang.IllegalArgumentException

class BaseImageAdapter(var context: Context?, var listImage: ArrayList<Image?>?, var iOnItemClick: IOnItemClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        val VIEW_TYPE_ITEM = 0
        val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == VIEW_TYPE_ITEM){
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.row_item_base_fragment, viewGroup, false)
            return ViewHolder(view)
        }else if(viewType == VIEW_TYPE_LOADING){
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.loadmore, viewGroup, false)
            return ViewHolder(view)
        }
        throw IllegalArgumentException()

    }

    override fun getItemCount(): Int {
        return listImage!!.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if(viewHolder is ViewHolder){
            viewHolder.bindImage(listImage?.get(position))
            viewHolder.onClickItem()
        }else if (viewHolder is ViewHolderLoadMore){
            viewHolder.setLoadMore()
        }

    }

    override fun getItemViewType(position: Int): Int {
       if(listImage?.get(position)==null){
           return VIEW_TYPE_LOADING
       }else{
           return VIEW_TYPE_ITEM
       }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun onClickItem(){
            view.setOnClickListener(View.OnClickListener {
                iOnItemClick.onClickItem(adapterPosition)
            })
        }
        fun bindImage(image: Image?) {
            if (image !=null){
                context?.let {
                    Glide.with(it).load(image?.imageUrl).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            view.progress_bar_item_fragment_base.visibility=View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            view.progress_bar_item_fragment_base.visibility=View.GONE

                            return false
                        }

                    }).apply(RequestOptions.centerCropTransform().placeholder(R.drawable.default_image)).into(view.im_row_item_fragment1)
                    Log.d("tag", "aasdsd")
                }
            }


        }
    }

    inner class ViewHolderLoadMore(var view:View) :RecyclerView.ViewHolder(view){
        fun setLoadMore(){
            view.itemProgressbar.isIndeterminate = true
        }


    }

    fun appendList(listImage: ArrayList<Image>?){
        listImage?.let { this.listImage?.addAll(it) }
        notifyDataSetChanged()
    }
}

