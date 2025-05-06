package com.androidlesson.yadro_tz_2025.di

import com.androidlesson.yadro_tz_2025.presentation.activities.MainActivity
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

    fun injectMainActivity(mainActivity: MainActivity)
}