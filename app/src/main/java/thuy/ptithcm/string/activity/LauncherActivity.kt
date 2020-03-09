package thuy.ptithcm.string.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.intro.activity.IntroActivity
import thuy.ptithcm.string.features.user.activity.RegisterLandingActivity
import thuy.ptithcm.string.utils.isFirstTime

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        if(isFirstTime()){
            val intent = Intent(this, IntroActivity.getInstance().javaClass)
            startActivity(intent)
            finish()
        }
        else{
            val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
            startActivity(intent)
            finish()
        }
    }
}
