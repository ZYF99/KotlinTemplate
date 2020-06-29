package com.zhangyf.draftbottle.manager.api

import com.zhangyf.draftbottle.model.api.Student
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {

    //请求示例，获取学生列表，以Rx2的Single流形式返回
    @FormUrlEncoded
    @POST("ATCA011.do")
    fun fetchStudents(
        @Field("classId") classId: String?
    ): Single<List<Student>>

}