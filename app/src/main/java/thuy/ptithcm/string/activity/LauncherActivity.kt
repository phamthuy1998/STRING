package thuy.ptithcm.string.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.activity.RegisterLandingActivity

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
        startActivity(intent)
        finish()
    }
}
