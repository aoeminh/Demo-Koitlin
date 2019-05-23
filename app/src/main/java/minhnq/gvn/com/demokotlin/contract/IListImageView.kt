package minhnq.gvn.com.demokotlin.contract

import minhnq.gvn.com.demokotlin.model.Image

interface IListImageView {
    fun onResponseListImage(listImage: ArrayList<Image>?)
}