package com.example.staffapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.staffapp.adapter.ContactAdapter
import com.example.staffapp.databinding.ActivityPhoneBookBinding
import com.example.staffapp.model.Contact
import android.speech.RecognizerIntent
import android.widget.Toast
import java.util.*

class PhoneBookActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPhoneBookBinding
    private var contactAdapter: ContactAdapter? = null
    private var allContacts: List<Contact> = emptyList()
    private lateinit var startButton: ImageView
    private lateinit var searchEditText: EditText
    private val REQUEST_CODE_SPEECH_INPUT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request permission to read contacts if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
        } else {
            loadContacts()
        }

        // Initialize the RecyclerView and adapter
        contactAdapter = ContactAdapter(allContacts)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = contactAdapter
        startButton = binding.voiceIcon
        searchEditText = binding.searchEditText

        startButton.setOnClickListener {
            // on below line we are calling speech recognizer intent.
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // on below line we are passing language model
            // and model free form in our intent
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            // on below line we are passing our
            // language as a default language.
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            // on below line we are specifying a prompt
            // message as speak to text on below line.
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            // on below line we are specifying a try catch block.
            // in this block we are calling a start activity
            // for result method and passing our result code.
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast
                Toast
                    .makeText(
                        this@PhoneBookActivity, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }



        // Setup search functionality
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterContacts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadContacts() {
        val contentResolver: ContentResolver = contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        if (cursor != null && cursor.moveToFirst()) {
            val nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            if (nameColumnIndex >= 0 && numberColumnIndex >= 0) {
                val contactsList = mutableListOf<Contact>()
                do {
                    val name = cursor.getString(nameColumnIndex)
                    val number = cursor.getString(numberColumnIndex)
                    contactsList.add(Contact(name, number))
                } while (cursor.moveToNext())
                cursor.close()
                allContacts = contactsList
                contactAdapter = ContactAdapter(allContacts)
                binding.recyclerView.adapter = contactAdapter
            }
        }
    }

    private fun filterContacts(query: String) {
        val filteredContacts = allContacts.filter { contact ->
            contact.name.contains(query, ignoreCase = true) ||
                    contact.number.contains(query, ignoreCase = true)
        }
        contactAdapter?.updateContacts(filteredContacts)
    }


    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 1
        private const val PERMISSIONS_REQUEST_READ_CONTACTS = 100

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
                searchEditText.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }

}