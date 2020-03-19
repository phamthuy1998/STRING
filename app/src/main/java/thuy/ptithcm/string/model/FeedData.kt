package thuy.ptithcm.string.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import thuy.ptithcm.string.database.PhotoConverter

data class FeedData(
    val code: Int?,
    val data: ArrayList<Feed>?,
    val message: String?,
    val metadata: Metadata?,
    val status: Boolean?
)

@Parcelize
data class FirstVideo(
    var duration: Int?,
    var thumbs: String?,
    var url: String?
):Parcelable

@Parcelize
data class Feed(
    val address: String?,
    @SerializedName("co_edit")
    val coEdit: Boolean?,
    var commentCounter: Int?,
    val copyCounter: Int?,
    @TypeConverters(PhotoConverter::class)
    val coverImage: Photos?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("first_videos")
    val firstVideo: FirstVideo?,
    val description: String?,
    @PrimaryKey
    val id: Int?,
    var isLiked: Boolean?,
    val isPrivate: Boolean?,
    val itineraries: ArrayList<Itinerary>? = arrayListOf(),
    val lat: Double?,
    var isSaved: Boolean?,
    var likeCounter: Int?,
    val long: Double?,
    val photos: ArrayList<Photo>? = arrayListOf(),
    val place: Place?,
    val placeID: String?,
    val strungCounter: Int?,
    val strungFrom: StrungFrom?,
    val strungFromID: Int?,
    var saveCounter: Int?,
    val title: String?,
    val tags: ArrayList<Tag>? = arrayListOf(),
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val user: UserInfo?,
    val videos: Video?,
    @SerializedName("walkthrough")
    val walkThrough: Int?,
    val websiteUrl: String?,
    val workingHours: WorkingHours?
) : Parcelable

@Parcelize
data class Video(
    val duration: Int?,
    val thumbs: String?,
    val url: String?
):Parcelable

@Parcelize
data class Itinerary(
    val id: Int?,
    val orderNo: Int?,
    val photos: Photos?,
    val poiID: Int?,
    val title: String?
):Parcelable

@Parcelize
data class StrungFrom(
    val id: Int?,
    val isLoginFirst: Boolean?,
    val numberOfLogin: Int?,
    val profilePhoto: String?,
    val username: String?
):Parcelable

@Parcelize
data class WorkingHours(
    val FRI: String?,
    val MON: String?,
    val SAT: String?,
    val SUN: String?,
    val THU: String?,
    val TUE: String?,
    val WD: String?
):Parcelable

@Parcelize
data class Photos(
    val id: Int?,
    val url: Url?
):Parcelable

@Parcelize
data class Url(
    val medium: String?,
    val original: String?,
    val thumb: String?
):Parcelable

@Parcelize
data class Place(
    val address: String?,
    val copyCounter: Int?,
    val id: Int?,
    val lat: Double?,
    val long: Double?,
    val photo: PhotoX?,
    val placeID: String?,
    val title: String?
):Parcelable

@Parcelize
data class PhotoX(
    val id: Int?,
    val url: UrlX?
):Parcelable

@Parcelize
data class UrlX(
    val medium: String?,
    val original: String?,
    val thumb: String?
):Parcelable

@Parcelize
data class Tag(
    val id: Int?,
    val title: String?
):Parcelable