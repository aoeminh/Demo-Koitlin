package minhnq.gvn.com.demokotlin.connection

class ServiceUtils {
companion object{
    var BASE_URL: String = "https://aoeminh1994.000webhostapp.com/ServerBeautyImage/"

    fun getAPIService():APIService{
        return RetrofitClient.getRetrofit(BASE_URL)!!.create(APIService::class.java)
    }
}


}