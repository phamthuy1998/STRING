package thuy.ptithcm.string.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

fun Context.isFirstTime(): Boolean {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getBoolean(FIRST_TIME, true)
}


fun Context.notFirstTime() {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putBoolean(FIRST_TIME, false)
    editor.apply()
}

fun Context.setEmail(email: String) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(EMAIL, email)
    editor.apply()
}

fun Context.getEmail(): String? {
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getString(EMAIL, "")
}

fun Context.setUserID(userID: Int) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putInt(USER_ID, userID)
    editor.apply()
}

fun Context.getUserID(): Int? {
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getInt(USER_ID, -1)
}

fun Context.setAccessToken(accessToken: String) {
    Log.d("ACCESS_TOKEN_PUT", accessToken)
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(ACCESS_TOKEN, accessToken)
    editor.apply()
}

fun Context.getAccessToken(): String? {
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val accessToken=pref.getString(ACCESS_TOKEN, "")
        Log.d("ACCESS_TOKEN_GET", accessToken.toString())
    return accessToken
}

fun Context.setPassword(key: String) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(PASSWORD, key)
    editor.apply()
}

fun Context.getPassword(): String? {
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getString(PASSWORD, "")
}

fun Context.removeValueSharePrefs(KEY_NAME: String) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = pref.edit()
    editor.remove(KEY_NAME)
    editor.apply()
}

fun Context.getInt(valueName: String): Int {
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getInt(valueName, -1)
}

fun Context.getBoolean(valueName: String): Boolean{
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getBoolean(valueName, false)
}

fun Context.setInt(valueName: String, value: Int) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putInt(valueName, value)
    editor.apply()
}


fun Context.getString(valueName: String): String?{
    val pref = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    return pref.getString(valueName,"")
}

fun Context.setString(valueName: String, value: String) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(valueName, value)
    editor.apply()
}

fun Context.setBoolean(valueName: String, value: Boolean) {
    val pref: SharedPreferences = getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putBoolean(valueName, value)
    editor.apply()
}