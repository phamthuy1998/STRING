package thuy.ptithcm.string.model

data class UserFollowData(
    val code: Int?,
    var data: ArrayList<UserInfo>?,
    val message: String?,
    val metadata: Metadata?,
    val status: Boolean?
)