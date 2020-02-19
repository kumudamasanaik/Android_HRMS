package com.hrms.hrmsandroidapp.screens.calenderscreen

import com.hrms.hrmsandroidapp.AppController
import com.hrms.hrmsandroidapp.api.ApiRequestParam
import com.hrms.hrmsandroidapp.api.ApiResponsePresenter
import com.hrms.hrmsandroidapp.api.ApiType
import com.hrms.hrmsandroidapp.api.IResponseInterface
import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes
import com.hrms.hrmsandroidapp.model.outputmodel.DemoResp
import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class CalenderPresenter(view: CalenderContract.View) : CalenderContract.Presenter, IResponseInterface {

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    var view: CalenderContract.View? = view


    override fun callapplyForLeaveApi(date: String, type: String, reason: String) {
        iResponseInterface.callApi(AppController.service.applyForLeave(ApiRequestParam.leaveRequestParameters(date,type,reason)), ApiType.LEAVE)
    }

    override fun callapplyWorkFromHomeApi(date: String, type: String, reason: String) {
        iResponseInterface.callApi(AppController.service.applyForLeave(ApiRequestParam.leaveRequestParameters(date,type,reason)), ApiType.WFH)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiType.LEAVE ->
                    view?.setapplyForLeaveResp(response.body() as CommonRes)

           ApiType.WFH ->
                    view?.setapplyWorkFromHomeResp(response.body() as CommonRes)
            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        when (reqType) {
            ApiType.LEAVE ->
                view?.showMsg(responseError.message)

         ApiType.WFH ->
                view?.showMsg(responseError.message)
        }
    }
}