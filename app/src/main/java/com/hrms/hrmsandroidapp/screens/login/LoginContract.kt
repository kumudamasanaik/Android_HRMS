package com.hrms.hrmsandroidapp.screens.login

import com.hrms.hrmsandroidapp.model.outputmodel.DemoResp
import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import com.hrms.hrmsandroidapp.util.BasePresenter
import com.hrms.hrmsandroidapp.util.BaseView

interface LoginContract {
    interface View:BaseView{
        fun invalidUserId()
        fun invalidPass()
        fun doLogin()
        fun setLoginResp(res: LoginResp,username:String)
    }

    interface Presenter:BasePresenter<View>{
        fun validateLogin(name: String?, pass: String?): Boolean
        fun doLogin(username: String, password: String,device_id:String)
    }

}