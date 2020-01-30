package com.hrms.hrmsandroidapp.screens.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hrms.hrmsandroidapp.R
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.annimon.stream.operator.IntArray
import kotlinx.android.synthetic.main.activity_calender.*

class CalenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)
        init()
    }

    private fun init() {
        /*calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val clickedDayCalendar = eventDay.calendar
            }
        })*/
    }
}
