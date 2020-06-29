package com.zhangyf.draftbottle.ui.fragment.login

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.zhangyf.draftbottle.manager.api.base.ApiClient
import com.zhangyf.draftbottle.manager.sharedpref.SharedPrefModel
import com.zhangyf.draftbottle.ui.base.BaseViewModel
import org.kodein.di.generic.instance

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val apiClient by instance<ApiClient>()
    val videoUrl = MutableLiveData<String>()
    var isBackFromHome: Boolean = false //if is back from home
    val userId = MutableLiveData("")
    val password = MutableLiveData("")
    val isAutoLoginEnabled = MutableLiveData(false)
    val showCenterView = MutableLiveData(true)

    fun init() {

        userId.value = SharedPrefModel.userId
        if (isAutoLoginEnabled.value == true) {
            password.value = SharedPrefModel.password
            if (!isBackFromHome
                && userId.value?.isNotEmpty() == true
                && password.value?.isNotEmpty() == true
            ) {
                //checkAndLogin()
            }
        }
    }

    fun checkAndLogin(){

    }




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

    private fun saveUserData() {
        //SharedPrefModel.autoLogin = isAutoLoginEnabled.value ?: false
        SharedPrefModel.userId = userId.value?:""
        SharedPrefModel.password = password.value?:""
    }

}

