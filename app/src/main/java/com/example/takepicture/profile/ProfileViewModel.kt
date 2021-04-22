package com.example.takepicture.profile

import com.example.library.takePicture.TakePictureViewModel
import com.example.takepicture.R

class ProfileViewModel : TakePictureViewModel() {
    override fun getAppName(): String = contextRef!!.get()!!.getString(R.string.app_name)

}