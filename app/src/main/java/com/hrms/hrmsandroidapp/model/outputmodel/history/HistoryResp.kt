package com.hrms.hrmsandroidapp.model.outputmodel.history

import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes

data class HistoryResp(
	val leavedetails: ArrayList<LeavedetailsItem>? = null
):CommonRes()
