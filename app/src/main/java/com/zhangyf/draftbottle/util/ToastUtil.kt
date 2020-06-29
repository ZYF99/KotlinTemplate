package com.zhangyf.draftbottle.util

import android.widget.Toast
import com.zhangyf.draftbottle.MainApplication
import io.reactivex.android.schedulers.AndroidSchedulers

fun showToast(message: String) {
    AndroidSchedulers.mainThread().scheduleDirect {
        Toast.makeText(MainApplication.instance, message, Toast.LENGTH_SHORT).show()
    }

}