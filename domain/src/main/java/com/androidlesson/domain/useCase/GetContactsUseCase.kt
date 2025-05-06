package com.androidlesson.domain.useCase

import com.androidlesson.domain.models.ContactData
import com.androidlesson.domain.repository.ContactsRepository

class GetContactsUseCase(private val contactsRepository: ContactsRepository) {
    suspend fun execute(): List<ContactData> {
        val originalContactsList = contactsRepository.getContacts()

        val numbersSet = mutableSetOf<String>()

        return originalContactsList
            .mapNotNull { contact ->
                val cleaned = cleanPhoneNumber(contact.phoneNumber)

                if (cleaned.length == 11 && cleaned.startsWith("8")) {
                    val normalized = cleaned.replaceFirst("8", "7")
                    if (numbersSet.add(normalized)) {
                        contact.copy(phoneNumber = formatPhoneNumber(normalized))
                    } else null
                } else if (cleaned.length == 11 && cleaned.startsWith("7")) {
                    if (numbersSet.add(cleaned)) {
                        contact.copy(phoneNumber = formatPhoneNumber(cleaned))
                    } else null
                } else null
            }
            .sortedBy { it.name.firstOrNull()?.uppercaseChar() ?: ' ' }
    }

    private fun cleanPhoneNumber(phone: String): String {
        return phone.filter { it.isDigit() }
    }

    private fun formatPhoneNumber(cleaned: String): String {
        if (cleaned.length != 11) return cleaned

        val normalized = if (cleaned.startsWith("8")) "7" + cleaned.substring(1) else cleaned
        return "+7 (${normalized.substring(1, 4)}) ${normalized.substring(4, 7)}-${normalized.substring(7, 9)}-${normalized.substring(9, 11)}"
    }
}