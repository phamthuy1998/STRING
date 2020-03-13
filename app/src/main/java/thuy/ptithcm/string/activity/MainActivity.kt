package thuy.ptithcm.string.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.addmore.fragment.AddMoreFragment
import thuy.ptithcm.string.features.feed.fragment.FeedFragment
import thuy.ptithcm.string.features.notification.fragment.NotificationFragment
import thuy.ptithcm.string.features.search.fragment.SearchFragment
import thuy.ptithcm.string.features.user.fragment.ChangePasswordFragment
import thuy.ptithcm.string.features.user.fragment.EditProfileFragment
import thuy.ptithcm.string.features.user.fragment.ProfileFragment
import thuy.ptithcm.string.features.user.fragment.SettingFragment
import thuy.ptithcm.string.utils.isNetworkAvailable

class MainActivity : AppCompatActivity() {

    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity {
            if (instance == null) instance = MainActivity()
            return instance!!
        }
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inItView()
    }

    private fun inItView() {
        showFragment(FeedFragment(), "FeedFragment")
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_feed -> {
                    showFragment(feedFragment, "FeedFragment")
                    true
                }
                R.id.menu_search -> {
                    showFragment(searchFragment, "SearchFragment")
                    true
                }
                R.id.menu_add -> {
                    showFragment(addFragment, "AddMoreFragment")
                    true
                }
                R.id.menu_notification -> {
                    showFragment(notificationFragment, "NotificationFragment")
                    true
                }
                R.id.menu_profile -> {
                    showFragment(profileFragment, "ProfileFragment")
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment, fragmentName: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frm_main, fragment)
            .addToBackStack(fragmentName)
            .commit()
    }

    private fun addFragment(fragment: Fragment, fragmentName: String) {
        if (!fragment.isAdded)
            supportFragmentManager.beginTransaction()
                .add(R.id.frm_main, fragment)
                .addToBackStack(fragmentName)
                .commit()
    }

    fun onClickBack(view: View) {
        onBackPressed()
    }

    fun showEditProfileFragment(view: View) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
        } else {
            addFragment(EditProfileFragment(), "EditProfileFragment")
        }
    }

    fun showChangePasswordFragment(view: View) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
        } else {
            addFragment(ChangePasswordFragment(), "ChangePasswordFragment")
        }
    }

    fun showSettingFragment(view: View) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
        } else {
            addFragment(SettingFragment.getInstance(), "SettingFragment")
        }
    }
}
