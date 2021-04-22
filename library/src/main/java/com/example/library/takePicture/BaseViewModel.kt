package com.example.library.takePicture

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

open class BaseViewModel : ViewModel() {
    protected var contextRef: WeakReference<Context>? = null
    private val showMessageLiveData = MutableLiveData<String>()

    fun getShowMessageLiveData() = showMessageLiveData

    fun showMessage(message: String?) = showMessageLiveData.postValue(message)

    open fun init(context: Context) {
        contextRef = WeakReference(context)
    }
}