package com.example.museumapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)

        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        val name = sharedPref.getString("name", "") ?: ""
        val email = sharedPref.getString("email", "") ?: ""

        val etName = EditText(this)
        etName.setText(name)
        etName.hint = "Ім'я"

        val etEmail = EditText(this)
        etEmail.setText(email)
        etEmail.hint = "Email"

        val btnSave = Button(this)
        btnSave.text = "Зберегти"

        btnSave.setOnClickListener {
            val newName = etName.text.toString()
            val newEmail = etEmail.text.toString()

            if (newName.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this, "Заповніть всі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sharedPref.edit()
                .putString("name", newName)
                .putString("email", newEmail)
                .apply()

            Toast.makeText(this, "Збережено!", Toast.LENGTH_SHORT).show()
        }

        val btnLogout = Button(this)
        btnLogout.text = "Вийти"

        btnLogout.setOnClickListener {
            sharedPref.edit().putBoolean("isAuthorized", false).apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        layout.addView(etName)
        layout.addView(etEmail)
        layout.addView(btnSave)
        layout.addView(btnLogout)

        setContentView(layout)
    }
}