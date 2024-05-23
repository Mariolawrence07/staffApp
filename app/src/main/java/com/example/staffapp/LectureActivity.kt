package com.example.staffapp

import Lectures
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.staffapp.adapter.LecturesAdapter
import org.json.JSONObject
import java.io.InputStream

class LectureActivity : AppCompatActivity() {
    private lateinit var assetManager: AssetManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecture)

        val recyclerView = findViewById<RecyclerView>(R.id.lecturerecyclerView)

        Log.d("Tag", "chris")

        assetManager = applicationContext.assets
        val books = loadBooksFromJson(assetManager)
        println(books.size)


      // Set up the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        LecturesAdapter(this, books as ArrayList<Lectures>).also { recyclerView.adapter = it }

    }

    private fun loadBooksFromJson(assetManager: AssetManager): List<Lectures> {
        return try {
            val inputStream = assetManager.open("classes.json")
            readBooksFromJson(inputStream)
        } catch (e: Exception) {
            // Handle the exception appropriately (e.g., logging, error message)
            emptyList()
        }
    }

    private fun readBooksFromJson(inputStream: InputStream): List<Lectures> {
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("classes")

        val books = mutableListOf<Lectures>()
        for (i in 0 until jsonArray.length()) {
            val bookObject = jsonArray.getJSONObject(i)
            val book = Lectures(
                lecturer = bookObject.getString("Lecturer"),
                title = bookObject.getString("Title"),
                code = bookObject.getString("code"),
                description = bookObject.getString("Description"),
                coverImage = bookObject.getString("CoverImage"),
                lat = bookObject.getDouble("lat"),
                long = bookObject.getDouble("long"),
            )
            books.add(book)
        }

        return books
    }
}
