package com.hrms.hrmsandroidapp.screens.leavehistory

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.api.NetworkStatus
import com.hrms.hrmsandroidapp.commonadapter.BaseRecAdapter
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.customviews.MultiStateView
import com.hrms.hrmsandroidapp.listener.IAdapterClickListener
import com.hrms.hrmsandroidapp.listener.LeaveDialogueClickListener
import com.hrms.hrmsandroidapp.listener.ReasonDialogueClickListener
import com.hrms.hrmsandroidapp.model.outputmodel.*
import com.hrms.hrmsandroidapp.model.outputmodel.history.HistoryResp
import com.hrms.hrmsandroidapp.model.outputmodel.history.LeaveItem
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import com.hrms.hrmsandroidapp.util.CommonUtils
import com.hrms.hrmsandroidapp.util.Validation
import com.hrms.hrmsandroidapp.util.showToastMsg
import kotlinx.android.synthetic.main.activity_leave_history.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.empty_view.*
import kotlinx.android.synthetic.main.error_view.*

class LeaveHistoryActivity : SubBaseActivity(), IAdapterClickListener, LeaveHistoryContract.View, LeaveDialogueClickListener {

    lateinit var notificationAdapter: BaseRecAdapter
    private var mContext: Context? = null
    private lateinit var presenter: LeaveHistoryPresenter
    private  var leave_history:ArrayList<LeaveItem>?=null
    private lateinit var snackbbar: View
    private val myselectedlist = arrayListOf<String>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_leave_history)
        layoutInflater.inflate(R.layout.activity_leave_history, fragment_layout)
        setToolBarTittle(getString(R.string.LEAVE_HISTORY))
        mContext = this
        init()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun init() {
        snackbbar = snack_barview
        presenter = LeaveHistoryPresenter(this)
        setUpRecyclerView()
        empty_button.setOnClickListener { getLeaveHistory() }
        error_button.setOnClickListener { getLeaveHistory() }
        getLeaveHistory()
    }

    private fun setUpRecyclerView() {
        val linearLayout = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        notificationAdapter = BaseRecAdapter(this, R.layout.partialy_history_list_items, adapterType = Constants.TYPE_HISTORY_ADAPTER,
            adapterClickListener = this)
        rv_history.apply {
            layoutManager = linearLayout
            adapter = notificationAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun onclick(item: Any, pos: Int, type: String?, op: String, view: View) {
        if (item is LeaveItem) {
            when(op){
                Constants.CANCEL->{
                    myselectedlist.add(item.date!!)
                    CommonUtils.customiseDialogue(context,listener = this,body = "Do You want to cancel Leave",list=myselectedlist ,
                        type = "C",reason = item.reason!!)
                }
            }
        }
    }

    override fun onClick(type: String?, reason: String, op: String?, list: ArrayList<String>?) {
        when (type) {
            Constants.ORDER_CANCELLED_CONFIRMATION -> {
                presenter.callCancelLeaveApi(date = list!!.toString(),reason = reason,type = "C")
            }
        }
    }

    override fun doCancelLeave() {
        if (NetworkStatus.isOnline2(this)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
           // presenter.callCancelLeaveApi()
        } else {
            showToastMsg(getString(R.string.error_no_internet))
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    override fun setCancelLeaveResp(res: CommonRes) {
        if (Validation.isValidStatus(res)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            showToastMsg("Successfully canceled ")
            getLeaveHistory()
        } else
            showViewState(MultiStateView.VIEW_STATE_ERROR)
    }

        override fun getLeaveHistory() {
        if (NetworkStatus.isOnline2(this)) {
            showViewState(MultiStateView.VIEW_STATE_LOADING)
            presenter.callGetLeaveHistory()
        } else {
            showToastMsg(getString(R.string.error_no_internet))
            showViewState(MultiStateView.VIEW_STATE_ERROR)
        }
    }

    override fun setLeaveHistoryResp(res: HistoryResp) {
        if (Validation.isValidStatus(res)) {
            showViewState(MultiStateView.VIEW_STATE_CONTENT)
            if (!res.leavedetails!![0].Item.isNullOrEmpty()) {//Todo check when Item list is coming emply , handle it ..
                leave_history = res.leavedetails[0].Item
                setData()
            }
            else
                showViewState(MultiStateView.VIEW_STATE_EMPTY)
        }
        else
            showViewState(MultiStateView.VIEW_STATE_ERROR)
    }

    private fun setData() {
        leave_history.apply {
            if (Validation.isValidList(leave_history)) {
                notificationAdapter.addList(leave_history!!)
            }
        }
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
        if (base_multistateview != null)
            base_multistateview.viewState = state
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
