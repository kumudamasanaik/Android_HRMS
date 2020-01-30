package com.hrms.hrmsandroidapp.screens.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.screens.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext=this
    }

    override fun onResume() {
        super.onResume()
        navigateScreen()
    }

    private fun navigateScreen() {
        Thread(Runnable {
            Thread.sleep(3000)
            this@SplashActivity.runOnUiThread {
                    navigateToLoginScreen()
            }
        }).start()
    }

    fun navigateToLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
