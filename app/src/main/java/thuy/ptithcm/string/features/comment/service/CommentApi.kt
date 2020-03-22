package thuy.ptithcm.string.features.comment.service

import retrofit2.Call
import retrofit2.http.*
import thuy.ptithcm.string.model.CommentData
import thuy.ptithcm.string.model.DataResult

interface CommentApi {
    @GET("comments-list/{ipps_id}")
    fun getCommentList(
        @Path("ipps_id") ipps_id: Int,
        @Header("Authorization") authentication: String,
        @Query("page") page: Int,
        @Query("current_per_page") currentPerPage: Int
    ): Call<CommentData>

    @FormUrlEncoded
    @POST("comment-add")
    fun addComment(
        @Field("ipps_id") id: Int?,
        @Field("comment") comment: String?,
        @Field("replyID") replyID: Int?,
        @Field("commentchildID") commentChildID: Int?,
        @Query("tagUsername[]") tagUsername: ArrayList<String>?,
        @Header("Authorization") authentication: String?
    ): Call<DataResult>

    @FormUrlEncoded
    @PUT("comment-edit")
    fun editComment(
        @Field("ipps_id") idFeed: Int?,
        @Field("comment_id") cmtID: Int?,
        @Field("comment") comment: String?,
        @Query("tagUsername[]") tagUsername: ArrayList<String>?,
        @Header("Authorization") authentication: String?
    ): Call<DataResult>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "comment-delete", hasBody = true)
    fun deleteComment(
        @Field("comment_id") cmtId: Int?,
        @Field("ipps_id") feedID: Int?,
        @Header("Authorization") authentication: String?
    ): Call<DataResult>
}