package com.hrms.hrmsandroidapp.screens.changepassword

import com.hrms.hrmsandroidapp.AppController
import com.hrms.hrmsandroidapp.api.ApiRequestParam
import com.hrms.hrmsandroidapp.api.ApiResponsePresenter
import com.hrms.hrmsandroidapp.api.ApiType
import com.hrms.hrmsandroidapp.api.IResponseInterface
import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import retrofit2.Call
import retrofit2.Response

class ChangePasswordPresenter(view: ChangePasswordContract.View) : ChangePasswordContract.Presenter, IResponseInterface {
    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    var view: ChangePasswordContract.View? = view


    override fun callChangePasswordApi(changed_password: String) {
        iResponseInterface.callApi(AppController.service.doChangePassword(ApiRequestParam.changePassword( changed_password)), ApiType.CHANGE_PASSWORD)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiType.CHANGE_PASSWORD ->
                    view?.setChangePasswordRes(response.body() as LoginResp)
            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        when (reqType) {
            ApiType.CHANGE_PASSWORD ->
                view?.showMsg(responseError.message)
        }
    }
}