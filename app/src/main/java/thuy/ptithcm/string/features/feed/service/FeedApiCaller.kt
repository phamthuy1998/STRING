package thuy.ptithcm.string.features.feed.service

import android.util.Log
import io.reactivex.Single
import thuy.ptithcm.string.api.RetrofitClientInstance
import thuy.ptithcm.string.model.DataResult
import thuy.ptithcm.string.model.FeedData
import thuy.ptithcm.string.utils.AUTHORIZATION
import thuy.ptithcm.string.utils.CURRENT_PER_PAGE

class FeedApiCaller {
    private val _apiRestFull: FeedApi by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(FeedApi::class.java)
    }

    fun getFeedList(
        authorization: String,
        page: Int
    ): Single<FeedData> {
        Log.d("AUTHORIZATION", AUTHORIZATION + authorization)
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.getFeedList(
                AUTHORIZATION + authorization,
                page,
                CURRENT_PER_PAGE
            )
        )
    }

    fun savePost(
        authorization: String,
        id: Int
    ): Single<DataResult>  {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.savePost(
            AUTHORIZATION + authorization,
            id
        ))
    }

    fun likePost(
        authorization: String,
        id: Int
    ): Single<DataResult>  {

        return RetrofitClientInstance.buildRequest(
            _apiRestFull.likePost(
            AUTHORIZATION + authorization,
            id
        ))
    }

}