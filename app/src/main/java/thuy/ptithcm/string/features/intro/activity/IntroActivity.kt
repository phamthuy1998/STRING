package thuy.ptithcm.string.features.intro.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.intro.fragment.Intro1Fragment
import thuy.ptithcm.string.features.intro.fragment.Intro2Fragment
import thuy.ptithcm.string.features.intro.fragment.Intro3Fragment
import thuy.ptithcm.string.features.user.activity.RegisterLandingActivity
import thuy.ptithcm.string.support.MyFragmentPagerAdapter
import thuy.ptithcm.string.utils.notFirstTime


class IntroActivity : AppCompatActivity() {
    companion object {
        private var instance: IntroActivity? = null
        fun getInstance(): IntroActivity {
            if (instance == null) instance = IntroActivity()
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        addTabLayout()
        addEvent()
    }

    private fun addEvent() {
        btn_skip_intro.setOnClickListener {
            notFirstTime()
            val intent = Intent(this, RegisterLandingActivity.getInstance().javaClass)
            startActivity(intent)
            finish()
        }
        btn_next.setOnClickListener {

        }
    }

    private fun addTabLayout() {
        val adapter = MyFragmentPagerAdapter(this.supportFragmentManager)
        adapter.addFragment(Intro1Fragment(), "")
        adapter.addFragment(Intro2Fragment(), "")
        adapter.addFragment(Intro3Fragment(), "")
        view_pager_intro.adapter = adapter
//        view_pager_intro.offscreenPageLimit = 1

//        tabDots.setupWithViewPager(view_pager_intro)
    }
}
