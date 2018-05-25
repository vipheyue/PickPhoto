package com.vipheyue.pickphotolib

import android.content.Intent

interface PickPhotoCallBack {
    fun onSuccess(data: Intent?, filePath: String)
    fun onfail()

}
