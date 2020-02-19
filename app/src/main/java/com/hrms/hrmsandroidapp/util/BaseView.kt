package com.hrms.hrmsandroidapp.util

interface BaseView {

    fun init()

    fun showMsg(msg: String?)

    fun showLoader()

    fun hideLoader()

    fun showViewState(state: Int)
}