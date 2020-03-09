package thuy.ptithcm.string.features.user.model

data class UserData(
    val code: Int?,
    val data: Data?,
    val message: String?,
    val status: Boolean?
)

data class Data(
    val access_token: String?,
    val badge: Any?,
    val bio: String?,
    val code: String?,
    val created_at: String?,
    val currentLocation: Any?,
    val date_of_birth: String?,
    val email: String?,
    val facebookID: Any?,
    val fcm_token: String?,
    val gender: String?,
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
    val type: String?,
    val updated_at: String?,
    val username: String?,
    val wanderlust_details: WanderlustDetails?,
    val website: Any?
)

data class NotificationSettings(
    val comments: Boolean?,
    val likes: Boolean,
    val new_followes: Boolean?,
    val post_saves: Boolean?,
    val string: Boolean?
)

data class WanderlustDetails(
    val isWanderlust: Boolean?,
    val location: Location?
)

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