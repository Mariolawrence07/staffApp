package com.example.staffapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.staffapp.R

class NoteAdapter(private var contacts: List<String>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contacts[position]
        holder.bind(item)
        holder.deleteButton.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val detailTextView: TextView = itemView.findViewById(R.id.detailTextValue)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteIcon)

        fun bind(item: String) {
            detailTextView.text = item
        }
    }


}
