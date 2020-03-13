package thuy.ptithcm.string.features.user.activity


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_edit_profile.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.model.UserData
import thuy.ptithcm.string.features.user.viewmodel.UserViewModel
import thuy.ptithcm.string.utils.*
import java.util.*


class EditProfileActivity : AppCompatActivity() {

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000
        //Permission code
        private const val PERMISSION_CODE = 1001
        private var instance: EditProfileActivity? = null
        fun getInstance(): EditProfileActivity {
            if (instance == null) instance =
                EditProfileActivity()
            return instance!!
        }
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(this)
            .get(UserViewModel::class.java)
    }

    private var isClickButtonDone = false
    private var checkUrlValid = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        // Hide keyboard
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        bindings()
        addEvents()
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            if (data != null) {
                iv_avatar_edit.setImageURI(data.data)
                Log.d("immg", data.data.toString())
            }
        }
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun addEvents() {
        btn_change_avt.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    //permission already granted
                    pickImageFromGallery()
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }
        edt_day_of_birth_edit.setOnClickListener {
            showDatetimePicker()
        }

        btn_back_edit_profile.setOnClickListener { finish() }
        btn_done_edit_profile.setOnClickListener {
            saveEditProfile()
        }

        edt_user_name_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tv_username_edit_count.text = (30 - s.toString().length).toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tv_username_edit_count.text = (30 - count).toString()
                if (edt_user_name_edit.text.trim().toString() != "" && checkUrlValid) {
                    btn_done_edit_profile.isEnabled = true
                    btn_done_edit_profile.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.colorPurple)
                } else {
                    btn_done_edit_profile.isEnabled = false
                    btn_done_edit_profile.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.colorGrayEnable)
                }
            }
        })

        edt_bio_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tv_bio_edit_count.text = (150 - s.toString().length).toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tv_bio_edit_count.text = (150 - count).toString()
            }
        })

        edt_website_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkUrlValid =
                    if (s?.trim().toString() == "") true else isValidUrl(s?.trim().toString())
                if (edt_user_name_edit.text.trim().toString() != "" && checkUrlValid) {
                    btn_done_edit_profile.isEnabled = true
                    btn_done_edit_profile.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.colorPurple)
                } else {
                    btn_done_edit_profile.isEnabled = false
                    btn_done_edit_profile.backgroundTintList =
                        ContextCompat.getColorStateList(applicationContext, R.color.colorGrayEnable)
                }
            }

        })

        // Hide keyboard
        ll_edit_profile.setOnClickListener { it.hideKeyboard() }
    }

    private fun saveEditProfile() {
        if (!isNetworkAvailable()) {
            tv_message_edit_profile.visibility = View.VISIBLE
            tv_message_edit_profile.text = getString(R.string.errConnection)
        } else {
            tv_message_edit_profile.visibility = View.GONE
            getAccessToken()?.let {
                userViewModel.changeProfile(
                    file = "",
                    username = edt_user_name_edit.text.trim().toString(),
                    bio = edt_bio_edit.text.trim().toString().replace("\\s+".toRegex(), " "),
                    website = edt_website_edit.text.trim().toString(),
                    name = edt_name_edit.text.trim().toString(),
                    dayOfBirth = edt_day_of_birth_edit.text.trim().toString(),
                    accessToken = it
                )
            }
            isClickButtonDone = true
        }
    }

    private fun showDatetimePicker() {
        var cal = Calendar.getInstance()
        var yyyy = cal.get(Calendar.YEAR)
        var mm = cal.get(Calendar.MONTH)
        var dd = cal.get(Calendar.DAY_OF_MONTH)

        if (edt_day_of_birth_edit.text.trim().toString() != "") {
            val dateArr = edt_day_of_birth_edit.text.trim().toString().split("/")
            yyyy = dateArr[2].toInt()
            mm = dateArr[1].toInt() - 1
            dd = dateArr[0].toInt()
        } else {
            yyyy -= 14
        }

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, yearDl, monthOfYear, dayOfMonth ->
                var date1 = dayOfMonth.toString()
                var month1 = (monthOfYear + 1).toString()
                if (date1.length == 1) date1 = "0$date1"
                if (month1.length == 1) month1 = "0$month1"
                val date = "$date1/$month1/$yearDl"
                edt_day_of_birth_edit.setText(date)
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

    private fun bindings() {
        if (getUserID() != -1) {
            getAccessToken()?.let {
                getUserID()?.let { it1 ->
                    userViewModel.getUserProfile(
                        it,
                        it1
                    )
                }
            }
        } else {
            Toast.makeText(
                this,
                "Can't load information of account!",
                Toast.LENGTH_LONG
            ).show()
        }
        userViewModel.dataUserProfile.observe(this, Observer { userData ->
            if (userData != null) {
                if (userData.status == true)
                    inItViews(userData)
                else
                    showDialogErrorLogin()
            }
        })

        userViewModel.dataUserChange.observe(this, Observer { dataProfile ->
            if (dataProfile != null) {
                if (isClickButtonDone) {
                    isClickButtonDone = false
                    if (dataProfile.status == true) {
                        dataProfile.data?.access_token?.let { setAccessToken(it) }
                        tv_message_edit_profile.visibility = View.GONE
                        Toast.makeText(this, "Save change!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    if (dataProfile.status == false) {
                        tv_message_edit_profile.visibility = View.VISIBLE
                        tv_message_edit_profile.text = dataProfile.message
                        Toast.makeText(
                            this,
                            "Can't save change, Check error below!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

    private fun inItViews(userData: UserData) {
        userData.data?.refresh_token?.let { setAccessToken(it) }
        // Set image avatar
        Glide.with(this)
            .load(userData.data?.profilePhoto)
            .error(R.drawable.ic_acc_img)
            .into(iv_avatar_edit)

        edt_user_name_edit.setText(userData.data?.username)
        if (userData.data?.bio != null)
            edt_bio_edit.setText(userData.data.bio)
        if (userData.data?.website != null)
            edt_website_edit.setText(userData.data.website)
        if (userData.data?.name != null)
            edt_name_edit.setText(userData.data.name)
        edt_day_of_birth_edit.setText(userData.data?.date_of_birth)
        tv_bio_edit_count.text = (150 - edt_bio_edit.text.toString().length).toString()
        tv_username_edit_count.text = (30 - edt_user_name_edit.text.toString().length).toString()
    }
}
