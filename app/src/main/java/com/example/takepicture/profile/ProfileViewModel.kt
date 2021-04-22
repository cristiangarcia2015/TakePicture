package com.example.takepicture.profile

import android.app.Application
import com.example.library.takePicture.TakePictureViewModel
import com.example.takepicture.R

class ProfileViewModel(application: Application) : TakePictureViewModel(application) {
    override fun getAppName(): String = getApplication<Application>().getString(R.string.app_name)
}