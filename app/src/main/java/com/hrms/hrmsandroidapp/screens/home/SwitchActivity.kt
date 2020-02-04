package com.hrms.hrmsandroidapp.screens.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hrms.hrmsandroidapp.R
import kotlinx.android.synthetic.main.activity_switch.*
import android.widget.CompoundButton
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.listener.ReasonDialogueClickListener
import com.hrms.hrmsandroidapp.util.CommonUtils
import com.hrms.hrmsandroidapp.util.showToastMsg
import android.graphics.PorterDuff
import android.graphics.Color
import android.view.View
import java.util.*
import java.text.SimpleDateFormat


class SwitchActivity : AppCompatActivity(), ReasonDialogueClickListener {
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch)
        mContext = this
        init()
    }

    private fun init() {
        time_date_layout.visibility=View.GONE
        switchButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            //switchButton.isChecked = if (isChecked) true else false

            time_date_layout.visibility=View.VISIBLE
            if (isChecked) {
                switchButton.text = getString(R.string.Check_out)
                switchButton.getThumbDrawable().setColorFilter(
                    if (isChecked) Color.BLUE else Color.GRAY,
                    PorterDuff.Mode.MULTIPLY
                )
                switchButton.getTrackDrawable().setColorFilter(
                    if (!isChecked) Color.BLUE else Color.GRAY,
                    PorterDuff.Mode.MULTIPLY
                )
                getCurrentDateAndTime()
               // showToastMsg("Successfully checked in")
            } else {
                CommonUtils.customisedCheckOutDialogue(this, listener = this, body = getString(R.string.tx_chech_out), switchButton = switchButton)
            }
        })
    }

    override fun onClick(type: String?) {
        when (type) {
            Constants.ORDER_CANCELLED_CONFIRMATION -> {
                switchButton.getThumbDrawable().setColorFilter(
                    if (switchButton.isChecked) Color.WHITE else Color.GRAY,
                    PorterDuff.Mode.MULTIPLY
                )
                switchButton.getTrackDrawable().setColorFilter(
                    if (!switchButton.isChecked) Color.WHITE else Color.GRAY,
                    PorterDuff.Mode.MULTIPLY
                )
                switchButton.text = getString(R.string.Check_in)
                time_date_layout.visibility=View.VISIBLE
                getCurrentDateAndTime()
                //showToastMsg("Successfully checked out")
            }
        }
    }

    fun getCurrentDateAndTime() {
        val currentTime = Calendar.getInstance().getTime()
        val df = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
        val formattedDate = df.format(currentTime.getTime())
        tv_checked_date.text = formattedDate.toString()
    }
}