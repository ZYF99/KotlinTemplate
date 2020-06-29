package com.zhangyf.draftbottle.model.api

import com.squareup.moshi.JsonClass

//数据类示例
@JsonClass(generateAdapter = true)
data class Student(
    val time: Long,
    val title: String
)