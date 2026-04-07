package com.example.museumapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddExhibitActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private val PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exhibit)

        val etName = findViewById<EditText>(R.id.etName)
        val etAuthor = findViewById<EditText>(R.id.etAuthor)
        val etType = findViewById<EditText>(R.id.etType)
        val etNumber = findViewById<EditText>(R.id.etNumber)

        val btnPickImage = findViewById<Button>(R.id.btnPickImage)
        val imagePreview = findViewById<ImageView>(R.id.imagePreview)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // 🔥 ВИБІР ФОТО
        btnPickImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        // 🔥 ЗБЕРЕЖЕННЯ
        btnSave.setOnClickListener {

            val name = etName.text.toString()
            val author = etAuthor.text.toString()
            val type = etType.text.toString()
            val number = etNumber.text.toString()

            if (name.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "Заповніть обов'язкові поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = "$name|$author|$type|$number|${imageUri.toString()}"

            val sharedPref = getSharedPreferences("Exhibits", MODE_PRIVATE)
            val old = sharedPref.getString("list", "") ?: ""
            val updated = if (old.isEmpty()) data else "$old;;$data"

            sharedPref.edit().putString("list", updated).apply()

            Toast.makeText(this, "Експонат додано!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // 🔥 ОТРИМАННЯ ФОТО
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            val imagePreview = findViewById<ImageView>(R.id.imagePreview)
            imagePreview.setImageURI(imageUri)
        }
    }
}