package com.zhangyf.draftbottle.manager.sharedpref

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonPref
import com.zhangyf.draftbottle.MainApplication
import com.zhangyf.draftbottle.util.Constants

/**
 *  user data need to be encrypt / decrypt
 *
 */
object SharedPrefModel : KotprefModel() {
    override val kotprefName: String = Constants.SHARED_PREF_FILE_NAME

    //do not use getter after login , this maybe clear , use MainApplication.nowUserId instead.
    var userId: String by stringPref()
    var password: String by stringPref()

    //use setUserModel to set data
    var userSettingMap: MutableMap<String, UserModel> by gsonPref(hashMapOf())

    fun getUserModel(userId: String = MainApplication.nowUserId): UserModel =
        userSettingMap[userId] ?: UserModel().apply {
            userSettingMap[userId] = this
        }

    fun setUserModel(userId: String = MainApplication.nowUserId, modify: UserModel.() -> Unit) {
        val map = userSettingMap
        val user = map[userId] ?: UserModel()
        user.apply { modify.invoke(this) }
        map[userId] = user
        userSettingMap = map
    }

    fun updateUserModelSet(map: Map<String, UserModel>) {
        userSettingMap = map.toMutableMap()
    }

    fun setDefault() {
        setUserModel {

        }
        userId = ""
        password = ""
    }
}
