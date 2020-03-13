package thuy.ptithcm.string.features.user.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import thuy.ptithcm.string.R

class ChangePasswordActivity : AppCompatActivity() {

    companion object {
        private var instance: ChangePasswordActivity? = null
        fun getInstance(): ChangePasswordActivity {
            if (instance == null) instance =
                ChangePasswordActivity()
            return instance!!
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
    }
}
