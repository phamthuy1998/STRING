package thuy.ptithcm.string.features.user.service

import retrofit2.Call
import retrofit2.http.*
import thuy.ptithcm.string.features.user.model.InterestData
import thuy.ptithcm.string.features.user.model.UserData

interface InterestAPI {
    @GET("interest-categories-list")
    fun getListInterest(
        @Header("Authorization") authorization: String
    ): Call<InterestData>

    @PUT("users-interest-categories-select")
    fun putInterest(
        @Header("Authorization") authorization: String,
        @Query("lists_interest") user_id: String
    ): Call<UserData>

}