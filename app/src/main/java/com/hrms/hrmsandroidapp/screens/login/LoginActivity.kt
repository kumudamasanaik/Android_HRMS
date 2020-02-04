package com.hrms.hrmsandroidapp.screens.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.screens.changepassword.ChangePasswordActivity
import com.hrms.hrmsandroidapp.screens.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext=this
        init()
    }

    private fun init() {
        btn_login.setOnClickListener(this)
       // tv_forgot_password.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_login->{
                navigateToHomeScreen()

            }
            /*R.id.tv_forgot_password->{
                navigateToChangePasswordScreen()

            }*/
        }
    }

    private fun navigateToChangePasswordScreen() {
        val intent = Intent(mContext, ChangePasswordActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(mContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}