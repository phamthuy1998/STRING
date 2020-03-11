package thuy.ptithcm.string.features.user.service

import android.util.Log
import io.reactivex.Single
import thuy.ptithcm.string.api.RetrofitClientInstance
import thuy.ptithcm.string.features.user.model.UserData
import thuy.ptithcm.string.features.user.model.UserFollowData
import thuy.ptithcm.string.utils.AUTHORIZATION

class FollowApiCaller {
    private val _apiRestFull: FollowApi by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(FollowApi::class.java)
    }

    fun getUserList(
        authorization: String,
        page: Int,
        currentPerPage: String
    ): Single<UserFollowData> {
        Log.d("AUTHORIZATION", AUTHORIZATION + authorization)
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.getUserList(
                AUTHORIZATION + authorization,
                page,
                currentPerPage
            )
        )
    }

    fun postFollowUser(authorization: String, userID: Int): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.postFollowUser(
                AUTHORIZATION + authorization,
                userID
            )
        )
    }
}