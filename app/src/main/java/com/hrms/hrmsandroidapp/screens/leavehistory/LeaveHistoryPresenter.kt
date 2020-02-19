package com.hrms.hrmsandroidapp.screens.leavehistory

import com.hrms.hrmsandroidapp.AppController
import com.hrms.hrmsandroidapp.api.ApiRequestParam
import com.hrms.hrmsandroidapp.api.ApiResponsePresenter
import com.hrms.hrmsandroidapp.api.ApiType
import com.hrms.hrmsandroidapp.api.IResponseInterface
import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes
import com.hrms.hrmsandroidapp.model.outputmodel.history.HistoryResp
import retrofit2.Call
import retrofit2.Response

class LeaveHistoryPresenter(view: LeaveHistoryContract.View) : LeaveHistoryContract.Presenter, IResponseInterface {

    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    var view: LeaveHistoryContract.View? = view


    override fun callGetLeaveHistory() {
        iResponseInterface.callApi(AppController.service.getLeaveHistory(ApiRequestParam.getLeaveHistoryParameter()), ApiType.LEAVE_HISTORY)
    }

    override fun callCancelLeaveApi(date: String, type: String,reason:String) {
        iResponseInterface.callApi(AppController.service.getCancelLeave(ApiRequestParam.getCancelLeaveParameter(date,type,reason)), ApiType.CANCEL_LEAVE)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiType.LEAVE_HISTORY ->
                    view?.setLeaveHistoryResp(response.body() as HistoryResp)

             ApiType.CANCEL_LEAVE ->
                    view?.setCancelLeaveResp(response.body() as CommonRes)
            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        when (reqType) {
            ApiType.LEAVE_HISTORY ->
                view?.showMsg(responseError.message)

         ApiType.CANCEL_LEAVE ->
                view?.showMsg(responseError.message)
        }
    }
}