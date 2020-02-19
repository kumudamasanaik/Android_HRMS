package com.hrms.hrmsandroidapp.listener

import java.util.*
import kotlin.collections.ArrayList

interface LeaveDialogueClickListener {
    fun onClick(type:String?="none",reason:String,op:String?="none",list:ArrayList<String>?)
}