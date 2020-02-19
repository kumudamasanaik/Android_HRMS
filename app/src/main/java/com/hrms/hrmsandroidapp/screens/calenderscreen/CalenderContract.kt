package com.hrms.hrmsandroidapp.screens.calenderscreen

import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes
import com.hrms.hrmsandroidapp.model.outputmodel.DemoResp
import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import com.hrms.hrmsandroidapp.util.BasePresenter
import com.hrms.hrmsandroidapp.util.BaseView

interface CalenderContract {
    interface View:BaseView{
        fun setapplyForLeaveResp(res: CommonRes)
        fun setapplyWorkFromHomeResp(res: CommonRes)
    }

    interface Presenter:BasePresenter<View>{
        fun callapplyForLeaveApi(date: String, type: String,reason:String)
        fun callapplyWorkFromHomeApi(date: String, type: String,reason:String)
    }

}