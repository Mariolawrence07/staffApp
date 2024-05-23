package com.example.staffapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.staffapp.database.MySharedPreferences
import java.util.Locale
import java.util.Objects

class AddNoteActivity : AppCompatActivity() {
    lateinit var outputValue: TextView
    lateinit var micIV: ImageView
    private lateinit var addButton : Button
    private lateinit var mySharedPreferences: MySharedPreferences

    // on below line we are creating a constant value
    private val REQUEST_CODE_SPEECH_INPUT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        mySharedPreferences = MySharedPreferences(applicationContext)

        // initializing variables of list view with their ids.
        outputValue = findViewById(R.id.idTVOutput)
        micIV = findViewById(R.id.idIVMic)
        addButton = findViewById(R.id.myButton)

        addButton.setOnClickListener {

            // Retrieve the saved list of strings
            val retrievedList = mySharedPreferences.getStringList("myKey")

            //add to list
            val mutableList = retrievedList.toMutableList()
            mutableList.add(outputValue.text.toString())

            // Save a list of strings
            val stringList = listOf("Item 1", "Item 2", "Item 3")
            mySharedPreferences.saveStringList("myKey", mutableList)
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
            finish()

        }

        // on below line we are adding on click
        // listener for mic image view.
        micIV.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast
                Toast
                    .makeText(
                        this@AddNoteActivity, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }

    // on below line we are calling on activity result method.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // in this method we are checking request
        // code with our result code.
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                outputValue.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }
}