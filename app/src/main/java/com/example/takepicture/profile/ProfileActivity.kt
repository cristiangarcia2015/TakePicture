package com.example.takepicture.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.library.utils.showMessage
import com.example.takepicture.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java].apply {
            bitmapLiveData.observe(this@ProfileActivity, { ivProfile.setImageBitmap(it) })
            showMessageLiveData.observe(this@ProfileActivity, { showMessage(it) })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    fun onClickTakePicture(v: View) = viewModel.takePicture(this)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) =
        viewModel.handlePermissionResult(this, requestCode, grantResults)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.handleActivityResult(requestCode, resultCode, data)
    }
}