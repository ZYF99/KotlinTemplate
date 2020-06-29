package com.zhangyf.draftbottle.manager.di

import com.zhangyf.draftbottle.BuildConfig
import com.zhangyf.draftbottle.manager.api.ApiService
import com.zhangyf.draftbottle.manager.api.base.ApiClient
import com.zhangyf.draftbottle.manager.api.base.HeaderInterceptor
import com.zhangyf.draftbottle.manager.api.base.NetErrorInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.util.concurrent.TimeUnit

val apiModule = Kodein.Module("api") {
    bind<ApiClient>() with singleton { provideApiClient() }
    bind<ApiService>() with singleton { instance<ApiClient>().createService(ApiService::class.java) }
}



fun provideApiClient(): ApiClient {
    val client = ApiClient.Builder()
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    client.okBuilder
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(NetErrorInterceptor())
/*        .addInterceptor(AddCookiesInterceptor())
        .addInterceptor(ReceivedCookiesInterceptor())*/
        .readTimeout(30, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
        }

    return client.build(baseUrl = BuildConfig.BASE_URL)
}