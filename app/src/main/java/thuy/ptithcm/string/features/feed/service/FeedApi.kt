package thuy.ptithcm.string.features.feed.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import thuy.ptithcm.string.features.feed.model.FeedData

interface FeedApi {
    @GET("feed")
    fun getFeedList(
        @Header("Authorization") authentication: String,
        @Query("page") page: Int,
        @Query("current_per_page") currentPerPage: Int
    ): Call<FeedData>

}