package thuy.ptithcm.string.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.addmore.fragment.AddMoreFragment
import thuy.ptithcm.string.features.feed.fragment.FeedFragment
import thuy.ptithcm.string.features.notification.fragment.NotificationFragment
import thuy.ptithcm.string.features.search.fragment.SearchFragment
import thuy.ptithcm.string.features.user.fragment.ProfileFragment
import thuy.ptithcm.string.support.MyFragmentPagerAdapter

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity {
            if (instance == null) instance = MainActivity()
            return instance!!
        }
    }

//
//    fun runtimeEnableAutoInit() {
//        // [START fcm_runtime_enable_auto_init]
//        FirebaseMessaging.getInstance().isAutoInitEnabled = true
//        // [END fcm_runtime_enable_auto_init]
//    }

    private val feedFragment by lazy {
        FeedFragment.getInstance()
    }
    private val searchFragment by lazy {
        SearchFragment.getInstance()
    }
    private val addFragment by lazy {
        AddMoreFragment.getInstance()
    }
    private val notificationFragment by lazy {
        NotificationFragment.getInstance()
    }
    private val profileFragment by lazy {
        ProfileFragment.getInstance()
    }

    private val viewPagerAdapter=MyFragmentPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inItView()
    }

    private fun inItView() {
        viewPagerAdapter.addFragment(feedFragment, "Feed fragment")
        viewPagerAdapter.addFragment(searchFragment, "Search fragment")
        viewPagerAdapter.addFragment(addFragment, "Add more fragment")
        viewPagerAdapter.addFragment(notificationFragment, "Notification fragment")
        viewPagerAdapter.addFragment(profileFragment, "Profile fragment")

        viewPagerMain.adapter = viewPagerAdapter
        viewPagerMain.addOnPageChangeListener(this)

        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_feed -> {
                    viewPagerMain.currentItem = 0
                    true
                }
                R.id.menu_search -> {
                    viewPagerMain.currentItem = 1
                    true
                }
                R.id.menu_add -> {
                    viewPagerMain.currentItem = 2
                    true
                }
                R.id.menu_notification -> {
                    viewPagerMain.currentItem = 3
                    true
                }
                R.id.menu_profile -> {
                    viewPagerMain.currentItem = 4
                    true
                }
                else -> false
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                botNavigation.selectedItemId = R.id.menu_feed
            }
            1 -> {
                botNavigation.selectedItemId = R.id.menu_search
            }
            2 -> {
                botNavigation.selectedItemId = R.id.menu_add
            }
            3 -> {
                botNavigation.selectedItemId = R.id.menu_notification
            }
            4 -> {
                botNavigation.selectedItemId = R.id.menu_profile
            }
        }
    }

}
