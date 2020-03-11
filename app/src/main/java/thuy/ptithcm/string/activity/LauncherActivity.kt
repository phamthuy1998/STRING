package thuy.ptithcm.string.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.intro.activity.IntroActivity
import thuy.ptithcm.string.features.user.activity.InterestActivity
import thuy.ptithcm.string.features.user.activity.RegisterLandingActivity
import thuy.ptithcm.string.features.user.viewmodel.UserViewModel
import thuy.ptithcm.string.utils.*

class LauncherActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(this)
            .get(UserViewModel::class.java)
    }
    private var isCheckLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        // removeValueSharePrefs(FIRST_TIME)

//        bindings()
        // Check first time open app
        if (isFirstTime()) {
            val intent = Intent(this, IntroActivity.getInstance().javaClass)
            startActivity(intent)
            finish()
        } else {
            // If The account was logged in
//            if (getEmail() != "" && getPassword() != "") {
//
//                userViewModel.login(
//                    getEmail()!!,
//                    getPassword()!!,
//                    AUTHORIZATION + getFcmToken()
//                )
//                isCheckLogin = true
//            } else
//            {
                val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
                startActivity(intent)
                finish()
//            }

        }
    }

    private fun bindings() {
        userViewModel.dataLogin.observe(this, Observer { dataLogin ->
            if (isCheckLogin) {
                if (dataLogin != null) { // login success  --> open InterestActivity
                    if (dataLogin.status == true) {
                        val intent = Intent(this, InterestActivity.getInstance().javaClass)
                        startActivity(intent)
                        finish()
                        isCheckLogin = false
                    }
                } else {    //login fail --> open RegisterLandingActivity
                    val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }
}
