package thuy.ptithcm.string.features.intro.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.intro.activity.IntroActivity

class Intro2Fragment : Fragment() {

    companion object {
        private var instance: Intro2Fragment? = null
        fun getInstance(): Intro2Fragment {
            if (instance == null) instance = Intro2Fragment()
            return instance!!
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro2, container, false)
    }


}
