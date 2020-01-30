package com.hrms.hrmsandroidapp.screens.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hrms.hrmsandroidapp.R
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import android.view.View
import android.widget.Toast
import com.annimon.stream.Stream
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import kotlinx.android.synthetic.main.activity_calender.*
import kotlinx.android.synthetic.main.option_layout.*
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.applandeo.materialcalendarview.utils.calendar
import com.applandeo.materialcalendarview.utils.setCurrentMonthDayColors
import java.util.*


class CalenderActivity : AppCompatActivity(), View.OnClickListener, OnCalendarPageChangeListener, OnSelectDateListener {

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)
        mContext = this
        init()
    }

    private fun init() {
        //openManyDaysPicker()
        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val clickedDayCalendar = eventDay.calendar

            }
        })
        calendarView.setOnForwardPageChangeListener(this)
        calendarView.setOnPreviousPageChangeListener(this)
        calendarView.selectedDates = getSelectedDays()


        radio_leave_layout.setOnClickListener(this)
        radio_work_from_home_layout.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        btn_ok.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.radio_leave_layout -> {
                img_leave.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)
                img_work_from_home.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
            }

            R.id.radio_work_from_home_layout -> {
                img_work_from_home.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)
                img_leave.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
            }
            R.id.btn_cancel -> {

            }

            R.id.btn_ok -> {
                for (calendar in calendarView.selectedDates) {
                    println(calendar.time.toString())
                    Toast.makeText(applicationContext, calendar.time.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onChange() {

    }

    private fun getSelectedDays(): List<Calendar> {
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
}