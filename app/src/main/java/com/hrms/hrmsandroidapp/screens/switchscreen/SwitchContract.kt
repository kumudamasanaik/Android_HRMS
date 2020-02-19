package com.hrms.hrmsandroidapp.screens.switchscreen

import com.hrms.hrmsandroidapp.model.outputmodel.CheckInAndCheckOutDetail
import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes
import com.hrms.hrmsandroidapp.model.outputmodel.DemoResp
import com.hrms.hrmsandroidapp.util.BasePresenter
import com.hrms.hrmsandroidapp.util.BaseView

interface SwitchContract {
    interface View:BaseView{
        fun doCheckInAndCheckOut()
        fun doCheckIn()
        fun docheckout()
        fun setCheckInResp(res: CommonRes,checked: Boolean)
        fun setCheckOutResp(res: CommonRes,checked: Boolean)
        fun setCheckInAndCheckOutResp(res: CheckInAndCheckOutDetail)
    }

    interface Presenter:BasePresenter<View>{
        fun callCheckInApi(date: String,checkintime:String,checked:Boolean)
        fun callCheckOutApi(date: String,checkouttime:String,checked:Boolean)

        fun callCheckInAndCheckOutApi(date: String)
    }

}