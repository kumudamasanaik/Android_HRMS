package com.hrms.hrmsandroidapp.screens.base

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.AppBarLayout
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.screens.switchscreen.SwitchActivity
import com.hrms.hrmsandroidapp.util.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_sub_base.*
import kotlinx.android.synthetic.main.tool_bar_with_back.*
import androidx.annotation.RequiresApi

abstract class SubBaseActivity : AppCompatActivity() {
    lateinit var context: Context
    private var sharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_base)
        initScreen()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initScreen() {
        context = this
        drawer_layout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setSupportActionBar(my_tool_bar)
        showBack()
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setToolbarScrollFlags()
        setToolBarTittle(getString(R.string.app_name))
        hideSearchView()

        ic_notification.setOnClickListener {
            // startActivity(Intent(this, CartActivity::class.java))
            val intent = Intent(context, SwitchActivity::class.java)
            startActivity(intent)
            //startJobInNagout()
        }
    }


    fun showMenu() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_three_line)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    fun hideMenu() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_three_line)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    fun hideBackbtn() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_navigation)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    fun showBack() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_navigation)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    private fun hideSearchView() {
        //search_view!!.visibility = View.GONE
    }

    fun hideToolBarImage() {
       // my_tool_bar_image!!.visibility = View.GONE
    }

    fun showSearchView() {
        //search_view!!.visibility = View.VISIBLE
    }

    fun showCartView() {
        //ic_cart!!.visibility = View.VISIBLE
    }

    fun hideNotificationView() {
        notification_layout!!.visibility = View.GONE
    }

    private fun setToolbarScrollFlags(setFlags: Boolean = false) {
        val params: AppBarLayout.LayoutParams = my_tool_bar.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = if (setFlags) AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS else 0
    }

    fun setToolBarTittle(title: String?) {
        tv_screen_title.text = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout!!.openDrawer(GravityCompat.START)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        registerSharedPreferenceChangeListener()

    }

/*
    private fun startJob() {
        val componentName = ComponentName(this, JobScheduler17::class.java)
        val jobInfo = JobInfo.Builder(15, componentName)
            .setRequiresCharging(true)
            .build()
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = jobScheduler.schedule(jobInfo)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(this@SubBaseActivity, "Job Schedule started", Toast.LENGTH_LONG).show()
            Log.e("Scheduling job","Scheduled job")

        } else {
            Toast.makeText(this@SubBaseActivity, "Job Schedule not started", Toast.LENGTH_LONG).show()
        }
    }
*/
    private fun registerSharedPreferenceChangeListener() {
        sharedPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
            key?.let {
                if (it.contentEquals(SharedPreferenceManager.PREF_CART_DATA)) {
                    //updateCartCount()
                }
            }
        }
        SharedPreferenceManager.getPrefs().registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }

    override fun onPause() {
        super.onPause()
        unregisterSharedPreferenceChangeListener()
    }

    private fun unregisterSharedPreferenceChangeListener() {
        SharedPreferenceManager.getPrefs().unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
    }
}