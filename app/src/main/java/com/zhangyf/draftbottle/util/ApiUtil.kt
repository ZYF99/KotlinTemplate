package com.zhangyf.draftbottle.util

import android.content.Context

object ApiUtil {

/*
    private val apiErrorMsg = mapOf(
        "exception.system" to R.string.service_error_exception_system,
        "cmn.passwd.unable" to R.string.service_error_cmn_passwd_unable,
        "cmn.login.userover.compulsion" to R.string.service_error_cmn_login_userover_compulsion,
        "cmn.automatic.login.unable" to R.string.service_error_cmn_automatic_login_unable,
        "cmn.chargecode.admin.invalidity" to R.string.service_error_cmn_chargecode_admin_invalidity,
        "CRPF_0001" to R.string.service_error_CRPF_0001
    )
*/

    fun parseErrorMsg(context: Context, e: Throwable): Pair<String, String> {
        return Pair("", "")
/*        return when (e) {
            is ApiException -> {
                "" to
                        if (parseApiErrorMsg(e) != null) context.getString(parseApiErrorMsg(e)!!)
                        else e.errorModel.message ?: ""
            }
            is HttpException -> "" to context.getString(
                R.string.error_dialog_msg_http_error,
                e.code()
            )
            is IOException -> context.getString(R.string.api_error_network_error_title) to
                    context.getString(R.string.api_error_network_error_msg)
            else -> "" to context.getString(R.string.error_dialog_msg_error)
        }*/
    }

/*
    private fun parseApiErrorMsg(e: ApiException): Int? {
        return apiErrorMsg[e.errorModel.msgCode]
    }

    fun isLoginUnable(e: Throwable) = e is ApiException &&
            !e.ignoreLoginUnableErrorCheck &&
            (e.isCompulsionError()
                    || e.isAutomaticLoginError()
                    || e.isUnableError())


    private fun <T> T?.getResultPair(api: ATCCommonAPI) = Pair(api, this)*/

}


/*fun Throwable.isUnableError(): Boolean =
    this is ApiException && errorModel.msgCode == "cmn.passwd.unable"*/


