package com.hrms.hrmsandroidapp.util

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hrms.hrmsandroidapp.AppController.Companion.context
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.listener.ISelectedDateListener
import com.hrms.hrmsandroidapp.listener.LeaveDialogueClickListener
import com.hrms.hrmsandroidapp.listener.ReasonDialogueClickListener
import com.hrms.hrmsandroidapp.model.outputmodel.CheckInAndCheckOutDetail
import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes
import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import com.hrms.hrmsandroidapp.util.SharedPreferenceManager.Companion.getMyPrefVal
import com.hrms.hrmsandroidapp.util.SharedPreferenceManager.Companion.getPrefVal
import com.hrms.hrmsandroidapp.util.SharedPreferenceManager.Companion.setMyPrefVal
import com.hrms.hrmsandroidapp.util.SharedPreferenceManager.Companion.setPrefVal
import kotlinx.android.synthetic.main.activity_switch.*
import kotlinx.android.synthetic.main.partail_check_out_dialog.view.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class CommonUtils {
    companion object {
        private var dateListener: ISelectedDateListener? = null
        private var TAG: String = "COMMON UTILS"
        private lateinit var reason: String
        private var myProgressDialog: ProgressDialog? = null


        fun showLoading(mContext: Context, cancelable: Boolean = false) {
            try {
                hideLoading()
                myProgressDialog = ProgressDialog(mContext, R.style.AppTheme_Loading_Dialog)
                myProgressDialog?.apply {
                    setMessage(mContext.getString(R.string.please_wait))
                    setCancelable(true)
                    setOnCancelListener {
                        dismiss()
                    }
                    show()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        fun hideLoading() {
            myProgressDialog?.apply {
                if (isShowing) {
                    dismiss()
                    myProgressDialog = null
                }
            }
        }

        fun convertToBase64(bitmap: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
            val byteArrayImage = baos.toByteArray()
            return Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
        }


        /*CALENDER */
        fun showCalenderPicker(
            context: Context,
            listener: ISelectedDateListener,
            min: Long = 0,
            max: Long = 0
        ) {
            dateListener = listener
            val mYear: Int
            val mMonth: Int
            val mDay: Int
            val cal = Calendar.getInstance()
            mYear = cal.get(Calendar.YEAR)
            mMonth = cal.get(Calendar.MONTH)
            mDay = cal.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context, R.style.MyDatePickerDialogTheme,
                { view, year, monthOfYear, dayOfMonth ->
                    val selCal = Calendar.getInstance()
                    selCal.timeInMillis = 0
                    selCal.set(year, monthOfYear, dayOfMonth, 0, 0, 0)
                    listener.setSelectedDate(String.format(" %1\$tb %1\$td,%1\$tY", selCal))
                }, mYear, mMonth, mDay

            )

            if (min > 0)
                datePickerDialog.datePicker.minDate = min
            else
                datePickerDialog.datePicker.minDate = cal.timeInMillis
            if (max > 0)
                datePickerDialog.datePicker.maxDate = max
            /*else
            datePickerDialog.datePicker.maxDate = cal.timeInMillis*/
            datePickerDialog.setTitle("")
            datePickerDialog.show()
        }

        fun customisedCheckOutDialogue(mcontext: Context, listener: ReasonDialogueClickListener?, body: String, switchButton: Switch,type:String) {
            val builder = AlertDialog.Builder(mcontext)
            val inflater = LayoutInflater.from(mcontext)
            val dialogview = inflater.inflate(R.layout.partail_check_out_dialog, null)

            dialogview.return_dialog_body.text = body
            builder.setView(dialogview)

            val dialog = builder.create()
            dialogview.return_btn_ok.setOnClickListener {
                dialog.dismiss()
                if(type==Constants.CHECK_IN){
                    switchButton.isChecked = true
                    switchButton.text ="Check In"
                }
                else{
                    switchButton.isChecked = false
                    switchButton.text ="Check Out"
                }
                listener?.onClick(type = Constants.ORDER_CANCELLED_CONFIRMATION,op=type)
            }

            dialogview.return_btn_cancel.setOnClickListener {
                if(type==Constants.CHECK_OUT){
                    switchButton.isChecked = true
                    switchButton.text ="Check In"
                    dialog.dismiss()
                }
                else{
                    switchButton.isChecked = false
                    switchButton.text = "Check Out"
                    dialog.dismiss()
                }
                dialog.dismiss()
            }
            dialog.show()
        }

        fun saveSelectedDays(selectedDays: ArrayList<String>) {
            val gson = Gson()
            val json = gson.toJson(selectedDays)
            setPrefVal(Constants.SELECTED_DAYS, json, SharedPreferenceManager.VALUE_TYPE.STRING)
            //setMyPrefVal(Constants.SELECTED_DAYS, selectedDays, SharedPreferenceManager.VALUE_TYPE.STRING)
            println(" Save In SharedPreference"+selectedDays)
        }

        fun getSelectedDays(): ArrayList<String>? {
            val gson = Gson()
            val json = getPrefVal(Constants.SELECTED_DAYS, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
            if (Validation.isValidString(json)) {
                val type = object : TypeToken<ArrayList<String>>() {}.type
                println(" get In SharedPreference"+gson.fromJson(json, type))
                return  gson.fromJson(json, type)
            }
            // val getList= getMyPrefVal(Constants.SELECTED_DAYS, arrayListOf<String>(), SharedPreferenceManager.VALUE_TYPE.STRING) as String
            /* if (!(getList.isEmpty())){
                 return arrayListOf()
             }*/
            return null
        }

        fun saveAsCheckedIn(checked: Boolean) {
            setPrefVal(SharedPreferenceManager.USER_CHHECKED, checked, SharedPreferenceManager.VALUE_TYPE.BOOLEAN)
        }

        fun getCheckedInData(): Boolean {
            return getPrefVal(SharedPreferenceManager.USER_CHHECKED, false, SharedPreferenceManager.VALUE_TYPE.BOOLEAN) as Boolean
        }

        fun saveCheckInTime(checkintime: String) {
            setPrefVal(Constants.CHECK_IN_TIME, checkintime, SharedPreferenceManager.VALUE_TYPE.STRING)
        }

        fun getCheckInTime(): String {
            return getPrefVal(Constants.CHECK_IN_TIME, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }

        fun saveCheckOutTime(checkouttime: String) {
            setPrefVal(Constants.CHECK_OUT_TIME, checkouttime, SharedPreferenceManager.VALUE_TYPE.STRING)
        }

        fun getCheckOutime(): String {
            return getPrefVal(Constants.CHECK_OUT_TIME, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }

        fun saveCheckinCount(checkincount: Int) {
            setPrefVal(Constants.CHECK_IN_COUNT, checkincount, SharedPreferenceManager.VALUE_TYPE.INTEGER)
        }

        fun getCheckInCount(): Int {
            return getPrefVal(Constants.CHECK_IN_COUNT, 0, SharedPreferenceManager.VALUE_TYPE.INTEGER) as Int
        }

        fun saveCheckInDate(checkInDate: String) {
            setPrefVal(Constants.CHECK_IN_DATE, checkInDate, SharedPreferenceManager.VALUE_TYPE.STRING)
        }

        fun getCheckInDate(): String {
            return getPrefVal(Constants.CHECK_IN_DATE, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }

        /* fun showDialog(context: Context, body: String) {
             val builder = AlertDialog.Builder(context)
             val inflater = LayoutInflater.from(context)
             val dialogview = inflater.inflate(R.layout.partail_popup_dialog, null)

             dialogview.dialog_body.text = body
             builder.setView(dialogview)
             val dialog = builder.create()
             dialogview.btn_ok.setOnClickListener {
                 dialog.dismiss()
                 val bundle = Bundle()
                 startActivity(context, LoginActivity::class.java, bundle, true)
             }
             dialogview.btn_cancel.setOnClickListener {
                 dialog.dismiss()
             }
             dialog.show()
         }
        */


        /*Asking Reason Dialogue*/
        fun customisedReasonDialogue(mcontext: Context, listener: LeaveDialogueClickListener?, body: String,selectedList:ArrayList<String>?,type:String) {
            val builder = AlertDialog.Builder(mcontext)
            val inflater = LayoutInflater.from(mcontext)
            val dialogview = inflater.inflate(R.layout.partail_leave_popup_dialog, null)

            dialogview.return_dialog_body.text = body
            builder.setView(dialogview)

            val dialog = builder.create()
            dialogview.return_btn_ok.setOnClickListener {
                dialog.dismiss()
                val text_reason = dialogview.findViewById<EditText>(R.id.ed_return_reason)
                if (text_reason.text.isNullOrEmpty()) {
                    Toast.makeText(mcontext, " Reason must not be null or empty", Toast.LENGTH_LONG).show()
                } else {
                    var reason = text_reason.text.toString()
                    listener?.onClick(type = Constants.ORDER_CANCELLED_CONFIRMATION, reason = reason,op=type,list=selectedList)
                }
            }

            dialogview.return_btn_cancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

        }

        fun customiseDialogue(mcontext: Context, listener: LeaveDialogueClickListener?, body: String,list:ArrayList<String>,type:String,reason:String) {
            val builder = AlertDialog.Builder(mcontext)
            val inflater = LayoutInflater.from(mcontext)
            val dialogview = inflater.inflate(R.layout.partail_check_out_dialog, null)

            dialogview.return_dialog_body.text = body
            builder.setView(dialogview)

            val dialog = builder.create()
            dialogview.return_btn_ok.setOnClickListener {
                dialog.dismiss()
                listener?.onClick(type = Constants.ORDER_CANCELLED_CONFIRMATION, reason = reason,op=type,list=list)
            }

            dialogview.return_btn_cancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }


        fun getDeviceID(): String {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
        }

        fun setCustomerData(customerData: LoginResp) {
            try {
                setUserLogin(true)
                setCurrentUser(customerData)
                if (Validation.validateValue(customerData.profilenme)) {
                    setPrefVal(Constants.PROFILE_NAME, customerData.profilenme ?: "", SharedPreferenceManager.VALUE_TYPE.STRING)
                    setPrefVal(Constants.MOBILE, customerData.mobile ?: "", SharedPreferenceManager.VALUE_TYPE.STRING)
                    setPrefVal(Constants.EMAIL, customerData.email ?: "", SharedPreferenceManager.VALUE_TYPE.STRING)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        fun setUserID(user_id: String) {
            setPrefVal(Constants.USER_ID, user_id, SharedPreferenceManager.VALUE_TYPE.STRING)
        }

        fun getUserID(): String {
            return getPrefVal(Constants.USER_ID, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }

        fun getProfileName(): String {
            return getPrefVal(Constants.PROFILE_NAME, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }

        fun getMobieNumber(): String {
            return getPrefVal(Constants.MOBILE, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }

        fun getEmailId(): String {
            return getPrefVal(Constants.EMAIL, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }

        private fun setCurrentUser(customer: LoginResp) {
            setPrefVal(Constants.CUR_CUSTOMER, Gson().toJson(customer), SharedPreferenceManager.VALUE_TYPE.STRING)
        }

        fun getCurrentUser(): LoginResp? {
            val json = getPrefVal(Constants.CUR_CUSTOMER, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
            return try {
                Gson().fromJson<LoginResp>(json, LoginResp::class.java) as LoginResp
            } catch (exp: Exception) {
                null
            }
        }


        fun setUserLogin(isLogin: Boolean) {
            setPrefVal(Constants.IS_USER_LOGIN, isLogin, SharedPreferenceManager.VALUE_TYPE.BOOLEAN)
        }

        fun isUserLogin(): Boolean {
            return getPrefVal(Constants.IS_USER_LOGIN, false, SharedPreferenceManager.VALUE_TYPE.BOOLEAN) as Boolean
        }

        fun saveCheckedInAndCheckOutTimings(res: CheckInAndCheckOutDetail) {
            try {
                    setPrefVal(Constants.CHECKINMTIME, res.checkintime ?: "", SharedPreferenceManager.VALUE_TYPE.STRING)
                    setPrefVal(Constants.CHECKOUTMTIME, res.checkouttime ?: "", SharedPreferenceManager.VALUE_TYPE.STRING)
                    setPrefVal(Constants.DATE, res.date ?: "", SharedPreferenceManager.VALUE_TYPE.STRING)

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        fun getsavedCheckinTime(): String {
            return getPrefVal(Constants.CHECKINMTIME, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }
        fun getsavedCheckOutTime(): String {
            return getPrefVal(Constants.CHECKOUTMTIME, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }
        fun getsavedCheckinAndOutDate(): String {
            return getPrefVal(Constants.DATE, "", SharedPreferenceManager.VALUE_TYPE.STRING) as String
        }

    }
}