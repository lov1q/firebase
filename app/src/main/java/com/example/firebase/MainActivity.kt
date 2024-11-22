package com.example.firebase

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore
        val textViewData: TextView = findViewById(R.id.textview)

        // Create a new user with a first, middle, and last name
        val user = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912
        )

        // Add a new document with a generated ID
        /*db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }*/

        db.collection("Student")
            .get()
            .addOnSuccessListener { result ->
                val dataBuilder = StringBuilder() // Для сборки данных в строку
                for (document in result) {
                    // Добавляем данные в StringBuilder
                    dataBuilder.append("${document.id} => ${document.data}\n")
                }
                // Устанавливаем данные в TextView
                textViewData.text = if (dataBuilder.isNotEmpty()) {
                    dataBuilder.toString()
                } else {
                    "Нет данных в коллекции Students"
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
                textViewData.text = "Ошибка загрузки данных."
            }
    }
}