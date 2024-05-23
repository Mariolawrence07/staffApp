package com.example.staffapp.adapter

import Lectures
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.staffapp.LectureActivity
import com.example.staffapp.LectureDetail
import com.example.staffapp.R

class LecturesAdapter(private val context: LectureActivity,
                      private val books: ArrayList<Lectures>) :
    RecyclerView.Adapter<LecturesAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val lecturecover: ImageView = view.findViewById(R.id.imgFact)
        val lecturetitle: TextView = view.findViewById(R.id.txtFact)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lecture_layout, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theBook = books[position]
        holder.lecturetitle.text = theBook.title
        val resourceId =
            context.resources.getIdentifier("@drawable/" +
                    theBook.coverImage,"drawable", context.packageName)
        holder.lecturecover.setImageResource(resourceId)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, LectureDetail::class.java)
            intent.putExtra("lecture", theBook)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return books.size
    }

}