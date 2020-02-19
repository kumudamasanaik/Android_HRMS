package com.hrms.hrmsandroidapp.screens.changepassword

import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import com.hrms.hrmsandroidapp.util.BasePresenter
import com.hrms.hrmsandroidapp.util.BaseView

interface ChangePasswordContract {
    interface View : BaseView {
        fun doChangePassword()
        fun setChangePasswordRes(res: LoginResp)
    }

    interface Presenter : BasePresenter<View> {
        fun callChangePasswordApi(changed_password: String)

    }
}