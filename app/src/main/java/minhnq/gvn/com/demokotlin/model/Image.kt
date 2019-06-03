package minhnq.gvn.com.demokotlin.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "image")
data class Image(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "imageId")
    @SerializedName("imageId")
    var imageId: Int,

    @SerializedName("imageName")
    @ColumnInfo(name = "imageName")
    var imageName: String,

    @SerializedName("imageUrl")
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String,


    @SerializedName("imageCategory")
    @ColumnInfo(name = "imageCategory")
    var imageCategory: Int,

    @SerializedName("isFavorite")
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Int
) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageId)
        parcel.writeString(imageName)
        parcel.writeString(imageUrl)
        parcel.writeInt(imageCategory)
        parcel.writeInt(isFavorite)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}