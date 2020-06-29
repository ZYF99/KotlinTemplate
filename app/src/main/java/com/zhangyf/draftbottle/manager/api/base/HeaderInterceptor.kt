package com.zhangyf.draftbottle.manager.api.base

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
//            .header("Authorization", "Bearer")
            .build()

        return chain.proceed(request)
    }

}