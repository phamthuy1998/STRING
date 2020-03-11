package thuy.ptithcm.string.features.notification.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import thuy.ptithcm.string.R

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {

    companion object {
        private var instance: NotificationFragment? = null
        fun getInstance(): NotificationFragment {
            if (instance == null) instance = NotificationFragment()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }


}
