package com.zhangyf.draftbottle.model.base

import com.squareup.moshi.JsonClass
import java.io.IOException

@JsonClass(generateAdapter = false)
data class ApiException(
    //val errorModel: CommonModel,
    var ignoreLoginUnableErrorCheck: Boolean = false
) : IOException()