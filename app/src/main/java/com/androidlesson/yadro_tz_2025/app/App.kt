package com.androidlesson.yadro_tz_2025.app

import android.app.Application
import com.androidlesson.yadro_tz_2025.di.AppComponent
import com.androidlesson.yadro_tz_2025.di.AppModule
import com.androidlesson.yadro_tz_2025.di.DaggerAppComponent

class App : Application(){
    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            ?.appModule(AppModule(this))
            ?.build()
    }
}