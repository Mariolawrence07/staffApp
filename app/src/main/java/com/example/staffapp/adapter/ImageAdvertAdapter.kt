package com.example.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.staffapp.R

class ImageAdvertAdapter(private val imageAdverts: List<String>) :
    RecyclerView.Adapter<ImageAdvertAdapter.ImageAdvertViewHolder>() {

    inner class ImageAdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewAdvert)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdvertViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_advert, parent, false)
        return ImageAdvertViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageAdvertViewHolder, position: Int) {
        val imageUrl = imageAdverts[position]
        Glide.with(holder.itemView)
            .load(imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageAdverts.size
    }
}
