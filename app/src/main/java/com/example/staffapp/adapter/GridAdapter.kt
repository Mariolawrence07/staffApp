package com.example.staffapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.staffapp.LectureActivity
import com.example.staffapp.NoteActivity
import com.example.staffapp.PhoneBookActivity
import com.example.staffapp.R
import com.example.staffapp.WeaatherActivity

class GridAdapter(private val context: Context, private val data: List<Array<String>>) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val item = getItem(position) as Array<String>
        val imageResource = context.resources.getIdentifier(item[0], "drawable", context.packageName)

        viewHolder.imageView.setImageResource(imageResource)
        viewHolder.titleTextView.text = item[1]
        view.setOnClickListener {
            if(viewHolder.titleTextView.text=="Note"){
                val intent = Intent(context, NoteActivity::class.java)
                context.startActivity(intent)
            }
            else if (viewHolder.titleTextView.text=="Phone Book"){
                val intent = Intent(context, PhoneBookActivity::class.java)
                context.startActivity(intent)
            }
            else if (viewHolder.titleTextView.text=="Lectures"){
                val intent = Intent(context, LectureActivity::class.java)
                context.startActivity(intent)
            }
            else{
                val intent = Intent(context, WeaatherActivity::class.java)
                context.startActivity(intent)
            }

        }

        return view
    }

    private class ViewHolder(view: View) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    }
}
