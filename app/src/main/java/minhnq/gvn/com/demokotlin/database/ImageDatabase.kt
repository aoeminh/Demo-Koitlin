package minhnq.gvn.com.demokotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import minhnq.gvn.com.demokotlin.model.Image
import okhttp3.internal.Internal.instance

@Database( entities = arrayOf(Image::class),version = 1)
abstract class ImageDatabase() : RoomDatabase() {

    abstract fun imageDao() : ImageDAO

    companion object{
        fun getImageDatabase(context: Context):ImageDatabase{
            return Room.inMemoryDatabaseBuilder(context,ImageDatabase::class.java).allowMainThreadQueries().build()
        }
    }

}