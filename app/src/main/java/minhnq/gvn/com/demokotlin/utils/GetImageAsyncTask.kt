package minhnq.gvn.com.demokotlin.utils

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class GetImageAsyncTask(var contextL: Context, var iGetBitmap: IGetBitmap): AsyncTask<String, Void, Bitmap>() {



    override fun doInBackground(vararg params: String?): Bitmap? {
        val strUrl = params[0]
        var bitmap: Bitmap? =null
        try {
            val url = URL(strUrl)
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

        }catch ( malform: MalformedURLException){
            malform.printStackTrace()
        }

        return bitmap
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
    setWallPaper(result)
    }

    interface IGetBitmap{
        fun getBitmapSuccess(isSuccess: Boolean)
    }

    fun setWallPaper(bitmap: Bitmap?){
        val metrics = DisplayMetrics()
        val windowManager = contextL
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(metrics)
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        val handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val success = msg.obj as Boolean
                if (success) {
                    iGetBitmap.getBitmapSuccess(success)
                    Toast.makeText(contextL, "Setting Wallpaper success", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(contextL)
        Thread(Runnable {
            wallpaperManager.setBitmap(bitmap)
            wallpaperManager.suggestDesiredDimensions(width,height)
            val message: Message = handler.obtainMessage()
            message.obj =true
            handler.sendMessage(message)

        }).start()
    }
}