package thuy.ptithcm.string.features.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.addmore.AddMoreFragment
import thuy.ptithcm.string.features.feed.FeedFragment
import thuy.ptithcm.string.features.login.ChangePasswordFragment
import thuy.ptithcm.string.features.notification.NotificationFragment
import thuy.ptithcm.string.features.search.SearchFragment
import thuy.ptithcm.string.features.user.EditProfileFragment
import thuy.ptithcm.string.features.user.ProfileFragment
import thuy.ptithcm.string.features.user.SettingFragment
import thuy.ptithcm.string.utils.isNetworkAvailable

class MainActivity : AppCompatActivity() {

    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity {
            if (instance == null) instance =
                MainActivity()
            return instance!!
        }
    }

    private var count = 0

    private lateinit var feedFragment :FeedFragment

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        feedFragment = FeedFragment.getInstance()
        inItView()
    }

    private fun inItView() {
        showFragment(FeedFragment())
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_feed -> {
                    count++
                    if (count == 2){
                        feedFragment = FeedFragment()
                        showFragment(feedFragment)
                        count = 0
                    }
                    else {
                        showFragment(feedFragment)
                    }
                    true
                }
                R.id.menu_search -> {
                    count = 0
                    showFragment(searchFragment)
                    true
                }
                R.id.menu_add -> {
                    count = 0
                    showFragment(addFragment)
                    true
                }
                R.id.menu_notification -> {
                    count = 0
                    showFragment(notificationFragment)
                    true
                }
                R.id.menu_profile -> {
                    count = 0
                    showFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun removeFragment(fragment: Fragment, fragmentName: String? = null){

    }

    private fun showFragment(fragment: Fragment, fragmentName: String? = null) {
        if (fragmentName == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frm_main, fragment)
                .commit()
        } else
            supportFragmentManager.beginTransaction()
                .replace(R.id.frm_main, fragment)
                .addToBackStack(fragmentName)
                .commit()

    }

//    private fun addFragment(fragment: Fragment, fragmentName: String) {
//        if (!fragment.isAdded)
//            supportFragmentManager.beginTransaction()
//                .add(R.id.frm_main, fragment)
//                .addToBackStack(fragmentName)
//                .commit()
//    }

    fun onClickBack(view: View) {
        onBackPressed()
    }

    fun showEditProfileFragment(view: View) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
        } else {
            showFragment(EditProfileFragment(), "EditProfileFragment")
        }
    }

    fun showChangePasswordFragment(view: View) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
        } else {
            showFragment(ChangePasswordFragment(), "ChangePasswordFragment")
        }
    }

    fun showSettingFragment(view: View) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
        } else {
            showFragment(SettingFragment.getInstance(), "SettingFragment")
        }
    }
}
