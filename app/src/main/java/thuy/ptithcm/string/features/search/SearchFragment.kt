package thuy.ptithcm.string.features.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import thuy.ptithcm.string.R

class SearchFragment : Fragment() {

    companion object {
        private var instance: SearchFragment? = null
        fun getInstance(): SearchFragment {
            if (instance == null) instance =
                SearchFragment()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


}
