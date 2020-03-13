package thuy.ptithcm.string.features.user.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_setting.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.viewmodel.UserViewModel
import thuy.ptithcm.string.utils.VERSION_STRING
import thuy.ptithcm.string.utils.getAccessToken
import thuy.ptithcm.string.utils.isNetworkAvailable
import thuy.ptithcm.string.utils.showDialogErrorLogin

class SettingActivity : AppCompatActivity() {

    companion object {
        private var instance: SettingActivity? = null
        fun getInstance(): SettingActivity {
            if (instance == null) instance =
                SettingActivity()
            return instance!!
        }
    }

    private var isLogOut = false
    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(this)
            .get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        inItViews()
        addEvents()
        bindings()
    }

    private fun bindings() {
        userViewModel.dataLogout.observe(this, Observer { dataLogout ->
            progressbar_logout.visibility = View.GONE
            if (dataLogout != null) {
                if (isLogOut) {
                    if (dataLogout.status == true) {
                        Toast.makeText(this, "Log out success!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
                        startActivity(intent)
                        finish()
                    } else {
                        showDialogErrorLogin()
                    }
                }
            }
        })
    }

    private fun inItViews() {
        tv_version.text = getString(R.string.version, VERSION_STRING)
    }

    private fun addEvents() {
        btn_back_setting.setOnClickListener {
            finish()
        }
        btn_log_out.setOnClickListener {
            if (!isNetworkAvailable()) {
                Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
            } else {
                progressbar_logout.visibility = View.VISIBLE
                getAccessToken()?.let { it1 -> userViewModel.logOut(it1) }
                isLogOut = true
            }
        }
        btn_edit_profile.setOnClickListener {
            if (!isNetworkAvailable()) {
                Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, EditProfileActivity.getInstance().javaClass)
                startActivity(intent)
            }
        }
        btn_change_password.setOnClickListener {
            if (!isNetworkAvailable()) {
                Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, ChangePasswordActivity.getInstance().javaClass)
                startActivity(intent)
            }
        }
    }
}
