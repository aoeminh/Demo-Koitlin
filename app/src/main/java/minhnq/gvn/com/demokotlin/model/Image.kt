package minhnq.gvn.com.demokotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("imageId")
    var imageId: Int? = null,

    @SerializedName("imageName")
    var imageName: Int? = null,

    @SerializedName("imageUrl")
    var imageUrl: String? = null,

    @SerializedName("imageCategory")
    var imageCategory: Int? = null,

    var isFavorite: Int? = null
)