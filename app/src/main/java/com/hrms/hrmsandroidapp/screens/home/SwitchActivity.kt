package com.hrms.hrmsandroidapp.screens.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hrms.hrmsandroidapp.R
import kotlinx.android.synthetic.main.activity_switch.*
import com.google.android.material.snackbar.Snackbar
import android.widget.CompoundButton
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.listener.ReasonDialogueClickListener
import com.hrms.hrmsandroidapp.util.CommonUtils
import com.hrms.hrmsandroidapp.util.showToastMsg
import android.graphics.PorterDuff
import android.R.attr.checked
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.graphics.Color


class SwitchActivity : AppCompatActivity(), ReasonDialogueClickListener {
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch)
        mContext=this
        init()
    }

    private fun init() {
        switchButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            switchButton.isChecked = if (isChecked) true else false

            if (isChecked) {
                switchButton.getThumbDrawable().setColorFilter(
                    if (isChecked) Color.BLUE else Color.GRAY,
                    PorterDuff.Mode.MULTIPLY
                )
                switchButton.getTrackDrawable().setColorFilter(
                    if (!isChecked) Color.BLUE else Color.GRAY,
                    PorterDuff.Mode.MULTIPLY
                )

                showToastMsg("Api call")
            } else {
                CommonUtils.customisedReasonDialogue(this,listener = this,body =  getString(R.string.tx_chech_out),switchButton = switchButton)
            }
        })
    }

    override fun onClick(type: String?) {
        when(type){
            Constants.ORDER_CANCELLED_CONFIRMATION->{
                switchButton.getThumbDrawable().setColorFilter(
                    if (switchButton.isChecked) Color.WHITE else Color.GRAY,
                    PorterDuff.Mode.MULTIPLY
                )
                switchButton.getTrackDrawable().setColorFilter(
                    if (!switchButton.isChecked) Color.WHITE else Color.GRAY,
                    PorterDuff.Mode.MULTIPLY
                )
                showToastMsg("Api2 call")
            }
        }
    }

}