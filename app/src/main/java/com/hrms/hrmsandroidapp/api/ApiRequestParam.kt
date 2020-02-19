package com.hrms.hrmsandroidapp.api

import com.google.gson.JsonObject
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.util.CommonUtils

object ApiRequestParam {
    var respParamObj = JsonObject()

    /*LOGIN*/
    fun login(username: String, password: String): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.username, username)
            addProperty(Constants.password, password)
            addProperty(Constants.DEVICE_ID, CommonUtils.getDeviceID())
        }
        return respParamObj
    }

    fun checkInParameter(date: String,chekin: String): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.date, date)
            addProperty(Constants.checkintime, chekin)
            addProperty(Constants.username, CommonUtils.getUserID())
        }
        return respParamObj
    }

    fun checkOutParameter(date: String,chekout: String): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.date, date)
            addProperty(Constants.checkouttime, chekout)
            addProperty(Constants.username, CommonUtils.getUserID())
        }
        return respParamObj
    }

    fun changePassword(password: String): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.password, password)
            addProperty(Constants.username, CommonUtils.getUserID())

        }
        return respParamObj
    }

    fun checkInAndCheckOutParameter(date: String): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.date, date)
            addProperty(Constants.username, CommonUtils.getUserID())
        }
        return respParamObj

    }

    fun leaveRequestParameters(date: String, type: String, reason: String): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.date, date)
            addProperty(Constants.type, type)
            addProperty(Constants.reason, reason)
            addProperty(Constants.username, CommonUtils.getUserID())
        }
        return respParamObj
    }

    fun getLeaveHistoryParameter(): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.username, CommonUtils.getUserID())
        }
        return respParamObj
    }

    fun getCancelLeaveParameter(date: String, type: String, reason: String): JsonObject {
        respParamObj = JsonObject()
        respParamObj.apply {
            addProperty(Constants.date, date)
            addProperty(Constants.type, type)
            addProperty(Constants.reason, reason)
            addProperty(Constants.username, CommonUtils.getUserID())
        }
        return respParamObj
    }
}