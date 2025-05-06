package com.androidlesson.domain.repository

import com.androidlesson.domain.models.ContactData

interface ContactsRepository {
    suspend fun getContacts(): List<ContactData>
}