package com.hrms.hrmsandroidapp.screens.myprofile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.screens.base.SubBaseActivity
import com.hrms.hrmsandroidapp.util.CommonUtils
import com.hrms.hrmsandroidapp.util.ImageLoader
import com.hrms.hrmsandroidapp.util.MyLogUtils
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.partial_profile_detail.*

class MyProfileActivity : SubBaseActivity() {
    private val TAG = "MyProfileActivity"
    private lateinit var bitmap: Bitmap
    var resultImageString: String = ""
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_my_profile)
        layoutInflater.inflate(R.layout.activity_my_profile, fragment_layout)
        setToolBarTittle(getString(R.string.my_profile))
        mContext=this
        init()
    }

    private fun init() {
       /* profile_photo.setOnClickListener {
            CropImage.activity().start(this)
        }*/
       // updateUI()
    }

    private fun updateUI() {
        profile_photo.setBackgroundResource(R.drawable.ic_person_white)
    }

   /* @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                profile_photo.setImageURI(resultUri)

                bitmap = (profile_photo.drawable as BitmapDrawable).bitmap
                getBase64Image(bitmap).execute()

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }


    inner class getBase64Image(private var bitmap: Bitmap) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg voids: Void): String {
            return CommonUtils.convertToBase64(bitmap)
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            resultImageString = s
            MyLogUtils.d(TAG, "IMAGE URL :$resultImageString")

        }
    }
*/
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
