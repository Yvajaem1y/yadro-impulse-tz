package com.androidlesson.yadro_tz_2025.di

import android.app.Application
import android.content.Context
import com.androidlesson.domain.useCase.GetContactsUseCase
import com.androidlesson.yadro_tz_2025.presentation.viewModels.MainActivityViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    fun provideMainActivityViewModelFactory(getContactsUseCase: GetContactsUseCase): MainActivityViewModelFactory{
        return MainActivityViewModelFactory(getContactsUseCase)
    }
}