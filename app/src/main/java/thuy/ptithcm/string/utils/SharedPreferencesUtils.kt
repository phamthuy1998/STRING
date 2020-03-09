package thuy.ptithcm.string.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

fun Context.isFirstTime(): Boolean {
    val pref: SharedPreferences = getSharedPreferences(PREFS, AppCompatActivity.MODE_PRIVATE)
    val isFirstTime = pref.getBoolean(FIRST_TIME, true)
    if (isFirstTime) return true
    return false
}
