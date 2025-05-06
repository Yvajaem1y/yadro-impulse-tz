package com.androidlesson.yadro_tz_2025.presentation.adapters

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.androidlesson.domain.models.ContactData
import com.androidlesson.yadro_tz_2025.R

class ContactAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var groupedContacts: List<Any> = ArrayList()

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNameAndSurname: TextView = itemView.findViewById(R.id.tv_name_and_surname)
        private val tvPhoneNumber: TextView = itemView.findViewById(R.id.tv_phone_number)

        fun bind(contact: ContactData) {
            tvNameAndSurname.text = contact.name
            tvPhoneNumber.text = contact.phoneNumber

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(Intent.ACTION_CALL).apply {
                    data = Uri.parse("tel:${contact.phoneNumber}")
                }

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Нет разрешения на звонки", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHeader: TextView = itemView.findViewById(R.id.tv_header)

        fun bind(header: String) {
            tvHeader.text = header
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_contact_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_contact_preview, parent, false)
                ContactViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContactViewHolder -> {
                val contact = groupedContacts[position] as ContactData
                holder.bind(contact)
            }
            is HeaderViewHolder -> {
                val header = groupedContacts[position] as String
                holder.bind(header)
            }
        }
    }

    override fun getItemCount(): Int = groupedContacts.size

    override fun getItemViewType(position: Int): Int {
        return if (groupedContacts[position] is ContactData) TYPE_ITEM else TYPE_HEADER
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateContacts(newContacts: List<ContactData>) {
        groupedContacts = groupContactsByLetter(newContacts)
        notifyDataSetChanged()
    }

    private fun groupContactsByLetter(contacts: List<ContactData>): List<Any> {
        val grouped = mutableListOf<Any>()
        val sortedContacts = contacts.sortedBy { it.name }

        var currentHeader: String? = null
        for (contact in sortedContacts) {
            val firstChar = contact.name.first().toUpperCase()

            if (firstChar.toString() != currentHeader) {
                currentHeader = firstChar.toString()
                grouped.add(currentHeader ?: "")
            }
            grouped.add(contact)
        }
        return grouped
    }
}