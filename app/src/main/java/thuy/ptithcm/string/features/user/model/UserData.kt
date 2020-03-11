package thuy.ptithcm.string.features.user.model

import com.google.gson.annotations.SerializedName

data class UserData(
    val code: Int?,
    val data: UserInfo?,
    var message: String?,
    var status: Boolean?
)

//@Entity(tableName = "UserInfo")
data class UserInfo(
    val access_token: String?,
    val badge: String?,
    val bio: String?,
    val code: String?,
    val created_at: String?,
    val currentLocation: String?,
    val date_of_birth: String?,
    val email: String?,
    val facebookID: String?,
    val fcm_token: String?,
    val gender: String?,
//    @PrimaryKey
    val id: Int?,
    val isActive: Boolean?,
    val isLoginFirst: Boolean?,
    val isNewUser: Boolean?,
    val isSuperUser: Boolean?,
    val name: String?,
    val notificationSettings: NotificationSettings?,
    val numberOfLogin: Int?,
    val profilePhoto: String?,
    val refresh_token: String?,
    val trash: Boolean?,
    val photos: ArrayList<Photo>?,
    val type: String?,
    val updated_at: String?,
    val username: String?,
    val wanderlust_details: WanderlustDetails?,
    val website: String?,
    var checkfollow: Boolean?,
    val followingCounter: Int?,
    val followerCounter: Int?,
    val postsCounter: Int?,
    val itineraryCounter: Int?

)

//@Parcelize
//@Entity(tableName = "NotificationSettings")
data class NotificationSettings(
    val comments: Boolean?,
    val likes: Boolean,
    @SerializedName("new_followes")
    val newFollows: Boolean?,
    val post_saves: Boolean?,
    val string: Boolean?
)
//    : Parcelable

//@Parcelize
//@Entity(tableName = "WanderlustDetails")
data class WanderlustDetails(
    val isWanderlust: Boolean?,
    val location: Location?
)
//    : Parcelable

//@Parcelize
//@Entity(tableName = "Location")
data class Location(
    val address: String?,
    val city: String?,
    val description: String?,
    val lat: String?,
    val long: String?,
    val name: String?,
    val placeID: String?,
    val websiteUrl: String?
)
//    : Parcelable