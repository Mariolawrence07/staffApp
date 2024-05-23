package com.example.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.example.staffapp.R

class DotIndicatorAdapter(private val itemCount: Int) : RecyclerView.Adapter<DotIndicatorAdapter.DotViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_dot_indicator, parent, false)
        return DotViewHolder(view)
    }

    override fun onBindViewHolder(holder: DotViewHolder, position: Int) {
        holder.bind(position == selectedPosition)
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    inner class DotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(isSelected: Boolean) {
            val dotView = itemView.findViewById<View>(R.id.viewDot)
            dotView.isSelected = isSelected

        }
    }
}
