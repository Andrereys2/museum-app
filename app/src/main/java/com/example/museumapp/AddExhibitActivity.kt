package com.example.museumapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddExhibitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exhibit)

        val etName = findViewById<EditText>(R.id.etName)
        val etAuthor = findViewById<EditText>(R.id.etAuthor)
        val etType = findViewById<EditText>(R.id.etType)
        val etNumber = findViewById<EditText>(R.id.etNumber)

        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {

            val name = etName.text.toString().trim()
            val author = etAuthor.text.toString().trim()
            val type = etType.text.toString().trim()
            val number = etNumber.text.toString().trim()

            if (name.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "Заповніть обов'язкові поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔥 БЕЗ ФОТО
            val data = "$name|$author|$type|$number"

            val sharedPref = getSharedPreferences("Exhibits", MODE_PRIVATE)
            val old = sharedPref.getString("list", "") ?: ""

            val updated = if (old.isEmpty()) {
                data
            } else {
                "$old;;$data"
            }

            sharedPref.edit().putString("list", updated).apply()

            Toast.makeText(this, "Експонат додано!", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}