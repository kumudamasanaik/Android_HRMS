package com.hrms.hrmsandroidapp.screens.switchscreen

import com.hrms.hrmsandroidapp.AppController
import com.hrms.hrmsandroidapp.api.ApiRequestParam
import com.hrms.hrmsandroidapp.api.ApiResponsePresenter
import com.hrms.hrmsandroidapp.api.ApiType
import com.hrms.hrmsandroidapp.api.IResponseInterface
import com.hrms.hrmsandroidapp.model.outputmodel.CheckInAndCheckOutDetail
import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes
import com.hrms.hrmsandroidapp.model.outputmodel.DemoResp
import retrofit2.Call
import retrofit2.Response

class SwitchPresenter(view: SwitchContract.View) : SwitchContract.Presenter, IResponseInterface {

var checkedParameter:Boolean = false
    private var iResponseInterface: ApiResponsePresenter = ApiResponsePresenter(this)
    var view: SwitchContract.View? = view

    override fun callCheckInApi(date: String, checkintime: String,checked:Boolean) {
        checkedParameter=checked
        iResponseInterface.callApi(AppController.service.getCheckIn(ApiRequestParam.checkInParameter(date,checkintime)), ApiType.CHECK_IN)
    }

    override fun callCheckOutApi(date: String, checkouttime: String,checked:Boolean) {
        checkedParameter=checked
        iResponseInterface.callApi(AppController.service.getCheckOut(ApiRequestParam.checkOutParameter(date, checkouttime)), ApiType.CHECK_OUT)
    }

    override fun callCheckInAndCheckOutApi(date: String) {
        iResponseInterface.callApi(AppController.service.getCheckInAndCheckOut(ApiRequestParam.checkInAndCheckOutParameter(date)), ApiType.CHECK_IN_AND_CHECK_OUT)
    }

    override fun <T> onResponseSuccess(call: Call<T>, response: Response<T>, reqType: String) {
        view?.hideLoader()
        if (response.isSuccessful) {
            when (reqType) {
                ApiType.CHECK_IN ->
                    view?.setCheckInResp(response.body() as CommonRes,checkedParameter)

            ApiType.CHECK_OUT ->
                    view?.setCheckOutResp(response.body() as CommonRes,checkedParameter)

             ApiType.CHECK_IN_AND_CHECK_OUT ->
                    view?.setCheckInAndCheckOutResp(response.body() as CheckInAndCheckOutDetail)

            }
        }
    }

    override fun <T> onResponseFailure(call: Call<T>, responseError: Throwable, reqType: String) {
        view?.hideLoader()
        when (reqType) {
            ApiType.CHECK_IN ->
                view?.showMsg(responseError.message)
            ApiType.CHECK_OUT ->
                view?.showMsg(responseError.message)
            ApiType.CHECK_IN_AND_CHECK_OUT ->
                view?.showMsg(responseError.message)
        }
    }
}