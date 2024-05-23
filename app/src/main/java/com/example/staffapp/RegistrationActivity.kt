package com.example.staffapp

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


private lateinit var auth: FirebaseAuth

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }else{
            println("You are not registered yet")
        }

    }

    fun login(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun signupclick(view: View) {
        val db = Firebase.firestore
        val etFullName = findViewById<EditText>(R.id.et_fullname)
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)

        val fullName = etFullName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (fullName.isEmpty()) {
            etFullName.error = "First Name is required"
            return
        }

        if (phone.isEmpty()) {
            etPhone.error = "Last Name is required"
            return
        }

        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Invalid Email"
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "Password is required"
            return
        }

        if (password.length < 6) {
            etPassword.error = "Password should be at least 6 characters long"
            return
        }

        // If all fields are valid, proceed with registration logic here...
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Store user detail in firestore db
                    Log.d(ContentValues.TAG, "Firebase")
                    val userdata = auth.currentUser

                    // Create a new user with a first and last name
                    val user = hashMapOf(
                        "fullname" to fullName,
                        "phone" to phone,
                        "UID" to userdata?.uid.toString(),
                        "email" to userdata?.email.toString(),
                    )

                    Log.d("user", user.toString())

                    // Add a new document with a generated ID
                    db.collection("users").document(userdata?.uid.toString()).set(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.toString()}")
                            //store data in local storage using

                            val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()

                            val jsonString = Gson().toJson(user)
                            editor.putString("map", jsonString)
                            editor.apply()
                            Toast.makeText(
                                baseContext,
                                "Authentication Successful",
                                Toast.LENGTH_SHORT,
                            ).show()
                            //Navigate to Home screen
                            reload()

                        }
                        .addOnFailureListener { e ->
                            Log.d("Error adding document", e.message.toString())
                            Toast.makeText(
                                baseContext,
                                "Fire base Storage failed",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
//                        updateUI(null)
                }
            }
    }

    private fun reload(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}