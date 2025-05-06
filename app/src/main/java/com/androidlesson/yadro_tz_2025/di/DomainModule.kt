package com.androidlesson.yadro_tz_2025.di

import com.androidlesson.domain.repository.ContactsRepository
import com.androidlesson.domain.useCase.GetContactsUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetContactsUseCase(contactsRepository: ContactsRepository) : GetContactsUseCase{
        return GetContactsUseCase(contactsRepository)
    }
}