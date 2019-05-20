package minhnq.gvn.com.demokotlin.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class Connection() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(!isConnect(context)){
            Toast.makeText(context,"No connection internet",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(context,"Internet connected",Toast.LENGTH_SHORT).show();

        }
    }

    fun isConnect(context: Context?):Boolean{
        val connectManager: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfos: NetworkInfo? = connectManager.activeNetworkInfo
            if(networkInfos != null && networkInfos.isConnected){
            return  true
        }
        return false
    }
}