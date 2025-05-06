package com.androidlesson.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import com.androidlesson.domain.models.ContactData
import com.androidlesson.domain.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ContactsRepositoryImpl(private val context: Context) : ContactsRepository{

    @SuppressLint("Recycle")
    override suspend fun getContacts(): List<ContactData> {
        val contacts = mutableListOf<ContactData>()
        val contentResolver = context.contentResolver
        val cursor = withContext(Dispatchers.IO) {
            contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null
            )
        }
        cursor?.use{
            val idIndex: Int = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex: Int= it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex: Int= it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val id = it.getLong(idIndex)
                val name = it.getString(nameIndex) ?: "Без имени"
                val number = it.getString(numberIndex) ?: ""

                contacts.add(ContactData(id, name, number))
            }
        }

        return contacts
    }
}