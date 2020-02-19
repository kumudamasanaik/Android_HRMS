package com.hrms.hrmsandroidapp.screens.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import com.hrms.hrmsandroidapp.screens.calenderscreen.CalenderActivity
import com.hrms.hrmsandroidapp.screens.changepassword.ChangePasswordActivity
import com.hrms.hrmsandroidapp.screens.leavehistory.LeaveHistoryActivity
import com.hrms.hrmsandroidapp.screens.switchscreen.SwitchActivity
import com.hrms.hrmsandroidapp.util.CommonUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.partial_profile_detail.*

class HomeActivity : SubBaseActivity(), View.OnClickListener {
    private var mContext: Context? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_home)
        layoutInflater.inflate(R.layout.activity_home, fragment_layout)
        setToolBarTittle(getString(R.string.app_name))
        mContext = this
        init()
        hideBackbtn()
    }

    private fun init() {
        updateUI()
        attendence_layout.setOnClickListener(this)
        leave_layout.setOnClickListener(this)
        change_password_layout.setOnClickListener(this)
        leave_history_layout.setOnClickListener(this)
        admin_layout.visibility=View.GONE
    }

    private fun updateUI() {
        tv_profile_name.text=CommonUtils.getProfileName()
        tv_email.text=CommonUtils.getEmailId()
        tv_mobile_num.text=CommonUtils.getMobieNumber()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.attendence_layout -> {
                navigateToSwitchActivity()
            }

            R.id.leave_layout -> {
                navigateToCalenderActivity()
            }

            R.id.change_password_layout -> {
                navigateToChangePassWordScreen()
            }

            R.id.leave_history_layout -> {
                navigateToLeaveHistoryScreen()
            }
        }
    }

    private fun navigateToLeaveHistoryScreen() {
        val intent = Intent(mContext, LeaveHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToChangePassWordScreen() {
        val intent = Intent(mContext, ChangePasswordActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToCalenderActivity() {
        val intent = Intent(mContext, CalenderActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSwitchActivity() {
        val intent = Intent(mContext, SwitchActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}