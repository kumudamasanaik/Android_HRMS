package com.hrms.hrmsandroidapp.screens.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.commonadapter.BaseRecAdapter
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.listener.IAdapterClickListener
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.app_bar_home.*

class NotificationActivity : SubBaseActivity(), IAdapterClickListener {

    lateinit var notificationAdapter: BaseRecAdapter
    private var mContext: Context? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_notification)
        layoutInflater.inflate(R.layout.activity_notification, fragment_layout)
        setToolBarTittle(getString(R.string.NOTIFICATION))
        mContext=this
        init()
        hideNotificationView()
    }

    private fun init() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val linearLayout = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        notificationAdapter = BaseRecAdapter(this, R.layout.partialy_notification_list_items, adapterType = Constants.TYPE_NOTIFICATION_ADAPTER, adapterClickListener = this)
        rv_notification.apply {
            layoutManager = linearLayout
            adapter = notificationAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun onclick(item: Any, pos: Int, type: String?, op: String, view: View) {

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