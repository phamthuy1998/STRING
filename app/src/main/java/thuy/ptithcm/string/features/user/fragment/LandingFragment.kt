package thuy.ptithcm.string.features.user.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_landing.*

import thuy.ptithcm.string.R

class LandingFragment : Fragment() {

    companion object {
        private var instance: LandingFragment? = null
        fun getInstance(): LandingFragment {
            if (instance == null) instance = LandingFragment()
            return instance!!
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvent()
    }

    private fun addEvent() {

    }


}
