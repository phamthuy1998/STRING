package thuy.ptithcm.string.features.feed.service

import android.util.Log
import io.reactivex.Single
import thuy.ptithcm.string.api.RetrofitClientInstance
import thuy.ptithcm.string.features.feed.model.FeedData
import thuy.ptithcm.string.utils.AUTHORIZATION

class FeedApiCaller {
    private val _apiRestFull: FeedApi by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(FeedApi::class.java)
    }

    fun getFeedList(
        authorization: String,
        page: Int,
        currentPerPage: Int
    ): Single<FeedData> {
        Log.d("AUTHORIZATION", AUTHORIZATION + authorization)
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.getFeedList(
                AUTHORIZATION + authorization,
                page,
                currentPerPage
            )
        )
    }

    fun getFeedLisat(
        authorization: String,
        page: Int,
        currentPerPage: Int
    ) {
        Log.d("AUTHORIZATION", AUTHORIZATION + authorization)
        _apiRestFull.getFeedList(
            AUTHORIZATION + authorization,
            page,
            currentPerPage
        )

    }


}