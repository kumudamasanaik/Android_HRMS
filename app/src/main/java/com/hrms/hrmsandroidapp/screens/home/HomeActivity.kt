package com.hrms.hrmsandroidapp.screens.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.annimon.stream.Stream
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.applandeo.materialcalendarview.utils.calendar
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.listener.ISelectedDateListener
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import com.hrms.hrmsandroidapp.screens.changepassword.ChangePasswordActivity
import com.hrms.hrmsandroidapp.util.CommonUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : SubBaseActivity(), View.OnClickListener {
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_home)
        layoutInflater.inflate(R.layout.activity_home, fragment_layout)
        setToolBarTittle(getString(R.string.app_name))
        mContext = this
        init()
        showMenu()
    }

    private fun init() {
        attendence_layout.setOnClickListener(this)
        leave_layout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.attendence_layout -> {
                navigateToSwitchActivity()
            }

            R.id.leave_layout -> {
                navigateToCalenderActivity()
            }
        }
    }

    private fun navigateToCalenderActivity() {
        val intent = Intent(mContext, CalenderActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSwitchActivity() {
        val intent = Intent(mContext, SwitchActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

/*
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

    private fun getDisabledDays(): List<Calendar> {
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
    }


    override fun onSelect(calendars: List<Calendar>) {
        Stream.of(calendars).forEach { calendar ->
            Toast.makeText(
                applicationContext,
                calendar.time.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
*/

/*override fun setSelectedDate(date: String) {

    }
*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}