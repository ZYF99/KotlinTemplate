package com.zhangyf.draftbottle

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson
import com.jakewharton.threetenabp.AndroidThreeTen
import com.zhangyf.draftbottle.manager.di.apiModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

open class MainApplication : Application(), KodeinAware {


    companion object {
        var instance: MainApplication? = null
        var nowUserId: String = ""
    }

    override val kodein by Kodein.lazy {
        import(androidXModule(this@MainApplication))
        import(apiModule)
        /* bindings */
    }

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        Kotpref.gson = Gson()
        AndroidThreeTen.init(this)
        if (instance == null) instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}