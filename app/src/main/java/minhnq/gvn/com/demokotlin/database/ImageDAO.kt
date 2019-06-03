package minhnq.gvn.com.demokotlin.database

import android.arch.persistence.room.*
import android.database.Cursor
import minhnq.gvn.com.demokotlin.model.Image


@Dao
interface ImageDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserImage(image :Image)

    @Delete
    fun deleteImage(image :Image)

    @Query("DELETE  FROM image")
    fun deleteAll()

    @Query("SELECT * FROM image")
    fun getAllImage(): Cursor





}