package thuy.ptithcm.string.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId


fun View.hideKeyboard() {
    val inputMethodManager =
        context!!.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun isValidEmail(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(input).matches()
}

fun isValidUsername(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.USER_NAME.matcher(input).matches()
}

fun isValidPassword(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.PASSWORD.matcher(input).matches()
}

fun isValidPhoneNumber(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.PHONE_NUMBER.matcher(input).matches()
}

fun isValidUrl(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.URL.matcher(input).matches()
}

fun getFcmToken(): String {
    var token = ""
    FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            } else {
                Log.d("Firebasetoken", task.result?.token.toString())
                token = task.result?.token.toString()
            }
        })
    return token
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return  activeNetwork?.isConnectedOrConnecting == true
}