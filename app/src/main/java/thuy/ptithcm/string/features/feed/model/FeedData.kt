package thuy.ptithcm.string.features.feed.model

import thuy.ptithcm.string.features.user.model.Metadata
import thuy.ptithcm.string.features.user.model.Photo

data class FeedData(
    val code: Int?,
    val data: ArrayList<DataFeed>?,
    val message: String?,
    val metadata: Metadata?,
    val status: Boolean?
)

data class DataFeed(
    val commentCounter: Int?,
    val copyCounter: Int?,
    val created_at: String?,
    val description: String?,
    val id: Int?,
    val isLiked: Boolean?,
    val isSaved: Boolean?,
    val likeCounter: Int?,
    val photos: ArrayList<Photo>?,
    val place: Place?,
    val saveCounter: Int?,
    val tags: ArrayList<Tag>?,
    val type: String,
    val updated_at: String?,
    val user: User?,
    val videos: Any?,
    val walkthrough: Int?
)

data class Place(
    val address: String?,
    val copyCounter: Int?,
    val id: Int?,
    val lat: Double?,
    val long: Double?,
    val photo: PhotoX?,
    val placeID: String?,
    val title: String?
)

data class PhotoX(
    val id: Int?,
    val url: UrlX?
)

data class UrlX(
    val medium: String?,
    val original: String?,
    val thumb: String?
)

data class Tag(
    val id: Int?,
    val title: String?
)

data class User(
    val badge: Any?,
    val badgeID: Any?,
    val bio: String?,
    val currentLocation: Any?,
    val date_of_birth: String?,
    val email: String?,
    val facebookID: Any?,
    val gender: String?,
    val id: Int?,
    val isLoginFirst: Boolean?,
    val isNewUser: Boolean?,
    val isSuperUser: Boolean?,
    val name: String?,
    val notificationSettings: NotificationSettings?,
    val numberOfLogin: Int?,
    val profilePhoto: Any?,
    val trash: Boolean?,
    val type: String?,
    val username: String?,
    val wanderlust_details: WanderlustDetails?,
    val website: String?
)

data class NotificationSettings(
    val app_update: Boolean?,
    val comments: Boolean?,
    val contact_joins_string: Boolean?,
    val likes: Boolean?,
    val near_you: Boolean?,
    val new_followes: Boolean?,
    val post_saves: Boolean?,
    val processor: String?,
    val string: Boolean?
)

data class WanderlustDetails(
    val isWanderlust: Boolean?,
    val location: Location?
)

data class Location(
    val description: String?,
    val lat: Double?,
    val long: Double?,
    val name: String?,
    val placeID: String?
)
