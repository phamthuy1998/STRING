package thuy.ptithcm.string.features.login

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_register_email.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.viewmodel.UserViewModel
import thuy.ptithcm.string.utils.*
import java.util.*

class SignUpEmailFragment : Fragment(), TextWatcher {

    companion object {
        private var instance: SignUpEmailFragment? = null
        fun getInstance(): SignUpEmailFragment {
            if (instance == null) instance =
                SignUpEmailFragment()
            return instance!!
        }
    }

    private lateinit var email: String
    private lateinit var name: String
    private lateinit var userName: String
    private lateinit var dayOfBirth: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private var isTextOk = false
    private var cbTerm = false
    private var isRegisterOk = false

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings()
        addEvent()
    }

    private fun bindings() {
        userViewModel.dataRegister.observe(this, Observer { dataRegister ->
            if (isRegisterOk) {
                pb_register_acc_email.visibility = View.GONE
                if (dataRegister.status == true) {// Register success
                    tv_message_register.visibility = View.GONE
                    // save email register
                    userViewModel.setEmailRegister(email)
                    dataRegister.data?.code?.let { userViewModel.setCode(it) }
                    // Show verify email fragment
                    if (!VerifyAccFragment().isAdded)
                        activity!!.supportFragmentManager.beginTransaction()
                            .add(R.id.frm_landing,
                                VerifyAccFragment.getInstance()
                            )
                            .addToBackStack(null)
                            .commit()
                    isRegisterOk = false
                } else { // Register fail
                    tv_message_register.visibility = View.VISIBLE
                    tv_message_register.text = dataRegister.message
                }
            }
        })
    }

    private fun addEvent() {
        // Show login fragment
        btn_login_in_register.setOnClickListener {
            if (!LoginFragment().isAdded)
                activity!!.supportFragmentManager.beginTransaction()
                    .add(R.id.frm_landing,
                        LoginFragment.getInstance()
                    )
                    .addToBackStack("bb")
                    .commit()
        }

        // Add text change listener
        edt_email_register.addTextChangedListener(this)
        edt_name_register.addTextChangedListener(this)
        edt_day_of_birth_register.addTextChangedListener(this)
        edt_confirm_password_register.addTextChangedListener(this)
        edt_password_register.addTextChangedListener(this)
        edt_user_name_register.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tv_username_count.text = (30 - s.toString().length).toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tv_username_count.text = (30 - count).toString()
                email = edt_email_register.getTextTrim()
                name = edt_name_register.getTextTrim()
                dayOfBirth = edt_day_of_birth_register.getTextTrim()
                password = edt_password_register.getTextTrim()
                confirmPassword = edt_confirm_password_register.getTextTrim()
                userName = edt_user_name_register.getTextTrim()
                isTextOk =
                    userName != "" && email != "" && name != "" && dayOfBirth != "" && password != "" && confirmPassword != ""
                if (cbTerm && isTextOk) {
                    btn_sign_up.isEnabled = true
                    btn_sign_up.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.colorPurple)
                } else {
                    btn_sign_up.isEnabled = false
                    btn_sign_up.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.colorGrayEnable)
                }
            }
        })

        // Show dialog day time
        edt_day_of_birth_register.setOnClickListener {
            it.hideKeyboard()
            showDatetimePicker(edt_day_of_birth_register)
        }

        // Checkbox listener
        cb_terms_register.setOnCheckedChangeListener { _, isChecked ->
            cbTerm = isChecked
            if (cbTerm && isTextOk) {
                btn_sign_up.isEnabled = true
                btn_sign_up.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.colorPurple)
            } else {
                btn_sign_up.isEnabled = false
                btn_sign_up.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.colorGrayEnable)
            }
        }
        // Button login event
        btn_sign_up.setOnClickListener {
            it.hideKeyboard()
            checkRegister()
        }

        // Hide keyboard
        ll_register.setOnClickListener { it.hideKeyboard() }
    }

    private fun showDatetimePicker(editText: EditText) {
        var cal = Calendar.getInstance()
        var yyyy = cal.get(Calendar.YEAR)
        var mm = cal.get(Calendar.MONTH)
        var dd = cal.get(Calendar.DAY_OF_MONTH)

        if (editText.getTextTrim() != "") {
            val dateArr = editText.getTextTrim().split("/")
            yyyy = dateArr[2].toInt()
            mm = dateArr[1].toInt() - 1
            dd = dateArr[0].toInt()
        } else {
            yyyy -= 14
        }

        val dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, yearDl, monthOfYear, dayOfMonth ->
                var date1 = dayOfMonth.toString()
                var month1 = (monthOfYear + 1).toString()
                if (date1.length == 1) date1 = "0$date1"
                if (month1.length == 1) month1 = "0$month1"
                val date = "$date1/$month1/$yearDl"
                editText.setText(date)
            }, yyyy, mm, dd
        )
        cal = Calendar.getInstance()
        // Set min date
        cal[Calendar.MONTH] = cal.get(Calendar.MONTH)
        cal[Calendar.DAY_OF_MONTH] = cal.get(Calendar.DAY_OF_MONTH)
        cal[Calendar.YEAR] = cal.get(Calendar.YEAR) - 100

        cal = Calendar.getInstance()
        // Set max date
        cal[Calendar.MONTH] = cal.get(Calendar.MONTH)
        cal[Calendar.DAY_OF_MONTH] = cal.get(Calendar.DAY_OF_MONTH)
        cal[Calendar.YEAR] = cal.get(Calendar.YEAR) - 14
        dpd.datePicker.maxDate = cal.timeInMillis
        dpd.show()
    }

    private fun checkRegister() {
        if (!requireContext().isNetworkAvailable()) {
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
            tv_message_register.visibility = View.VISIBLE
            tv_message_register.text = getString(R.string.errConnection)
        } else {
            tv_message_register.visibility = View.GONE
            if (!isValidEmail(email)) {
                isRegisterOk = false
                edt_email_register.error = getString(R.string.err_email_not_valid)
            }
            if (!isValidUsername(userName)) {
                isRegisterOk = false
                edt_user_name_register.error = getString(R.string.err_username_not_valid)
            }
            if (!isValidPassword(password)) {
                isRegisterOk = false
                edt_password_register.error = getString(R.string.err_pw_not_valid)
            }
            if (confirmPassword != password) {
                isRegisterOk = false
                edt_confirm_password_register.error = getString(R.string.err_pw_not_match)
            }

            if (isValidEmail(email) && isValidUsername(userName) && isValidPassword(password) && confirmPassword == password) {
                isRegisterOk = true
                pb_register_acc_email.visibility = View.VISIBLE
                Log.d(
                    "aaaa1", userName + "|" +
                            name + "|" + dayOfBirth + "|" + email + "|" +
                            password + "|" + confirmPassword
                )
                userViewModel.registerAccByEmail(
                    userName,
                    name,
                    dayOfBirth,
                    email,
                    password,
                    confirmPassword
                )
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (cbTerm) {
            email = edt_email_register.getTextTrim()
            name = edt_name_register.getTextTrim()
            dayOfBirth = edt_day_of_birth_register.getTextTrim()
            password = edt_password_register.getTextTrim()
            confirmPassword = edt_confirm_password_register.getTextTrim()
            userName = edt_user_name_register.getTextTrim()
            isTextOk =
                userName != "" && email != "" && name != "" && dayOfBirth != "" && password != "" && confirmPassword != ""
            if (isTextOk) {
                btn_sign_up.isEnabled = true
                btn_sign_up.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.colorPurple)
            } else {
                btn_sign_up.isEnabled = false
                btn_sign_up.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.colorGrayEnable)
            }
        } else {
            btn_sign_up.isEnabled = false
            btn_sign_up.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.colorGrayEnable)
        }
    }
}
