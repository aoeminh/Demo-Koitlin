package minhnq.gvn.com.demokotlin.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("imageId")
    @Expose
    var imageId: Int,

    @SerializedName("imageName")
    @Expose
    var imageName: String,

    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String,

    @SerializedName("imageCategory")
    @Expose
    var imageCategory: Int,

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