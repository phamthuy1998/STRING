package thuy.ptithcm.string.features.follow.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import thuy.ptithcm.string.model.UserData
import thuy.ptithcm.string.model.UserFollowData

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