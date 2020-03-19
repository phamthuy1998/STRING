package thuy.ptithcm.string.features.user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.string.features.user.service.UserApiCaller
import thuy.ptithcm.string.model.UserData

class UserViewModel : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: UserApiCaller by lazy { UserApiCaller() }
    val dataLogin = MutableLiveData<UserData>().apply { value = null }
    val dataRegister = MutableLiveData<UserData>().apply { value = null }
    val dataForgotPassword = MutableLiveData<UserData>().apply { value = null }
    val dataUserProfile = MutableLiveData<UserData>().apply { value = null }
    val dataUserChange = MutableLiveData<UserData>().apply { value = null }
    val dataResendEmail = MutableLiveData<UserData>().apply { value = null }
    val dataLogout = MutableLiveData<UserData>().apply { value = null }
    var errorData = MutableLiveData<Boolean>().apply { value = false }
    var email = MutableLiveData<String>().apply { value = "" }
    private var emailRegister = MutableLiveData<String>().apply { value = "" }
    private var code = MutableLiveData<String>().apply { value = "" }

    fun setEmailRegister(_email: String) {
        emailRegister.value = _email
    }

    fun getEmailRegister(): String? {
        return emailRegister.value.toString()
    }

    fun setCode(_code: String) {
        code.value = _code
    }

    fun getCode(): String? {
        return code.value.toString()
    }

    fun login(username: String, password: String, fcmToken: String) {
        compo.add(
            apiManager.login(username, password, fcmToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataLogin.value = it
                    dataLogin.value?.status = false
                }, {
                    dataLogin.value?.message = it.message
                    dataLogin.value?.status = false
                })
        )
    }

    fun forgotPassword(email: String) {
        compo.add(
            apiManager.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataForgotPassword.value = it
                }, {
                })
        )
    }

    fun getUserProfile(accessToken: String, usersID: Int) {
        compo.add(
            apiManager.getUserProfile(accessToken, usersID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataUserProfile.value = it
                    errorData.value = false
                }, {
                    errorData.value = true
                })
        )
    }

    fun logOut(accessToken: String) {
        compo.add(
            apiManager.logOut(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataLogout.value = it
                    errorData.value = false
                }, {
                    errorData.value = true
                })
        )
    }

    fun resendEmailRegister(code: String) {
        compo.add(
            apiManager.resendEmailRegister(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataResendEmail.value = it
                }, {
                })
        )
    }

    fun changeProfile(
        file: String,
        username: String,
        bio: String,
        website: String,
        name: String,
        dayOfBirth: String,
        accessToken: String
    ) {
        compo.add(
            apiManager.changeProfile(
                file,
                username,
                bio,
                website,
                name,
                dayOfBirth,
                accessToken
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    errorData.value = false
                    dataUserChange.value = it
                }, {
                    errorData.value = true
                })
        )
    }

    fun registerAccByEmail(
        username: String,
        name: String,
        dayOfBirth: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        compo.add(
            apiManager.registerAccByEmail(
                username,
                name,
                dayOfBirth,
                email,
                password,
                confirmPassword
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataRegister.value = it
                }, {
                })
        )
    }

    fun isValidate(email: String): Boolean {
        return email.isNotEmpty()
    }
}