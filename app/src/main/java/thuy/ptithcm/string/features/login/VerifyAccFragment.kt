package thuy.ptithcm.string.features.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_verify_acc.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.viewmodel.UserViewModel
import thuy.ptithcm.string.utils.isNetworkAvailable


class VerifyAccFragment : Fragment() {

    companion object {
        private var instance: VerifyAccFragment? = null
        fun getInstance(): VerifyAccFragment {
            if (instance == null) instance =
                VerifyAccFragment()
            return instance!!
        }
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(UserViewModel::class.java)
    }
    private var isResendMail = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verify_acc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_email_verify.text = getString(R.string.mail_verify, userViewModel.getEmailRegister())
        addEvent()
        bindings()
    }

    private fun bindings() {
        userViewModel.dataResendEmail.observe(this, androidx.lifecycle.Observer { dataResendEmail ->
            if (isResendMail) { // Resent mail success
                progressbar_verify_email.visibility = View.GONE
                if (dataResendEmail.status == true) {
                    showLoginFragment()
                    isResendMail = false
                } else { // Resent mail fail
                    btn_resend_email.isEnabled = true
                    btn_resend_email.setTextColor(
                        ContextCompat.getColorStateList(
                            requireContext(),
                            R.color.colorPurple
                        )
                    )
                }
            }
        })
    }

    private fun showLoginFragment() {
        activity!!.supportFragmentManager.beginTransaction()
            .add(R.id.frm_landing,
                LoginFragment.getInstance()
            )
            .addToBackStack(null)
            .commit()
    }

    private fun addEvent() {
        btn_open_mail_app.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            startActivity(emailIntent)
        }

        btn_resend_email.setOnClickListener {
            resendEmail()
        }
    }

    private fun resendEmail() {
        if (!requireContext().isNetworkAvailable()) {
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
            tv_message_verify.visibility = View.VISIBLE
            tv_message_verify.text = getString(R.string.errConnection)
        } else {
            tv_message_verify.visibility = View.GONE
            isResendMail = true
            progressbar_verify_email.visibility = View.VISIBLE
            btn_resend_email.isEnabled = false
            btn_resend_email.setTextColor(
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.colorGrayEnable
                )
            )
            userViewModel.getCode()?.let { userViewModel.resendEmailRegister(it) }
        }
    }
}
