package thuy.ptithcm.string.features.user.service

import retrofit2.Call
import retrofit2.http.*
import thuy.ptithcm.string.features.user.model.UserData
import thuy.ptithcm.string.features.user.model.UserFollowData

interface FollowApi {

    @GET("users-list")
    fun getUserList(
        @Header("Authorization") authentication: String,
        @Query("page") page: Int,
        @Query("current_per_page") currentPerPage: String
    ): Call<UserFollowData>

    @POST("follow-users")
    fun postFollowUser(
        @Header("Authorization") authorization: String,
        @Query("users_id") userID: Int
    ): Call<UserData>

}