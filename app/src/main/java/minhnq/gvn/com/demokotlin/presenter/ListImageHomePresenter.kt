package minhnq.gvn.com.demokotlin.presenter

import android.util.Log
import minhnq.gvn.com.demokotlin.connection.ServiceUtils
import minhnq.gvn.com.demokotlin.contract.IListImagePresenter
import minhnq.gvn.com.demokotlin.contract.IListImageView
import minhnq.gvn.com.demokotlin.model.Image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListImageHomePresenter(var view: IListImageView): IListImagePresenter {

    override fun getListImage(categoryImage: Int, skip: Int, take: Int) {
        ServiceUtils.getAPIService().getListImage(categoryImage,skip,take).enqueue(object : Callback<ArrayList<Image>>{
            override fun onFailure(call: Call<ArrayList<Image>>, t: Throwable) {
                Log.d("ListImageHomePresenter","error " + t.message)
            }

            override fun onResponse(call: Call<ArrayList<Image>>, response: Response<ArrayList<Image>>) {
                if(response.isSuccessful){
                    view.onResponseListImage(response.body())
                    Log.d("ListImageHomePresenter"," success " + response.body()?.size )
                }
            }


        })


    }
}