package com.androidlesson.yadro_tz_2025.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidlesson.domain.useCase.GetContactsUseCase

class MainActivityViewModelFactory(private val getContactsUseCase: GetContactsUseCase) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(getContactsUseCase) as T
    }
}