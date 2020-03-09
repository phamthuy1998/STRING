package thuy.ptithcm.string.features.intro.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import thuy.ptithcm.string.R

class Intro3Fragment : Fragment() {

    companion object {
        private var instance: Intro3Fragment? = null
        fun getInstance(): Intro3Fragment {
            if (instance == null) instance = Intro3Fragment()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro3, container, false)
    }


}
