package thuy.ptithcm.string.features.user.service

import android.util.Log
import io.reactivex.Single
import thuy.ptithcm.string.api.RetrofitClientInstance
import thuy.ptithcm.string.features.user.model.UserData

class UserApiCaller {
    private val _apiRestFull: UserApi by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(UserApi::class.java)
    }

    fun login(username: String, password: String, fcmToken: String): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.signIn(
                username,
                password,
                fcmToken
            )
        )
    }

    fun forgotPassword(email: String): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.forgotPassword(email)
        )
    }

    fun resendEmailRegister(code: String): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.resendEmailRegister(code)
        )
    }

    fun registerAccByEmail(
        username: String,
        name: String,
        dayOfBirth: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Single<UserData> {
        Log.d(
            "aaaa2", username + "|" +
                    name + "|" + dayOfBirth + "|" + email + "|" +
                    password + "|" + confirmPassword
        )
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.registerAccByEmail(
                username = username,
                name = name,
                dayOfBirth = dayOfBirth,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        )
    }

}