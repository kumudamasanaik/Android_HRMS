package com.hrms.hrmsandroidapp.screens.leavehistory

import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes
import com.hrms.hrmsandroidapp.model.outputmodel.history.HistoryResp
import com.hrms.hrmsandroidapp.util.BasePresenter
import com.hrms.hrmsandroidapp.util.BaseView

interface LeaveHistoryContract {
    interface View:BaseView{
        fun getLeaveHistory()
        fun doCancelLeave()
        fun setLeaveHistoryResp(res: HistoryResp)
        fun setCancelLeaveResp(res: CommonRes)
    }

    interface Presenter:BasePresenter<View>{
        fun callGetLeaveHistory()
        fun callCancelLeaveApi(date: String, type: String,reason:String)
    }

}