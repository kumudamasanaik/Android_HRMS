package com.hrms.hrmsandroidapp.screens.splash

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.screens.alaramManager.MyBroadCastReceiver
import com.hrms.hrmsandroidapp.screens.home.HomeActivity
import com.hrms.hrmsandroidapp.screens.login.LoginActivity
import com.hrms.hrmsandroidapp.util.CommonUtils
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"
    private var mContext: Context? = null
    lateinit var current_time: String
    lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext = this

        alertOnTime()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        //alertOnTime()
        navigateScreen()
    }

    private fun navigateScreen() {
        Thread(Runnable {
            Thread.sleep(3000)
            this@SplashActivity.runOnUiThread {
                if (CommonUtils.isUserLogin())
                    navigateToHomeScreen()
                else
                    navigateToLoginScreen()
            }
        }).start()
    }

    fun navigateToLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun navigateToHomeScreen() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    /*Alarm Manager*/
    private fun alertOnTime() {

        val calendar = Calendar.getInstance()
         calendar.set(Calendar.HOUR_OF_DAY, 19)
        calendar.set(Calendar.MINUTE, 10)

        val intent12 = Intent(this, MyBroadCastReceiver::class.java)
        intent12.action = "FOO_ACTION"
        intent12.putExtra("KEY1_FOO_STRING", "Medium Demo1")
        val pendingIntent12 = PendingIntent.getBroadcast(this.applicationContext, 234324243, intent12, 0)
        startAlarm(calendar.timeInMillis, pendingIntent12)
        println("1st trigger time " + calendar.timeInMillis)

        val intent11 = Intent(this, MyBroadCastReceiver::class.java)
        intent11.action = "FOO_ACTION1"
        intent11.putExtra("KEY1_FOO_STRING", "Medium Demo2")
        val pendingIntent1 = PendingIntent.getBroadcast(this.applicationContext, 234324244, intent11, 0)
        startAlarm(calendar.timeInMillis*3*1000*60, pendingIntent1)
        println("2nd trigger time " + calendar.time)
    }

    fun startAlarm(seconds : Long, pendingIntent1 : PendingIntent){
        val alarmManager12 = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager12.set(AlarmManager.RTC_WAKEUP, seconds, pendingIntent1)
        println("time is "+seconds)
        Log.e("Started ","Yes")
    }

    fun dotask(hour: Int, minute: Int) {
/*val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 16)
            set(Calendar.MINUTE, 58)

            alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            pendingIntent = Intent(context, MyBroadCastReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, 0)
            }
                    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.timeInMillis, pendingIntent)

                    val formatter = SimpleDateFormat("hh:mm a")
                    val triggerTime = formatter.format(calendar.time)
                    println(" trigger time is  " + triggerTime)
                    Log.e("Start", "starting server :------------- ")
        }*/

        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("hh:mm a")
        val output = formatter.format(calendar.time)
        println("current time " + output)

        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, MyBroadCastReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val formatter = SimpleDateFormat("hh:mm a")
                val triggerTime = formatter.format(calendar.time)
                println("trigger time " + triggerTime)

                if (triggerTime >= output) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                    val formatter = SimpleDateFormat("hh:mm a")
                    val triggerTime = formatter.format(calendar.timeInMillis)
                    println(" second server is started at " + triggerTime)
                    Log.e("JOB", ">=Marshmallow:------------- ")
                } else {
                    Log.e("JOB", "trigger time is crossed ")
                }
            }
            /*Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                Log.e("JOB", ">=Kitkat:------------- ")
            }
            else -> {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                Log.e("JOB", "Other:------------- ")
            }*/
        }
    }
}