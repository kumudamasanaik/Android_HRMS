package com.hrms.hrmsandroidapp.screens.login

import com.hrms.hrmsandroidapp.AppController
import com.hrms.hrmsandroidapp.api.ApiRequestParam
import com.hrms.hrmsandroidapp.api.ApiResponsePresenter
import com.hrms.hrmsandroidapp.api.ApiType
import com.hrms.hrmsandroidapp.api.IResponseInterface
import com.hrms.hrmsandroidapp.model.outputmodel.DemoResp
import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class LoginPresenter(view: LoginContract.View) : LoginContract.Presenter, IResponseInterface {
    lateinit var user_id:String
    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    var view: LoginContract.View? = view

    override fun validateLogin(name: String?, pass: String?): Boolean {
        if (name.isNullOrEmpty()) {
            view?.invalidUserId()
            return false
        }
        if (pass.isNullOrEmpty()) {
            view?.invalidPass()
            return false
        }
        return true
    }

    override fun doLogin(username: String, password: String,device_id:String) {
        user_id=username
        iResponseInterface.callApi(AppController.service.doLogin(ApiRequestParam.login(username,password)), ApiType.LOGIN)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiType.LOGIN ->
                    view?.setLoginResp(response.body() as LoginResp,user_id)
            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        when (reqType) {
            ApiType.LOGIN ->
                view?.showMsg(responseError.message)
        }
    }
}