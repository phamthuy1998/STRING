package thuy.ptithcm.string.features.user.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.string.features.user.model.UserData
import thuy.ptithcm.string.features.user.service.UserApiCaller

class UserViewModel : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: UserApiCaller by lazy { UserApiCaller() }
    val dataLogin = MutableLiveData<UserData>().apply { value = null }
    val dataRegister = MutableLiveData<UserData>().apply { value = null }
    val dataForgotPassword = MutableLiveData<UserData>().apply { value = null }
    val dataResendEmail = MutableLiveData<UserData>().apply { value = null }
    var email = MutableLiveData<String>().apply { value = "" }
    private var emailRegister = MutableLiveData<String>().apply { value = "" }
    private var code = MutableLiveData<String>().apply { value = "" }
    fun setEmail(tx: Editable) {
        email.value = tx.toString()
    }

    fun setEmailRegister(_email: String) {
        emailRegister.value = _email.toString()
    }
    fun getEmailRegister(): String? {
        return emailRegister.value.toString()
    }

    fun setCode(_code: String) {
        code.value = _code.toString()
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
                }, {})
        )
    }

    fun forgotPassword(email: String) {
        compo.add(
            apiManager.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataForgotPassword.value = it
                }, {})
        )
    }

    fun resendEmailRegister(code: String) {
        compo.add(
            apiManager.resendEmailRegister(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataResendEmail.value = it
                }, {})
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
        Log.d("aaaa",  username+"|"+
                name+"|"+ dayOfBirth+"|"+ email+"|"+
                password+"|"+ confirmPassword )
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
                }, {})
        )
    }

}