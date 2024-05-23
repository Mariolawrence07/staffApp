package com.example.staffapp

import Lectures
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.staffapp.databinding.ActivityLectureDetailBinding

class LectureDetail() : AppCompatActivity() {
    private lateinit var binding: ActivityLectureDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLectureDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }


// Retrieve the book data from the intent extras using the unique key
        val data = intent.getSerializableExtra("lecture") as Lectures?

// Display the book data in your views
        if (data != null) {
            // Display the book data as needed
            binding.titleTextView.text = data.title
            binding.nameTextView.text = data.lecturer
            binding.detailTextView.text = data.description
            val resourceId =
                this.resources.getIdentifier("@drawable/" +
                        data.coverImage,"drawable", this.packageName)
            binding.imageView.setImageResource(resourceId)
        }
    }
}

