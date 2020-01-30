package com.hrms.hrmsandroidapp.screens.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.listener.ISelectedDateListener
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import com.hrms.hrmsandroidapp.screens.changepassword.ChangePasswordActivity
import com.hrms.hrmsandroidapp.util.CommonUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import java.text.SimpleDateFormat

class HomeActivity : SubBaseActivity(), View.OnClickListener, ISelectedDateListener {
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_home)
        layoutInflater.inflate(R.layout.activity_home, fragment_layout)
        setToolBarTittle(getString(R.string.app_name))
        mContext=this
        init()
        showMenu()
        hideBackbtn()
    }

    private fun init() {

        attendence_layout.setOnClickListener(this)
        leave_layout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.attendence_layout->{
                val intent = Intent(mContext, SwitchActivity::class.java)
                startActivity(intent)
            }
            R.id.leave_layout->{
                val intent = Intent(mContext, CalenderActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun setSelectedDate(date: String) {

    }

}