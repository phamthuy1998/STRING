package thuy.ptithcm.string.features.user.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.fragment.*


class RegisterLandingActivity : AppCompatActivity() {

    companion object {
        private var instance: RegisterLandingActivity? = null
        fun getInstance(): RegisterLandingActivity {
            if (instance == null) instance = RegisterLandingActivity()
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_landing)
        supportFragmentManager.beginTransaction()
            .add(R.id.frm_landing, LandingFragment.getInstance(), "aa")
            .commit()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.frm_landing, fragment)
            .addToBackStack("bb")
            .commit()
    }

    fun openFragmentSignUpWithEmail(view: View) {
        showFragment(SignUpEmailFragment.getInstance())
    }

    fun openSignUpFacebookFragment(view: View) {
        showFragment(SignUpFacebookFragment.getInstance())
    }

    fun openLoginFragment(view: View) {
        showFragment(LoginFragment.getInstance())
    }

    fun showForgotPasswordFragment(view: View) {
        showFragment(ForgotPasswordFragment())
    }

    fun onClickButtonBack(view: View) {
        onBackPressed()
    }

}