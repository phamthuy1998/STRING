package thuy.ptithcm.string.fragment


import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_wanderlust_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.support.MyFragmentPagerAdapter


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTabLayout()
        addEvent()
    }

    private fun addEvent() {
        btn_wanderlust.setOnClickListener {
            showBottomDialog()
        }
    }

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
