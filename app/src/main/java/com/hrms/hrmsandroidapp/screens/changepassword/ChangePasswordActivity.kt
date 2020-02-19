package com.hrms.hrmsandroidapp.screens.changepassword

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.api.NetworkStatus
import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import com.hrms.hrmsandroidapp.screens.login.LoginActivity
import com.hrms.hrmsandroidapp.util.CommonUtils
import com.hrms.hrmsandroidapp.util.Validation
import com.hrms.hrmsandroidapp.util.showToastMsg
import com.hrms.hrmsandroidapp.util.toast
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.app_bar_home.*

class ChangePasswordActivity : SubBaseActivity(), View.OnClickListener,ChangePasswordContract.View {

    private lateinit var mContext: Context
    private lateinit var presenter: ChangePasswordPresenter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_change_password)
        layoutInflater.inflate(R.layout.activity_change_password, fragment_layout)
        setToolBarTittle(getString(R.string.CHANGE_PASSWORD))
        init()
    }

    override fun init() {
        mContext=this
        btn_submit.setOnClickListener(this)
        presenter= ChangePasswordPresenter(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_submit ->callPresenter()

        }
    }

    private fun callPresenter() {
        if (ed_new_password.text!!.isNotEmpty() && ed_confirm_password.text!!.isNotEmpty()) {
            if (ed_new_password.text.toString() == ed_confirm_password.text.toString()) {
                doChangePassword()
            } else
                toast(getString(R.string.err_conf_pass_match), Toast.LENGTH_LONG)
        }
        else
            toast(getString(R.string.error_empty_password), Toast.LENGTH_LONG)
    }


    override fun doChangePassword() {
        if (NetworkStatus.isOnline2(this)) {
            showLoader()
            presenter.callChangePasswordApi(ed_new_password.text.toString())
        } else
            showMsg(getString(R.string.error_no_internet))
    }

    override fun setChangePasswordRes(res: LoginResp) {
        if (Validation.isValidStatus(res)) {
            showMsg(res.message ?: getString(R.string.passwrd_changed_successfully))
            navigateLoginScreen()
        } else
            showMsg(res.message ?: getString(R.string.Whoops_Something_went_wrong))
    }

    private fun navigateLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}