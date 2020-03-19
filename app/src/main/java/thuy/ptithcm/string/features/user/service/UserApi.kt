package thuy.ptithcm.string.features.user.service

import retrofit2.Call
import retrofit2.http.*
import thuy.ptithcm.string.model.UserData

interface UserApi {

    @FormUrlEncoded
    @POST("users-login")
    fun signIn(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fcm_token") fcmToken: String
    ): Call<UserData>

    @FormUrlEncoded
    @POST("users-forget-password")
    fun forgotPassword(
        @Field("email") email: String
    ): Call<UserData>

    @FormUrlEncoded
    @POST("users-register-email")
    fun registerAccByEmail(
        @Field("username") username: String,
        @Field("name") name: String,
        @Field("date_of_birth") dayOfBirth: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String
    ): Call<UserData>

    @FormUrlEncoded
    @POST("resend-email")
    fun resendEmailRegister(
        @Field("code") code: String
    ): Call<UserData>

    @GET("profile/{users_id}")
    fun getUserProfile(
        @Header("Authorization") authorization: String,
        @Path("users_id") users_id: Int
    ): Call<UserData>

    @GET("users-logout")
    fun logOut(
        @Header("Authorization") authorization: String
    ): Call<UserData>

    @FormUrlEncoded
    @POST("users-change-profile")
    fun changeProfile(
        @Field("file") file: String,
        @Field("username") username: String,
        @Field("bio") bio: String,
        @Field("website") website: String,
        @Field("name") name: String,
        @Field("date_of_birth") dayOfBirth: String,
        @Header("Authorization") authorization: String
    ): Call<UserData>
}