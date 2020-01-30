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

class SwitchActivity : AppCompatActivity(), ReasonDialogueClickListener {
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch)
        mContext=this
        init()
    }

    private fun init() {
       // switchButton.isChecked=true

        switchButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            switchButton.isChecked = if (isChecked) true else false

            if (isChecked) {
                showToastMsg("Api call")
            } else {
                CommonUtils.customisedReasonDialogue(this,listener = this,body =  getString(R.string.tx_chech_out),switchButton = switchButton)
            }
        })

    }
    override fun onClick(type: String?) {
        when(type){
            Constants.ORDER_CANCELLED_CONFIRMATION->{
                showToastMsg("Api2 call")
            }
        }
    }

}