package minhnq.gvn.com.demokotlin.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.MediaStore.Images.Thumbnails.IMAGE_ID
import android.util.Log
import minhnq.gvn.com.demokotlin.model.Image

class ImageDBOpenHelper(var context: Context,var factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME,factory,
    DATABASE_VERSION) {
    companion object{
        val DATABASE_NAME = "favorite_db"
        val DATABASE_VERSION =1
        val  TABLE_NAME: String = "favorite"
        val IMAGE_ID: String = "id";
        val IMAGE_NAME: String = "name";
        val IMAGE_URL: String = "url";
        val IMAGE_FAV: String = "favorite";
        val CREATE_TABLE: String =
            "CREATE TABLE $TABLE_NAME ($IMAGE_ID INTEGER PRIMARY KEY, $IMAGE_NAME TEXT, $IMAGE_URL TEXT, $IMAGE_FAV INTEGER)"
         val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun getAllImage():ArrayList<Image>{
        var sql= "SELECT * FROM $TABLE_NAME"
        var db: SQLiteDatabase = this.readableDatabase
        var arrayList : ArrayList<Image> = arrayListOf()
        var cursor: Cursor = db.rawQuery(sql,null)
        while (cursor.moveToNext()){
            var image: Image = Image(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                1  ,
                cursor.getInt(3)
                )
            arrayList.add(image)
        }
        db.close()
        return arrayList

    }

    fun addImage(image: Image){
        val db: SQLiteDatabase = this.readableDatabase
        val contentValues: ContentValues = ContentValues()
        contentValues.put(IMAGE_NAME,image.imageName)
        contentValues.put(IMAGE_URL,image.imageUrl)
        contentValues.put(IMAGE_FAV,image.isFavorite)
        val newRowId = db.insert(TABLE_NAME,null,contentValues)
        Log.d("tag"," $newRowId " );
    }

    fun deleteImage(image: Image){
        val db: SQLiteDatabase = this.readableDatabase
        db.delete(TABLE_NAME, "$IMAGE_NAME = ?",  arrayOf(image.imageUrl)   )
        db.close()
    }
}