package minhnq.gvn.com.demokotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("imageId")
    @Expose
    var imageId: Int? = null,

    @SerializedName("imageName")
    @Expose
    var imageName: String? = null,

    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String? = null,

    @SerializedName("imageCategory")
    @Expose
    var imageCategory: Int? = null,

    var isFavorite: Int? = null
)