package minhnq.gvn.com.demokotlin.connection

import android.media.Image
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.ArrayList

interface APIService {
    @FormUrlEncoded
    @POST("imagelist.php")
    fun getListImage(@Field("imageCategory") category:Int,
                     @Field("skip") skip: Int,
                     @Field("take") take: Int): Call<ArrayList<minhnq.gvn.com.demokotlin.model.Image>>
}