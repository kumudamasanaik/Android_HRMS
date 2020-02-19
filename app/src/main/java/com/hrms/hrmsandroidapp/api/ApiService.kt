package com.hrms.hrmsandroidapp.api

import com.google.gson.JsonObject
import com.hrms.hrmsandroidapp.model.outputmodel.*
import com.hrms.hrmsandroidapp.model.outputmodel.history.HistoryResp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(ApiConstants.LOGIN)
    fun doLogin(@Body jsonObject: JsonObject): Call<LoginResp>

    @POST(ApiConstants.CHECK_IN)
    fun getCheckIn(@Body jsonObject: JsonObject): Call<CommonRes>

    @POST(ApiConstants.CHECK_OUT)
    fun getCheckOut(@Body jsonObject: JsonObject): Call<CommonRes>

    @POST(ApiConstants.CHECK_IN_AND_CHECK_OUT)
    fun getCheckInAndCheckOut(@Body jsonObject: JsonObject): Call<CheckInAndCheckOutDetail>

    @POST(ApiConstants.CHANGE_PASSWORD)
    fun doChangePassword(@Body jsonObject: JsonObject): Call<LoginResp>

    @POST(ApiConstants.LEAVE)
    fun applyForLeave(@Body jsonObject: JsonObject): Call<CommonRes>

    @POST(ApiConstants.LEAVE_HISTORY)
    fun getLeaveHistory(@Body jsonObject: JsonObject): Call<HistoryResp>

    @POST(ApiConstants.LEAVE)
    fun getCancelLeave(@Body jsonObject: JsonObject): Call<CommonRes>


}