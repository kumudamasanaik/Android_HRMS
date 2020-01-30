package com.hrms.hrmsandroidapp.screens.changepassword

import android.os.Bundle
import android.view.MenuItem
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import kotlinx.android.synthetic.main.app_bar_home.*

class ChangePasswordActivity : SubBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_change_password)
        layoutInflater.inflate(R.layout.activity_change_password, fragment_layout)
        setToolBarTittle(getString(R.string.CHANGE_PASSWORD))
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
