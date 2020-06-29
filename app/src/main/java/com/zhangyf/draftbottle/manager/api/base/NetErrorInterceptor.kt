package com.zhangyf.draftbottle.manager.api.base

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset

class NetErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val responseBody = response.body
        val contentLength = responseBody!!.contentLength()
        val source = responseBody.source()

        try {
            source.request(Long.MAX_VALUE)
        } catch (e: IOException) {
            return response
        }

        val charset = responseBody.contentType()?.charset(Charset.forName("UTF-8"))
            ?: Charset.forName("UTF-8")

/*        if (contentLength != 0L) {
            val responseStr = source.buffer.clone().readString(charset)
            if (!responseStr.startsWith("nikkei.jsuggest.printList")
                && responseBody.contentType()?.subtype != "pdf"
                && responseBody.contentType()?.type?.startsWith("image") != true
            ) {
                var errorModel: CommonModel? = null
                try {
                    errorModel = JSONObject(responseStr)
                        .opt("common")?.toString()
                        .fromJson<CommonModel>()
                } catch (e: Exception) {
                    Timber.e(e)
                } catch (e:JSONException){
                    Timber.e(e)
                }
                if (errorModel?.msgType == "E")
                    throw ApiException(errorModel)
            }
        }*/

        return response
    }
}
