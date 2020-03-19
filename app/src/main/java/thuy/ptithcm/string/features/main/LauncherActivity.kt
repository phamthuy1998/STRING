package thuy.ptithcm.string.features.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.intro.IntroActivity
import thuy.ptithcm.string.features.login.RegisterLandingActivity
import thuy.ptithcm.string.features.user.viewmodel.UserViewModel
import thuy.ptithcm.string.utils.isFirstTime
import thuy.ptithcm.string.utils.setAccessToken

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

//         removeValueSharePrefs(FIRST_TIME)

        // Check first time open app
        if (isFirstTime()) {
            val intent = Intent(this, IntroActivity().javaClass)
            startActivity(intent)
            finish()
        } else {

            val intent = Intent(this, MainActivity().javaClass)
            startActivity(intent)
            finish()

//            // If The account was logged in
//            if (getEmail() != "" && getPassword() != "") {
//                userViewModel.login(
//                    getEmail() ?: "",
//                    getPassword() ?: "",
//                    FCM_TOKEN + getFcmToken()
//                )
//                isCheckLogin = true
//            } else {
//                val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
//                startActivity(intent)
//                finish()
//            }
//            bindings()
        }
    }

    private fun bindings() {
        userViewModel.dataLogin.observe(this, Observer { dataLogin ->
            if (isCheckLogin) {
                if (dataLogin != null) { // Login success  --> open InterestActivity
                    if (dataLogin.status == true) {
                        setAccessToken(dataLogin.data?.access_token ?: "")
                        val intent = Intent(this, MainActivity().javaClass)
                        startActivity(intent)
                        finish()
                        isCheckLogin = false
                    } else {    // Login fail --> open RegisterLandingActivity
                        val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })
    }
}
