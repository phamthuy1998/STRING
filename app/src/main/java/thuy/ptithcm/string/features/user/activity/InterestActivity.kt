package thuy.ptithcm.string.features.user.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import thuy.ptithcm.string.R
import thuy.ptithcm.string.activity.MainActivity
import thuy.ptithcm.string.features.user.fragment.InterestFragment

class InterestActivity : AppCompatActivity() {

    companion object {
        private var instance: InterestActivity? = null
        fun getInstance(): InterestActivity {
            if (instance == null) instance =
                InterestActivity()
            return instance!!
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)
        supportFragmentManager.beginTransaction()
            .add(R.id.frm_interest, InterestFragment.getInstance(), "aa")
            .commit()
    }

    fun clickBackFollow(view: View) { onBackPressed()}

    fun showMainActivity(view: View) {
        val intent = Intent(this, MainActivity.getInstance().javaClass)
        startActivity(intent)
        finish()
    }

}
