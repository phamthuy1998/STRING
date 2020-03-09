package thuy.ptithcm.string.features.user.fragment


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_login.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.databinding.FragmentLoginBinding
import thuy.ptithcm.string.features.user.activity.InterestActivity
import thuy.ptithcm.string.features.user.viewmodel.UserViewModel
import thuy.ptithcm.string.utils.AUTHORIZATION
import thuy.ptithcm.string.utils.hideKeyboard
import thuy.ptithcm.string.utils.isValidEmail
import thuy.ptithcm.string.utils.isValidPassword

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
    private var fcmToken: String? = ""

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
        getFcmToken()
        bindings()
        addEvent()
    }

    private fun bindings() {
        userViewModel.dataLogin.observe(this, Observer { dataLogin ->
            if (isCheckLogin)
                if (dataLogin.data != null) {   // login success
                    val intent = Intent(context, InterestActivity.getInstance().javaClass)
                    startActivity(intent)
                    activity?.finish()
                } else {    //login fail
                    tv_message_login.visibility = View.VISIBLE
                    tv_message_login.text = dataLogin.message
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

    private fun getFcmToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                } else {
                    fcmToken = task.result?.token
                    Log.d("Firebasetoken", fcmToken.toString())
                }
            })
    }


    private fun checkLogin() {
        if (!isValidEmail(edt_email_login.text.trim().toString()))
            edt_email_login.error = getString(R.string.err_email_not_valid)
        if (!isValidPassword(edt_password_login.text.trim().toString()))
            edt_password_login.error = getString(R.string.err_pw_not_valid)
        else if (isValidEmail(edt_email_login.text.trim().toString()) &&
            isValidEmail(edt_email_login.text.trim().toString())
        ) {
            userViewModel.login(
                edt_email_login.text.trim().toString(),
                edt_password_login.text.trim().toString(),
                AUTHORIZATION + fcmToken
            )
            isCheckLogin = true
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


