package com.hrms.hrmsandroidapp.util

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.listener.ISelectedDateListener
import com.hrms.hrmsandroidapp.listener.ReasonDialogueClickListener
import com.hrms.hrmsandroidapp.screens.home.HomeActivity
import kotlinx.android.synthetic.main.activity_switch.*
import kotlinx.android.synthetic.main.activity_switch.view.*
import kotlinx.android.synthetic.main.partail_return_popup_dialog.view.*
import java.io.ByteArrayOutputStream
import java.util.*

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
        fun showCalenderPicker(context: Context, listener: ISelectedDateListener, min: Long = 0, max: Long = 0) {
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

        fun customisedReasonDialogue(mcontext:Context, listener: ReasonDialogueClickListener?, body:String,switchButton:Switch){
            val builder = AlertDialog.Builder(mcontext)
            val inflater = LayoutInflater.from(mcontext)
            val dialogview = inflater.inflate(R.layout.partail_return_popup_dialog, null)

            dialogview.return_dialog_body.text = body
            builder.setView(dialogview)

            val dialog = builder.create()
            dialogview.return_btn_ok.setOnClickListener {
                dialog.dismiss()
                //var switchButton=dialogview.findViewById<View>(R.id.switchButton)

                switchButton.isChecked=false
                    listener?.onClick(type = Constants.ORDER_CANCELLED_CONFIRMATION)
            }

            dialogview.return_btn_cancel.setOnClickListener {
                switchButton.isChecked=true
                dialog.dismiss()
            }
            dialog.show()
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
       */ }
    }