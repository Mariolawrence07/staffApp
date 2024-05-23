package com.example.staffapp.database

import android.content.Context
import com.google.gson.Gson

class MySharedPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveStringList(key: String, stringList: List<String>) {
        val jsonString = gson.toJson(stringList)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    fun getStringList(key: String): List<String> {
        val jsonString = sharedPreferences.getString(key, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, Array<String>::class.java).toList()
        } else {
            emptyList()
        }
    }
}