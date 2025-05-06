package com.androidlesson.yadro_tz_2025.di

import android.app.Application
import android.content.Context
import com.androidlesson.data.repository.ContactsRepositoryImpl
import com.androidlesson.domain.repository.ContactsRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideContactsRepository(application: Application) : ContactsRepository{
        return ContactsRepositoryImpl(application)
    }
}