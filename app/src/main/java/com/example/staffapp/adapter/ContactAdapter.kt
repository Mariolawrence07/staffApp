package com.example.staffapp.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.staffapp.R
import com.example.staffapp.model.Contact

class ContactAdapter(private var contacts: List<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
        var phoneNumber = contact.number
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val packageManager = context.packageManager

            // Create the intent with the ACTION_DIAL action and the phone number
            val intent = Intent(Intent.ACTION_DIAL)

            intent.data = Uri.parse("tel:$phoneNumber")

            // Verify that the intent can be resolved before starting the activity
            if (intent.resolveActivity(packageManager) != null) {
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun updateContacts(filteredContacts: List<Contact>) {
        contacts = filteredContacts
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.contactNameTextView)
        private val numberTextView: TextView = itemView.findViewById(R.id.contactPhoneTextView)

        fun bind(contact: Contact) {
            nameTextView.text = contact.name
            numberTextView.text = contact.number
        }
    }
}
