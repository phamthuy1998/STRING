package thuy.ptithcm.string.features.user.service

import android.util.Log
import io.reactivex.Single
import thuy.ptithcm.string.api.RetrofitClientInstance
import thuy.ptithcm.string.features.user.model.InterestData
import thuy.ptithcm.string.features.user.model.UserData
import thuy.ptithcm.string.utils.AUTHORIZATION

class InterestApiCaller {
    private val _apiRestFull: InterestAPI by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(InterestAPI::class.java)
    }

    fun getListInterest(authorization: String): Single<InterestData> {
        Log.d("AUTHORIZATION",AUTHORIZATION + authorization)
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.getListInterest(
                AUTHORIZATION + authorization
            )
        )
    }

    fun putInterest(authorization: String, listsInterest: ArrayList<Int>): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.putInterest(
                authorization = AUTHORIZATION + authorization,
                listsInterest = listsInterest
            )
        )
    }
}