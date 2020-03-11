package thuy.ptithcm.string.features.addmore.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import thuy.ptithcm.string.R

/**
 * A simple [Fragment] subclass.
 */
class AddMoreFragment : Fragment() {

    companion object {
        private var instance: AddMoreFragment? = null
        fun getInstance(): AddMoreFragment {
            if (instance == null) instance = AddMoreFragment()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_more, container, false)
    }


}
