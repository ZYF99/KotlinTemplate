package com.zhangyf.draftbottle.ui.fragment.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zhangyf.draftbottle.R
import com.zhangyf.draftbottle.ui.base.BaseViewModel

class SplashViewModel(application: Application) : BaseViewModel(application) {

    val time = MutableLiveData("")

/*        .onErrorResumeNext { error ->
            return@onErrorResumeNext if (error is ApiException
                && apiClient.hasN21MAGIC()) getOnlineSignUpUserInfoSingle()
            else Single.error(error)
        }*/

/*    private fun getOnlineSignUpUserInfoSingle(): Single<LoginModel> {
        return ctrService.onlineSignUpUserInfo(
            userId.value!!
        ).flatMap {
            getLoginSingle(
                it.common!!.userId!!,
                password.value!!,
                chargeCode.value!!
            )
        }
    }*/


}

