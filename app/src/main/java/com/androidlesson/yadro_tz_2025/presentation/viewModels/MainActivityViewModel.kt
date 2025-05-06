package com.androidlesson.yadro_tz_2025.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidlesson.domain.models.ContactData
import com.androidlesson.domain.useCase.GetContactsUseCase
import kotlinx.coroutines.launch

class MainActivityViewModel(private val getContactsUseCase: GetContactsUseCase) :ViewModel(){
    private val contactsMutableLiveData: MutableLiveData<List<ContactData>> = MutableLiveData(ArrayList())

    init {
        getContacts()
    }


    private fun getContacts(){
        viewModelScope.launch {
            contactsMutableLiveData.postValue(getContactsUseCase.execute())
        }
    }

    fun getContactsLiveData() : LiveData<List<ContactData>>{
        return contactsMutableLiveData
    }
}