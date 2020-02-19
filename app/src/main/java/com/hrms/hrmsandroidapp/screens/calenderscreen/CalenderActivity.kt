package com.hrms.hrmsandroidapp.screens.calenderscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.hrms.hrmsandroidapp.R
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calender.*
import kotlinx.android.synthetic.main.option_layout.*
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.hrms.hrmsandroidapp.util.CommonUtils
import java.text.SimpleDateFormat
import java.util.*
import android.os.Build
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.listener.LeaveDialogueClickListener
import com.hrms.hrmsandroidapp.listener.ReasonDialogueClickListener
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import kotlinx.android.synthetic.main.app_bar_home.*
import com.applandeo.materialcalendarview.utils.calendar
import com.hrms.hrmsandroidapp.model.outputmodel.CommonRes
import com.hrms.hrmsandroidapp.model.outputmodel.LoginResp
import com.hrms.hrmsandroidapp.screens.home.HomeActivity
import com.hrms.hrmsandroidapp.screens.login.LoginPresenter
import com.hrms.hrmsandroidapp.util.Validation
import com.hrms.hrmsandroidapp.util.showToastMsg
import kotlin.collections.ArrayList

class CalenderActivity : SubBaseActivity(), View.OnClickListener, OnCalendarPageChangeListener, OnSelectDateListener,
    LeaveDialogueClickListener, ReasonDialogueClickListener,CalenderContract.View {

    private var mContext: Context? = null
    private val mylist = arrayListOf<Calendar>()
    private val myselectedlist = arrayListOf<String>()
    private lateinit var currentdate: Calendar
    private lateinit var calenderSelecteddate: Date
    private var selected: Boolean = true
    private lateinit var presenter: CalenderPresenter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_calender)
        layoutInflater.inflate(R.layout.activity_calender, fragment_layout)

        mContext = this
        setToolBarTittle(getString(R.string.CALANDER_SCREEN))
        getCurrentDate()

        val calenderinstance = Calendar.getInstance()
        calenderinstance.time = currentdate.time
        calendarView.setMinimumDate(calenderinstance)
        /*val f = CalendarView::class.java!!.getDeclaredField("mCalendarProperties")
        f.setAccessible(true)
        val p = f.get(calendarView) as CalendarProperties
            p.daysLabelsColor=resources.getColor(R.color.app_txt_white)
        calendarView.requestLayout()*/
        init()
    }

    override fun init() {
        presenter= CalenderPresenter(this)
        getCurrentDate()

        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val clickedDayCalendar = eventDay.calendar
                val calenderinstance = Calendar.getInstance()
                calenderinstance.time = currentdate.time
                calendarView.setMinimumDate(calenderinstance)
            }
        })

        calendarView.setOnForwardPageChangeListener(this)
        calendarView.setOnPreviousPageChangeListener(this)
        //calendarView.selectedDates = getSelectedDays()

        radio_leave_layout.setOnClickListener(this)
        radio_work_from_home_layout.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.radio_leave_layout -> {
                selected = true
                img_leave.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)
                img_work_from_home.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
            }

            R.id.radio_work_from_home_layout -> {
                selected = false
                img_work_from_home.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)
                img_leave.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
            }

            R.id.btn_cancel -> {
                myselectedlist.clear()
                for (calendar in calendarView.selectedDates) {
                    calenderSelecteddate=calendar.time
                    val df = SimpleDateFormat("dd-MMM-yyyy") //17-Feb-2020
                    val formattedSelectedDate = df.format(calenderSelecteddate)
                    //Toast.makeText(applicationContext, formattedDate, Toast.LENGTH_SHORT).show()
                    myselectedlist.remove(formattedSelectedDate)
                    CommonUtils.saveSelectedDays(myselectedlist)
                    println(" saving list is"+myselectedlist)
                }
                println("getting list is"+CommonUtils.getSelectedDays()+"list size is"+CommonUtils.getSelectedDays()!!.size)
                navigateToHomeScreen()
            }

            R.id.btn_ok -> {
                if (selected == true) {
                    if(calendarView.selectedDates.isNotEmpty()){
                        selectedDate()
                        CommonUtils.customisedReasonDialogue(this,listener = this, body = getString(R.string.Reason_for_leave),
                            selectedList = CommonUtils.getSelectedDays(),type=Constants.LEAVE)
                    }
                    else{
                        Toast.makeText(mContext, "date is not selected", Toast.LENGTH_SHORT).show()
                    }
                    /* mylist.clear()
                     for (calendar in calendarView.selectedDates) {
                         val df = SimpleDateFormat("dd-MMM-yyyy")
                         val formattedDate = df.format(calendar.time)
                         //Toast.makeText(applicationContext, formattedDate, Toast.LENGTH_SHORT).show()
                         mylist.add(calendar)
                         CommonUtils.saveSelectedDays(mylist)
                     }*/
                } else {
                    if(calendarView.selectedDates.isNotEmpty()){
                        selectedDate()
                        CommonUtils.customisedReasonDialogue(this,listener = this, body = getString(R.string.Reason_for_to_work_from_home),
                            selectedList = CommonUtils.getSelectedDays(), type=Constants.WFH)
                    }
                    else{
                        Toast.makeText(mContext, "date is not selected", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun  selectedDate():Date {
        myselectedlist.clear()
        for (calendar in calendarView.selectedDates) {
            calenderSelecteddate=calendar.time
            val df = SimpleDateFormat("dd MMM yyyy")
            val formattedSelectedDate = df.format(calenderSelecteddate)
            //Toast.makeText(applicationContext, formattedDate, Toast.LENGTH_SHORT).show()
            myselectedlist.add(formattedSelectedDate)
            CommonUtils.saveSelectedDays(myselectedlist)
            println(" saving list is"+myselectedlist)
        }
        println("getting list is"+CommonUtils.getSelectedDays())
        return calendar.time
    }

    override fun onClick(type: String?, reason: String, op: String?, list: ArrayList<String>?) {
        when (type) {
            Constants.ORDER_CANCELLED_CONFIRMATION -> {
                when(op){
                    Constants.LEAVE->{
                        //Toast.makeText(mContext, "From Leave"+list, Toast.LENGTH_SHORT).show()
                        presenter.callapplyForLeaveApi(date=list.toString(),type = "L",reason = reason)
                    }
                    Constants.WFH->{
                        //Toast.makeText(mContext, "From WFH"+list, Toast.LENGTH_SHORT).show()
                        presenter.callapplyWorkFromHomeApi(date=list.toString(),type = "WFH",reason = reason)
                    }
                }

            }
        }

    }

    override fun setapplyForLeaveResp(res: CommonRes) {
        if (Validation.isValidStatus(res)) {
            hideLoader()
            Toast.makeText(mContext, "Successfully Applied for leave", Toast.LENGTH_SHORT).show()
            navigateToHomeScreen()
        }
        else if (res.message != null) {
            showMsg(res.message)
        }
    }

    override fun setapplyWorkFromHomeResp(res: CommonRes) {
        if (Validation.isValidStatus(res)) {
            hideLoader()
            Toast.makeText(mContext, "Successfully Applied for Work From Home", Toast.LENGTH_SHORT).show()
            navigateToHomeScreen()
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

    override fun onClick(type: String?, op: String?) {
        when (type) {
            Constants.ORDER_CANCELLED_CONFIRMATION -> {

            }
        }
    }

    private fun navigateToHomeScreen() {
        val intent = Intent(mContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onChange() {
        val calenderinstance = Calendar.getInstance()
        calenderinstance.time = currentdate.time
        calendarView.setMinimumDate(calenderinstance)
    }

    /* private fun getSelectedDays(): List<Calendar> {
         val calendars = ArrayList<Calendar>()

         for (i in 0..9) {
             val calendar = calendar
             calendar.add(Calendar.DAY_OF_MONTH, i)
             calendars.add(calendar)
         }
         return calendars
     }

     private fun openManyDaysPicker() {
         val min = Calendar.getInstance()
         min.add(Calendar.DAY_OF_MONTH, -5)

         val max = Calendar.getInstance()
         max.add(Calendar.DAY_OF_MONTH, 3)

         val selectedDays = ArrayList(getDisabledDays())
         selectedDays.add(min)
         selectedDays.add(max)

         val manyDaysBuilder = DatePickerBuilder(this, this)
             .setPickerType(CalendarView.MANY_DAYS_PICKER)
             .setHeaderColor(android.R.color.holo_blue_dark)
             .setSelectionColor(android.R.color.holo_blue_dark)
             .setTodayLabelColor(android.R.color.holo_blue_dark)
             .setDialogButtonsColor(android.R.color.holo_blue_dark)
             .setSelectedDays(selectedDays)
             .setNavigationVisibility(View.GONE)
             .setDisabledDays(getDisabledDays())

         val manyDaysPicker = manyDaysBuilder.build()
         manyDaysPicker.show()
     }
*/

    /* private fun getDisabledDays(): List<Calendar> {
         val firstDisabled = calendar
         firstDisabled.add(Calendar.DAY_OF_MONTH, 2)

         val secondDisabled = calendar
         secondDisabled.add(Calendar.DAY_OF_MONTH, 1)

         val thirdDisabled = calendar
         thirdDisabled.add(Calendar.DAY_OF_MONTH, 18)

         val calendars = ArrayList<Calendar>()
         calendars.add(firstDisabled)
         calendars.add(secondDisabled)
         calendars.add(thirdDisabled)
         return calendars
     }*/

    override fun onSelect(calendars: List<Calendar>) {
        Log.e("Selected date is", calendars[0].toString())
    }

    fun getCurrentMonth() {
        //calendarView.showCurrentMonthPage()
        val currentTime = Calendar.getInstance().getTime()
        val df = SimpleDateFormat("MMM")
        // formatteMonth = df.format(currentTime.getTime())
    }

    fun getCurrentDate(): Date {
        /*setting 1 day before current day*/
        currentdate = Calendar.getInstance();
        currentdate.add(Calendar.DATE, -1)
        return currentdate.time

        /* val formatter = SimpleDateFormat("dd-MMM-yyyy")
         currentdate = Date()
         println(formatter.format(currentdate))
         return   currentdate
         Log.e("CurrentDate",currentdate.toString())*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}