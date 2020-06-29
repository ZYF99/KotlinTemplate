package com.zhangyf.draftbottle.ui.activity

import android.app.Application
import com.zhangyf.draftbottle.ui.base.BaseViewModel
import com.zhangyf.draftbottle.ui.base.LiveDataEmptyEvent
import com.zhangyf.draftbottle.ui.base.LiveDataEvent

class MainViewModel(application: Application) : BaseViewModel(application) {

    val isLogoutSuccess = LiveDataEmptyEvent()
    val logoutFailed = LiveDataEvent<Throwable>()

    fun tryLogout() {
/*        cmn0Service.logout()
            .switchThread()
            .onErrorResumeNext {
                if (it is ApiException || it is HttpException) {
                    logoutFailed.setEventValue(it)
                    return@onErrorResumeNext Completable.error(SkipCatchApiException())
                }
                return@onErrorResumeNext Completable.error(it)
            }
            .catchApiError()
            .doOnComplete { logout() }
            .bindLife()*/
    }

    fun logout() {
        isLogoutSuccess.sendEvent()
    }



}