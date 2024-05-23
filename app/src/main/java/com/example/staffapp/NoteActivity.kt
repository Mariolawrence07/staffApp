package com.example.staffapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.data.NoteDatabase
import com.example.staffapp.R.menu.main_menu
import com.example.staffapp.adapter.NoteAdapter
import com.example.staffapp.database.MySharedPreferences
import com.example.staffapp.databinding.ActivityNoteBinding
import com.example.staffapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var mySharedPreferences: MySharedPreferences
    private var itemList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        binding.root
        setContentView(binding.root)

        mySharedPreferences = MySharedPreferences(applicationContext)

        // Retrieve the saved string list
        val retrievedList = mySharedPreferences.getStringList("myKey")
        itemList = retrievedList as MutableList<String>

        // Set up RecyclerView and NoteAdapter
        noteAdapter = NoteAdapter(retrievedList)
        binding.noteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.noteRecyclerView.adapter = noteAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addNote->{
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }



}