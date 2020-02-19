package com.hrms.hrmsandroidapp.screens.switchscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.hrms.hrmsandroidapp.R
import kotlinx.android.synthetic.main.activity_switch.*
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.listener.ReasonDialogueClickListener
import com.hrms.hrmsandroidapp.util.CommonUtils
import android.graphics.PorterDuff
import android.graphics.Color
import android.os.Build
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hrms.hrmsandroidapp.api.NetworkStatus
import com.hrms.hrmsandroidapp.model.outputmodel.CheckInAndCheckOutDetail
import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes
import com.hrms.hrmsandroidapp.model.outputmodel.DemoResp
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import com.hrms.hrmsandroidapp.screens.home.HomeActivity
import com.hrms.hrmsandroidapp.screens.login.LoginPresenter
import com.hrms.hrmsandroidapp.util.Validation
import com.hrms.hrmsandroidapp.util.showToastMsg
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar_home.*
import java.text.ParseException
import java.util.*
import java.text.SimpleDateFormat

class SwitchActivity : SubBaseActivity(), ReasonDialogueClickListener,SwitchContract.View {

    private var mContext: Context? = null
    var checked:Boolean=false
    private lateinit var checkInTime:String
    private lateinit var checkInTimeFormat:String
    private lateinit var checkInDate:String
    private lateinit var checkOutTime:String
    private lateinit var todayDateAndTime:String
    private lateinit var todayDateOnly:String
    private lateinit var currentdate:String
    private  var checkincount:Int = 0
    private lateinit var presenter: SwitchPresenter
    private lateinit var result: CheckInAndCheckOutDetail

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_switch)
        layoutInflater.inflate(R.layout.activity_switch, fragment_layout)

        mContext = this
        setToolBarTittle(getString(R.string.CHECKING_SCREEN))
        hideNotificationView()

        init()
    }

    override fun init() {
        presenter= SwitchPresenter(this)
        getTodayDateAndTime()
        getCurrentDateAndTime()
        //updateUI()

        if(CommonUtils.getCheckInTime().isNotEmpty()){
            updateUI()
        }
        else if(CommonUtils.getCheckInTime().isNotEmpty()&& CommonUtils.getCheckOutime().isNotEmpty()){
            updateUI()
            switchButton.isClickable = false
        }
        else{
           doCheckInAndCheckOut()
        }


        switchButton.setOnClickListener {
            if((currentdate==CommonUtils.getCheckInDate())&&CommonUtils.getCheckInTime().isNotEmpty()&&CommonUtils.getCheckOutime().isNotEmpty()) {
                switchButton.isChecked = false
                switchButton.isClickable = false
                Toast.makeText(mContext, "unable to check in again", Toast.LENGTH_SHORT).show()
            }
            else if ((todayDateOnly==CommonUtils.getsavedCheckinAndOutDate())&&CommonUtils.getsavedCheckinTime().isNotEmpty()&&CommonUtils.getsavedCheckOutTime().isNotEmpty()) {
                switchButton.isChecked = false
                switchButton.isClickable = false
                Toast.makeText(mContext, "unable to check in again", Toast.LENGTH_SHORT).show()
            }
            else{
                if (switchButton.isChecked) {
                    CommonUtils.customisedCheckOutDialogue(this, listener = this, body = getString(R.string.tx_check_in),
                        switchButton = switchButton, type = Constants.CHECK_IN)
                }
                else{
                    CommonUtils.customisedCheckOutDialogue(this, listener = this, body = getString(R.string.tx_chech_out),
                        switchButton = switchButton,type=Constants.CHECK_OUT)
                }
            }
        }

        /* switchButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            */
        /* if (currentdate == CommonUtils.getCheckInDate()) {
                switchButton.isChecked = false
                Toast.makeText(mContext, "unable to check in again", Toast.LENGTH_SHORT).show()
            } else {
                CommonUtils.customisedCheckOutDialogue(this, listener = this, body = getString(R.string.tx_check_in), switchButton = switchButton, type = Constants.CHECK_IN)
            }*/
        /*
            if (switchButton.isChecked) {
                CommonUtils.customisedCheckOutDialogue(
                    this, listener = this, body = getString(R.string.tx_check_in),
                    switchButton = switchButton, type = Constants.CHECK_IN)
            } else {
                CommonUtils.customisedCheckOutDialogue(
                    this, listener = this, body = getString(R.string.tx_chech_out),
                    switchButton = switchButton, type = Constants.CHECK_OUT)
            }
        })*/
    }

    private fun updateUI() {
        if(todayDateAndTime=="06:00 AM"){
            checked=false
            CommonUtils.saveAsCheckedIn(checked)
            Log.e("clearing check in data","6 am")
        }
        else{
            if(CommonUtils.getCheckInTime().isNotEmpty()){
                time_layout.visibility=View.VISIBLE
                tv_checked_time.text = CommonUtils.getCheckInTime()
            }
            if(CommonUtils.getCheckOutime().isNotEmpty()){
                checked_out_layout.visibility=View.VISIBLE
                tv_checked_out_time.text = CommonUtils.getCheckOutime()
            }
            if(CommonUtils.getCheckedInData()==true){
                switchButton.isChecked=true
                switchButton.text = getString(R.string.Check_in)
                switchButton.thumbDrawable.setColorFilter(if (switchButton.isChecked) Color.BLUE else Color.GRAY, PorterDuff.Mode.MULTIPLY)
                switchButton.trackDrawable.setColorFilter(if (!switchButton.isChecked) Color.BLUE else Color.GRAY, PorterDuff.Mode.MULTIPLY)
            }
            if(CommonUtils.getCheckedInData()==false){
                switchButton.isChecked=false
                switchButton.text = getString(R.string.Check_out)
                switchButton.thumbDrawable.setColorFilter(if (switchButton.isChecked) Color.LTGRAY else Color.GRAY, PorterDuff.Mode.MULTIPLY)
                switchButton.trackDrawable.setColorFilter(if (!switchButton.isChecked) Color.LTGRAY else Color.GRAY, PorterDuff.Mode.MULTIPLY)
            }
        }
    }

    override fun onClick(type: String?, op: String?) {
        when (type) {
            Constants.ORDER_CANCELLED_CONFIRMATION -> {
                when(op){
                    Constants.CHECK_IN->{
                        time_layout.visibility=View.VISIBLE
                        if (switchButton.isChecked) {
                            checked=true
                            /* checkincount++
                             Toast.makeText(mContext, checkincount, Toast.LENGTH_SHORT).show()

                             CommonUtils.saveCheckinCount(checkincount)
 */
                            //CommonUtils.saveAsCheckedIn(checked)
                            switchButton.text = getString(R.string.Check_in)
                            switchButton.thumbDrawable.setColorFilter(if (switchButton.isChecked) Color.BLUE else Color.GRAY, PorterDuff.Mode.MULTIPLY)
                            switchButton.trackDrawable.setColorFilter(if (!switchButton.isChecked) Color.BLUE else Color.GRAY, PorterDuff.Mode.MULTIPLY)
                            getCurrentCheckedInTime()
                            doCheckIn()
                        }
                    }
                    Constants.CHECK_OUT->{
                        checked_out_layout.visibility=View.VISIBLE
                        checked=false
                        // CommonUtils.saveAsCheckedIn(checked)
                        switchButton.text = getString(R.string.Check_out)
                        switchButton.thumbDrawable.setColorFilter(if (switchButton.isChecked) Color.LTGRAY else Color.GRAY, PorterDuff.Mode.MULTIPLY)
                        switchButton.trackDrawable.setColorFilter(if (!switchButton.isChecked) Color.LTGRAY else Color.GRAY, PorterDuff.Mode.MULTIPLY)
                        getCurrentCheckedOutInTime()
                        docheckout()
                    }
                }
            }
        }
    }

    override fun doCheckInAndCheckOut() {
        if (NetworkStatus.isOnline2(this)) {
            showLoader()
            presenter.callCheckInAndCheckOutApi(date =todayDateAndTime)
        } else {
            showToastMsg(getString(R.string.error_no_internet))
        }
    }

    override fun setCheckInAndCheckOutResp(res: CheckInAndCheckOutDetail) {
        if (Validation.isValidStatus(res)) {
            hideLoader()
            CommonUtils.saveCheckedInAndCheckOutTimings(res)
            setdata()
            //showToastMsg("Updated CheckIn And CheckOut data ")
        }
        else if (res.message != null) {
            showMsg(res.message)
        }
    }

    private fun setdata() {
        /*if ((CommonUtils.getsavedCheckinTime().isNotEmpty())&&(CommonUtils.getsavedCheckOutTime().isNotEmpty())){
            switchButton.isClickable = false
        }*/
        if(CommonUtils.getsavedCheckinTime().isNotEmpty()){
            time_layout.visibility=View.VISIBLE
           /* val inTime=CommonUtils.getsavedCheckinTime()

            val input = SimpleDateFormat("hh:mm:ss")
            val output = SimpleDateFormat("hh:mm a")
            try {
                val parsingDate = input.parse(inTime)
                tv_checked_time.text = output.toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }*/
            tv_checked_time.text = CommonUtils.getsavedCheckinTime()
            switchButton.isChecked=true
            switchButton.text = getString(R.string.Check_in)
            switchButton.thumbDrawable.setColorFilter(if (switchButton.isChecked) Color.BLUE else Color.GRAY, PorterDuff.Mode.MULTIPLY)
            switchButton.trackDrawable.setColorFilter(if (!switchButton.isChecked) Color.BLUE else Color.GRAY, PorterDuff.Mode.MULTIPLY)
        }

        if(CommonUtils.getsavedCheckOutTime().isNotEmpty()) {
            checked_out_layout.visibility = View.VISIBLE
           // val df = SimpleDateFormat("hh:mm a")
            tv_checked_out_time.text = CommonUtils.getsavedCheckOutTime()
            switchButton.isChecked=false
            switchButton.text = getString(R.string.Check_out)
            switchButton.thumbDrawable.setColorFilter(if (switchButton.isChecked) Color.LTGRAY else Color.GRAY, PorterDuff.Mode.MULTIPLY)
            switchButton.trackDrawable.setColorFilter(if (!switchButton.isChecked) Color.LTGRAY else Color.GRAY, PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun doCheckIn() {
        if (NetworkStatus.isOnline2(this)) {
            showLoader()
            presenter.callCheckInApi(date =todayDateAndTime,checkintime =checkInTime,checked=true)
        } else {
            showToastMsg(getString(R.string.error_no_internet))
        }
    }

    override fun setCheckInResp(res: CommonRes,checked: Boolean) {
        if (Validation.isValidStatus(res)) {
            hideLoader()
            CommonUtils.saveAsCheckedIn(checked)
            showToastMsg("Successfully checked in and saved data ")
        }
        else if (res.message != null) {
            showMsg(res.message)
        }
    }

    override fun docheckout() {
        if (NetworkStatus.isOnline2(this)) {
            showLoader()
            presenter.callCheckOutApi(date =todayDateAndTime,checkouttime = checkOutTime,checked=false)
        } else {
            showToastMsg(getString(R.string.error_no_internet))
        }
    }

    override fun setCheckOutResp(res: CommonRes,checked: Boolean) {
        if (Validation.isValidStatus(res)) {
            hideLoader()
            CommonUtils.saveAsCheckedIn(checked)
            showToastMsg("Successfully checked Out and saved data ")
        }
        else if (res.message != null) {
            showMsg(res.message)
        }
    }

    override fun showMsg(msg: String?) {
        showToastMsg(msg!!)
    }

    override fun showLoader() {
        CommonUtils.showLoading(this, true)
    }

    override fun hideLoader() {
        CommonUtils.hideLoading()
    }

    override fun showViewState(state: Int) {

    }

    fun getTodayDateAndTime() {
        val currentTime = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy hh:mm a")//17 Feb 2020 4:00PM
        val d = SimpleDateFormat("yyyy-MM-dd") // 2020-02-17
        todayDateAndTime = df.format(currentTime.time)
        todayDateOnly = d.format(currentTime.time)
        //CommonUtils.saveTodaydate(todayDateAndTime)
    }

    fun getCurrentDateAndTime() {
        val currentTime = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy")
        currentdate = df.format(currentTime.time)
        tv_checked_date.text = currentdate
    }

    fun getCurrentCheckedInTime() {
        val currentTime = Calendar.getInstance().time
        val df = SimpleDateFormat("hh:mm a") //09:40 AM
        val d = SimpleDateFormat("dd-MMM-yyyy") // 19-Feb-2020
        val timeformat = SimpleDateFormat("HH:mm:ss")
        checkInTime = df.format(currentTime.time)
        checkInDate = d.format(currentTime.time)
        CommonUtils.saveCheckInTime(checkInTime)
        CommonUtils.saveCheckInDate(checkInDate)
        tv_checked_time.text = checkInTime
    }

    fun getCurrentCheckedOutInTime() {
        val currentTime = Calendar.getInstance().time
        val df = SimpleDateFormat("hh:mm a")
        checkOutTime = df.format(currentTime.time)
        CommonUtils.saveCheckOutTime(checkOutTime)
        tv_checked_out_time.text = checkOutTime
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigateToHomeScreen()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateToHomeScreen()
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(mContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}