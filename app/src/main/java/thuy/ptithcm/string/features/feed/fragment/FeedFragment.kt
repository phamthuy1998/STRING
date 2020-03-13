package thuy.ptithcm.string.features.feed.fragment


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.dialog_err_login.view.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.no_feed.*
import kotlinx.android.synthetic.main.no_internet.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.feed.viewmodel.FeedViewModel
import thuy.ptithcm.string.features.user.fragment.LoginFragment
import thuy.ptithcm.string.utils.*


class FeedFragment : Fragment() {

    companion object {
        private var instance: FeedFragment? = null
        fun getInstance(): FeedFragment {
            if (instance == null) instance = FeedFragment()
            return instance!!
        }
    }

    private val feedViewModel: FeedViewModel by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(FeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!context?.isNetworkAvailable()!!) {
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
            feed_no_wifi.visible()
        } else {
            feed_no_wifi.gone()
        }
        bindings()
        addEvents()
    }


    @SuppressLint("InflateParams")
    fun Context.showDialogErrorLogin() {
        val mDialogView = LayoutInflater.from(applicationContext)
            .inflate(R.layout.dialog_err_login, null)
        val mBuilder = AlertDialog.Builder(applicationContext)
            .setView(mDialogView)
            .setCancelable(false) //click outside = false

        val mAlertDialog = mBuilder.show()
        mDialogView.btn_ok_err_login.setOnClickListener {
            mAlertDialog.dismiss()
            if (!LoginFragment().isAdded)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.frm_landing, LoginFragment.getInstance())
                    ?.addToBackStack("LoginFragment")
                    ?.commit()
        }
    }

    private fun addEvents() {
        swipe_refresh_feed.setOnRefreshListener {
            refreshLayout()
            swipe_refresh_feed.isRefreshing = false
        }

        btn_reload_no_wifi.setOnClickListener { refreshLayout() }
        btn_follow_people.setOnClickListener { followPeople() }
    }

    private fun followPeople() {

    }

    private fun refreshLayout() {
        if (!context?.isNetworkAvailable()!!) {
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
            feed_no_wifi.visible()
        } else {
            feed_no_wifi.gone()
            activity?.getAccessToken()?.let {
                feedViewModel.getUserFollowList(
                    it, 1,
                    CURRENT_PER_PAGE
                )
            }
        }
    }

    private fun bindings() {
        activity?.getAccessToken()?.let {
            feedViewModel.getUserFollowList(
                it, 1,
                CURRENT_PER_PAGE
            )
        }

        feedViewModel.feedData.observe(this, Observer { feedData ->
            if (feedData == null) {
                layout_no_feed.visible()
            } else {
                if (feedData.status == true) {
                    layout_no_feed.gone()
                } else
                    activity?.showDialogErrorLogin()
            }
        })
    }

}
