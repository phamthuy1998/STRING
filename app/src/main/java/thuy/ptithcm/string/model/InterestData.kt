package thuy.ptithcm.string.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class InterestData(
    val code: Int?,
    val data: ArrayList<Interest>?,
    val message: String?,
    val metadata: Metadata?,
    val status: Boolean?
)

data class Interest(
    @SerializedName("check_user_sellect")
    var checkUserSelect: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    val id: Int?,
    val photo: Photo?,
    val photoID: Int?,
    val title: String?,
    val trash: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
@Parcelize
data class Photo(
    val id: Int?,
    val url: Url?
): Parcelable

data class Metadata(
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("current_per_page")
    val currentPerPage: Int?,
    @SerializedName("next_page")
    val nextPage: Int?,
    @SerializedName("prev_pages")
    val prevPages: Int?,
    @SerializedName("total_count")
    val totalCount: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)