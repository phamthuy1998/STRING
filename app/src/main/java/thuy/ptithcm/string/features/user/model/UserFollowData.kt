package thuy.ptithcm.string.features.user.model

data class UserFollowData(
    val code: Int?,
    var data: ArrayList<UserInfo>?,
    val message: String?,
    val metadata: Metadata?,
    val status: Boolean?
)