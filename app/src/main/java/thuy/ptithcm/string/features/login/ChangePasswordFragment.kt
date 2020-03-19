package thuy.ptithcm.string.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import thuy.ptithcm.string.R

class ChangePasswordFragment : Fragment() {

    companion object {
        private var instance: ChangePasswordFragment? = null
        fun getInstance(): ChangePasswordFragment {
            if (instance == null) instance =
                ChangePasswordFragment()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

}
