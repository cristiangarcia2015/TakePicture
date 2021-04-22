package com.example.library.takePicture

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val showMessageLiveData = MutableLiveData<String>()

    protected fun showMessage(message: String?) = showMessageLiveData.postValue(message)
}