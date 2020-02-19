package com.hrms.hrmsandroidapp.screens.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.api.NetworkStatus
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.model.outputmodel.DemoResp
import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import com.hrms.hrmsandroidapp.screens.changepassword.ChangePasswordActivity
import com.hrms.hrmsandroidapp.screens.home.HomeActivity
import com.hrms.hrmsandroidapp.util.CommonUtils
import com.hrms.hrmsandroidapp.util.Validation
import com.hrms.hrmsandroidapp.util.showToastMsg
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener,LoginContract.View {

    private var mContext: Context? = null
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext=this
        init()
    }

    override fun init() {
        btn_login.setOnClickListener(this)
        presenter= LoginPresenter(this)
       // tv_forgot_password.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_login->{
                doLogin()
            }
        }
    }

    override fun doLogin() {
        if (presenter.validateLogin(ed_employee_id.text.toString(), ed_password.text.toString())) {
            if (NetworkStatus.isOnline2(this)) {
                showLoader()
                presenter.doLogin(ed_employee_id.text.toString(), ed_password.text.toString(),Constants.DEVICE_ID)
            } else {
                showToastMsg(getString(R.string.error_no_internet))
            }
        }
    }

    override fun setLoginResp(res: LoginResp,username:String) {
        if (Validation.isValidStatus(res)) {
            hideLoader()
            CommonUtils.setCustomerData(res)
            CommonUtils.setUserID(username)
            navigateToHomeScreen()
        }
         else if (res.message != null) {
                 showMsg(res.message)
         }
    }

    override fun invalidUserId() {
        ed_employee_id.error = getString(R.string.error_invalid_user_id)
    }

    override fun invalidPass() {
        ed_password.error = getString(R.string.error_invalid_password)
    }

    override fun showMsg(msg: String?) {
        showToastMsg(msg!!)
    }

    override fun showLoader() {
        CommonUtils.showLoading(this, true)
    }

    override fun hideLoader() {
        CommonUtils.hideLoading()
    }

    override fun showViewState(state: Int) {

    }

    private fun navigateToHomeScreen() {
        val intent = Intent(mContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}