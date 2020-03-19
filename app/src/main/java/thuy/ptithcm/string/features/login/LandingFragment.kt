package thuy.ptithcm.string.features.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import thuy.ptithcm.string.R
import thuy.ptithcm.string.utils.isNetworkAvailable

class LandingFragment : Fragment() {

    companion object {
        private var instance: LandingFragment? = null
        fun getInstance(): LandingFragment {
            if (instance == null) instance =
                LandingFragment()
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
        if (!requireContext().isNetworkAvailable())
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
    }
}
