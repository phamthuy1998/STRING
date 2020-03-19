package thuy.ptithcm.string.features.interest.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query
import thuy.ptithcm.string.model.InterestData
import thuy.ptithcm.string.model.UserData

interface InterestAPI {
    @GET("interest-categories-list")
    fun getListInterest(
        @Header("Authorization") authorization: String
    ): Call<InterestData>

    @PUT("users-interest-categories-select")
    fun putInterest(
        @Header("Authorization") authorization: String,
        @Query("lists_interest[]") listsInterest: ArrayList<Int>
    ): Call<UserData>

}