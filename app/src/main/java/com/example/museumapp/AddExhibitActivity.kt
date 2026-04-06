package com.example.museumapp

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class AddExhibitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exhibit)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val etExhibit = findViewById<EditText>(R.id.etExhibit)
        val btnSave = findViewById<MaterialButton>(R.id.btnSave)

        val sharedPref = getSharedPreferences("Exhibits", Context.MODE_PRIVATE)

        // (можеш не юзати toolbar якщо не хочеш)
        toolbar.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {

            val newExhibit = etExhibit.text.toString()

            if (newExhibit.isEmpty()) {
                Toast.makeText(this, "Введіть назву", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val old = sharedPref.getString("list", "") ?: ""
            val updated = if (old.isEmpty()) newExhibit else "$old|$newExhibit"

            sharedPref.edit().putString("list", updated).apply()

            Toast.makeText(this, "Збережено!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}