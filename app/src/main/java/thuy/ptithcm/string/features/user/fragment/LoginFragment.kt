package thuy.ptithcm.string.features.user.fragment


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_login.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.activity.MainActivity
import thuy.ptithcm.string.databinding.FragmentLoginBinding
import thuy.ptithcm.string.features.user.viewmodel.UserViewModel
import thuy.ptithcm.string.utils.*

class LoginFragment : Fragment(), TextWatcher {

    companion object {
        private var instance: LoginFragment? = null
        fun getInstance(): LoginFragment {
            if (instance == null) instance = LoginFragment()
            return instance!!
        }
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(UserViewModel::class.java)
    }

    private var isCheckLogin = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this@LoginFragment
        binding.userViewModel = userViewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings()
        addEvent()
    }

//    private fun isNetworkAvailable(): Boolean {
//        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
//        return  activeNetwork?.isConnectedOrConnecting == true
//    }

    private fun bindings() {
        userViewModel.dataLogin.observe(this, Observer { dataLogin ->
            if (dataLogin != null) {
                if (isCheckLogin) {
                    progressbarLogin.visibility = View.GONE
                    if (dataLogin.status == true) {   // Login success
                        // Save login information
                        context?.setEmail(edt_email_login.text.trim().toString())
                        context?.setPassword(edt_password_login.text.trim().toString())
                        dataLogin.data?.id?.let { context?.setUserID(it) }
                        dataLogin.data?.access_token?.let { context?.setAccessToken(it) }
                        val intent = Intent(context, MainActivity().javaClass)
                        startActivity(intent)
                        activity?.finish()
                    } else {    // Login fail
                        tv_message_login.visibility = View.VISIBLE
                        tv_message_login.text = dataLogin.message
                        btn_login.isEnabled = true
                        btn_login.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.colorPurple)
                    }
                    isCheckLogin = false
                }
            }
        })
    }

    private fun addEvent() {
        btn_login.setOnClickListener {
            checkLogin()
        }
        edt_email_login.addTextChangedListener(this)
        edt_password_login.addTextChangedListener(this)
        ll_login.setOnTouchListener { v, _ ->
            v.hideKeyboard()
            true
        }
    }

    private fun checkLogin() {
        if (!context?.isNetworkAvailable()!!) {
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
            tv_message_login.visibility = View.VISIBLE
            tv_message_login.text = getString(R.string.errConnection)
        } else {
            tv_message_login.visibility = View.GONE
            if (!isValidEmail(edt_email_login.text.trim().toString()))
                edt_email_login.error = getString(R.string.err_email_not_valid)
            if (!isValidPassword(edt_password_login.text.trim().toString()))
                edt_password_login.error = getString(R.string.err_pw_not_valid)
            else if (isValidEmail(edt_email_login.text.trim().toString()) &&
                isValidEmail(edt_email_login.text.trim().toString())
            ) {
                btn_login.isEnabled = false
                btn_login.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.colorGrayEnable)
                progressbarLogin.visibility = View.VISIBLE
                userViewModel.login(
                    edt_email_login.text.trim().toString(),
                    edt_password_login.text.trim().toString(),
                    FCM_TOKEN + getFcmToken()
                )
                isCheckLogin = true
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (edt_email_login.text.trim().toString() != "" && edt_password_login.text.trim().toString() != "") {
            btn_login.isEnabled = true
            btn_login.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.colorPurple)
        } else {
            btn_login.isEnabled = false
            btn_login.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.colorGrayEnable)
        }
    }
}


