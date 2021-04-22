package com.example.library.takePicture

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.library.R
import com.example.library.utils.REQUEST_CODE_CHOOSE_PICTURE
import com.example.library.utils.REQUEST_CODE_EXTERNAL_STORAGE
import com.example.library.utils.launchTakePictureIntent

abstract class TakePictureViewModel(application: Application) : BaseViewModel(application) {
    protected var imageUri: Uri? = null
    protected var bitmap: Bitmap? = null

    val bitmapLiveData: MutableLiveData<Bitmap> = MutableLiveData()
    private val storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    protected abstract fun getAppName(): String

    private fun isStoragePermissionGranted(activity: Activity) =
        ActivityCompat.checkSelfPermission(
            activity,
            storagePermission
        ) == PackageManager.PERMISSION_GRANTED

    fun takePicture(activity: Activity) {
        if (isStoragePermissionGranted(activity))
            imageUri = launchTakePictureIntent(activity, getAppName(), REQUEST_CODE_CHOOSE_PICTURE)
        else
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(storagePermission),
                REQUEST_CODE_EXTERNAL_STORAGE
            )
    }

    fun takePicture(fragment: Fragment) {
        if (isStoragePermissionGranted(fragment.activity!!))
            imageUri = launchTakePictureIntent(fragment, getAppName(), REQUEST_CODE_CHOOSE_PICTURE)
        else
            fragment.requestPermissions(arrayOf(storagePermission), REQUEST_CODE_EXTERNAL_STORAGE)
    }

    fun handlePermissionResult(context: Activity, requestCode: Int, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_EXTERNAL_STORAGE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            imageUri = launchTakePictureIntent(context, getAppName(), REQUEST_CODE_CHOOSE_PICTURE)
        else
            showMessage(context.getString(R.string.missing_permissions))
    }

    fun handlePermissionResult(fragment: Fragment, requestCode: Int, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_EXTERNAL_STORAGE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            imageUri = launchTakePictureIntent(fragment, getAppName(), REQUEST_CODE_CHOOSE_PICTURE)
        else
            showMessage(fragment.getString(R.string.missing_permissions))
    }

    open fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PICTURE) {
            try {
                if (data?.data != null) imageUri = Uri.parse(data.dataString)
                Glide.with(getApplication() as Context).asBitmap().load(imageUri)
                    .apply(RequestOptions.overrideOf(400).centerCrop()).into(
                        object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                bitmap = resource
                                bitmapLiveData.postValue(bitmap)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {}
                        }
                    )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}