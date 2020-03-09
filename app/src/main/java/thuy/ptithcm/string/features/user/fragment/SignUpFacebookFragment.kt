package thuy.ptithcm.string.features.user.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import thuy.ptithcm.string.R

class SignUpFacebookFragment  : Fragment() {

    companion object {
        private var instance: SignUpFacebookFragment? = null
        fun getInstance(): SignUpFacebookFragment {
            if (instance == null) instance = SignUpFacebookFragment()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_facebook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvent()
    }

    private fun addEvent() {

    }


}
