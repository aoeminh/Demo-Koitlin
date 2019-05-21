package minhnq.gvn.com.demokotlin.connection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object{
        var gson: Gson= GsonBuilder().setLenient().create()
        private var retrofit: Retrofit?= null
        fun getRetrofit(baseUrl: String):Retrofit?{
            retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit
        }
    }

}