package thuy.ptithcm.string.features.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import thuy.ptithcm.string.R


class RegisterLandingActivity : AppCompatActivity() {

    companion object {
        private var instance: RegisterLandingActivity? = null
        fun getInstance(): RegisterLandingActivity {
            if (instance == null) instance =
                RegisterLandingActivity()
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_landing)
        supportFragmentManager.beginTransaction()
            .add(R.id.frm_landing, LandingFragment.getInstance(), "LandingFragment")
            .commit()
    }

    private fun showFragment(fragment: Fragment, fragmentName: String) {
        if (!fragment.isAdded)  // not exist
            supportFragmentManager.beginTransaction()
                .add(R.id.frm_landing, fragment)
                .addToBackStack(fragmentName)
                .commit()
    }

    fun openFragmentSignUpWithEmail(view: View) {
        showFragment(SignUpEmailFragment.getInstance(), "SignUpEmailFragment")
    }

    fun openSignUpFacebookFragment(view: View) {
        showFragment(SignUpFacebookFragment.getInstance(), "SignUpFacebookFragment")
    }

    fun openLoginFragment(view: View) {
        showFragment(LoginFragment.getInstance(), "LoginFragment")
    }

    fun showForgotPasswordFragment(view: View) {
        showFragment(ForgotPasswordFragment(), "ForgotPasswordFragment")
    }

    fun onClickButtonBack(view: View) {
        onBackPressed()
    }

}