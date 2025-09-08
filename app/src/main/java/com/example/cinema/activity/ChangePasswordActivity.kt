package com.example.cinema.activity

import android.os.Bundle
import android.widget.Toast
import com.example.cinema.R
import com.example.cinema.databinding.ActivityChangePasswordBinding
import com.example.cinema.model.*
import com.example.cinema.prefs.DataStoreManager
import com.example.cinema.util.StringUtil
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : BaseActivity() {

    private var mActivityChangePasswordBinding: ActivityChangePasswordBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityChangePasswordBinding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(mActivityChangePasswordBinding!!.root)
        mActivityChangePasswordBinding!!.imgBack.setOnClickListener { onBackPressed() }
        mActivityChangePasswordBinding!!.btnChangePassword.setOnClickListener { onClickValidateChangePassword() }
    }

    private fun onClickValidateChangePassword() {
        val strOldPassword = mActivityChangePasswordBinding!!.edtOldPassword.text.toString().trim { it <= ' ' }
        val strNewPassword = mActivityChangePasswordBinding!!.edtNewPassword.text.toString().trim { it <= ' ' }
        val strConfirmPassword = mActivityChangePasswordBinding!!.edtConfirmPassword.text.toString().trim { it <= ' ' }
        if (StringUtil.isEmpty(strOldPassword)) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_old_password_require), Toast.LENGTH_SHORT).show()
        } else if (StringUtil.isEmpty(strNewPassword)) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_new_password_require), Toast.LENGTH_SHORT).show()
        } else if (StringUtil.isEmpty(strConfirmPassword)) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_confirm_password_require), Toast.LENGTH_SHORT).show()
        } else if (DataStoreManager.getUser()?.password != strOldPassword) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_old_password_invalid), Toast.LENGTH_SHORT).show()
        } else if (strNewPassword != strConfirmPassword) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_confirm_password_invalid), Toast.LENGTH_SHORT).show()
        } else if (strOldPassword == strNewPassword) {
            Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_new_password_invalid), Toast.LENGTH_SHORT).show()
        } else {
            changePassword(strNewPassword)
        }
    }

    private fun changePassword(newPassword: String) {
        showProgressDialog(true)
        val user = FirebaseAuth.getInstance().currentUser ?: return
        user.updatePassword(newPassword)
                .addOnCompleteListener { task: Task<Void?> ->
                    showProgressDialog(false)
                    if (task.isSuccessful) {
                        Toast.makeText(this@ChangePasswordActivity, getString(R.string.msg_change_password_successfully), Toast.LENGTH_SHORT).show()
                        val userLogin: User? = DataStoreManager.getUser()
                        userLogin?.password = newPassword
                        DataStoreManager.setUser(userLogin)
                        mActivityChangePasswordBinding!!.edtOldPassword.setText("")
                        mActivityChangePasswordBinding!!.edtNewPassword.setText("")
                        mActivityChangePasswordBinding!!.edtConfirmPassword.setText("")
                    }
                }
    }
}