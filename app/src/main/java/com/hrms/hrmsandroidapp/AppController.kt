package com.hrms.hrmsandroidapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.hrms.hrmsandroidapp.api.ApiConstants
import com.hrms.hrmsandroidapp.api.ApiService
import com.hrms.hrmsandroidapp.customviews.AppSignatureHelper
import com.hrms.hrmsandroidapp.util.MyLogUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppController :Application(), Application.ActivityLifecycleCallbacks{

    companion object {
        val TAG = "AppController"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var mActivityRefernce: String = ""
        lateinit var service: ApiService
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        registerActivityLifecycleCallbacks(this)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        createRetroFitObject()
        AppSignatureHelper(this).appSignatures
    }

    private fun createRetroFitObject() {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(getRequestHeader())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(ApiService::class.java)
    }

    private fun getRequestHeader(): OkHttpClient {
        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.readTimeout(60, TimeUnit.SECONDS)
        okhttpClient.writeTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okhttpClient.addNetworkInterceptor(StethoInterceptor())
            okhttpClient.addInterceptor(logging)
        }
        return okhttpClient.build()
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
        MyLogUtils.i(TAG, "onActivityStarted:$activity!!::class.java.simpleName")
    }

    override fun onActivityDestroyed(activity: Activity) {
        MyLogUtils.i(TAG, "onActivityDestroyed:$activity!!::class.java.simpleName")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        MyLogUtils.i(TAG, "onActivitySaveInstanceState:$activity!!::class.java.simpleName")
    }

    override fun onActivityStopped(activity: Activity) {
        MyLogUtils.i(TAG, "onActivityStopped:$activity!!::class.java.simpleName")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        MyLogUtils.i(TAG, "onActivityCreated:$activity!!::class.java.simpleName")
    }

    override fun onActivityResumed(activity: Activity) {
        mActivityRefernce = activity!!::class.java.simpleName
        MyLogUtils.i(TAG, "onActivityResumed:$activity!!::class.java.simpleName")

    }
}