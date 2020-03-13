package thuy.ptithcm.string.features.user.fragment


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_wanderlust_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.model.UserData
import thuy.ptithcm.string.features.user.viewmodel.UserViewModel
import thuy.ptithcm.string.support.MyFragmentPagerAdapter
import thuy.ptithcm.string.utils.*


class ProfileFragment : Fragment() {

    companion object {
        private var instance: ProfileFragment? = null
        fun getInstance(): ProfileFragment {
            if (instance == null) instance = ProfileFragment()
            return instance!!
        }
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!context?.isNetworkAvailable()!!)
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
        progressbar_profile.visibility = View.VISIBLE
        if (activity?.getUserID() != -1) {
            activity?.getAccessToken()?.let {
                activity?.getUserID()?.let { it1 ->
                    userViewModel.getUserProfile(
                        it,
                        it1
                    )
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Can't load information of account!",
                Toast.LENGTH_LONG
            ).show()
        }
        bindings()
        addTabLayout()
        addEvent()
    }

    override fun onResume() {
        super.onResume()
        if (!context?.isNetworkAvailable()!!)
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
        if (activity?.getUserID() != -1) {
            activity?.getAccessToken()?.let {
                activity?.getUserID()?.let { it1 ->
                    userViewModel.getUserProfile(
                        it,
                        it1
                    )
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Can't load information of account!",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun bindings() {
        userViewModel.dataUserProfile.observe(this, Observer<UserData>  { userData ->
            progressbar_profile.visibility = View.GONE
            if (userData != null) {
                if (userData.status == true)
                    inItViews(userData)
                else{
                    activity?.showDialogErrorLogin()
                }
            }
        })
    }

    private fun inItViews(userData: UserData) {
        userData.data?.refresh_token?.let { activity?.setAccessToken(it) }
        // Set image avatar
        Glide.with(requireContext())
            .load(userData.data?.profilePhoto)
            .error(R.drawable.ic_acc_img)
            .into(iv_avatar)

        tv_username_prof.text = userData.data?.username
        tv_bio_prof.text = userData.data?.bio
        tv_web.text = userData.data?.website
        tv_posts_count.text = userData.data?.postsCounter.toString()
        tv_itinerary_counter.text = userData.data?.itineraryCounter.toString()
        tv_following.text = userData.data?.followingCounter.toString()
        tv_follows.text = userData.data?.followerCounter.toString()

    }

    private fun addEvent() {
        btn_wanderlust.setOnClickListener {
            showBottomDialog()
        }
        tv_web.setOnClickListener {
            var url = tv_web.text.trim().toString()
            if (!url.contains("https://www."))
                url = "https://www.$url"
            Log.d("uriiii", url)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)//https://www.facebook.com/
            startActivity(intent)
        }
    }

    @SuppressLint("InflateParams")
    private fun showBottomDialog() {
        val mBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
        val dialog = layoutInflater.inflate(R.layout.dialog_wanderlust_profile, null)

        dialog.btn_done_dialog.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }
        dialog.btn_cancel_dialog_profile.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }

        mBottomSheetDialog.setContentView(dialog)
        mBottomSheetDialog.show()
    }

    private fun addTabLayout() {
        val adapter = MyFragmentPagerAdapter(activity!!.supportFragmentManager)
        adapter.addFragment(PostsFragment(), getString(R.string.post))
        adapter.addFragment(ItinerariesFramgent(), getString(R.string.itineraries))
        adapter.addFragment(SavesFragment(), getString(R.string.save))
        viewpager_tab_profile.adapter = adapter
        viewpager_tab_profile.offscreenPageLimit = 1
        tab_layout_profile.setupWithViewPager(viewpager_tab_profile)
    }
}
