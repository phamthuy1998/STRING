package thuy.ptithcm.string.model

data class CommentData(
    val code: Int?,
    val data: ArrayList<Comment>?= arrayListOf(),
    val message: String?,
    val metadata: Metadata?,
    val status: Boolean
)

data class Comment(
    val description: String?,
    val id: Int?,
    val ippID: Int?,
    val replyComment: ArrayList<ReplyComment>? = arrayListOf(),
    val replyID: Int?,
    val tagUsername: String?,
    val user: UserComment?
)

data class ReplyComment(
    val commentchildID: String?,
    val created_at: String?,
    val description: String?,
    val id: Int?,
    val ippID: Int?,
    val replyID: Int?,
    val tagUsername: ArrayList<String>?= arrayListOf(),
    val trash: Int?,
    val updated_at: String?,
    val user: UserComment?,
    val userID: Int?
)

data class UserComment(
    val badge: String?,
    val facebookID: String?,
    val id: Int?,
    val isLoginFirst: Boolean?,
    val numberOfLogin: Int?,
    val profilePhoto: String?,
    val type: String?,
    val username: String?
)
