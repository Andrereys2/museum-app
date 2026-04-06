package com.example.museumapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)

        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        val name = sharedPref.getString("name", "")
        val email = sharedPref.getString("email", "")

        val tv = TextView(this)
        tv.text = "Ім'я: $name\nEmail: $email"
        tv.textSize = 18f

        val btnLogout = MaterialButton(this)
        btnLogout.text = "Вийти"

        btnLogout.setOnClickListener {
            sharedPref.edit().putBoolean("isAuthorized", false).apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        layout.addView(tv)
        layout.addView(btnLogout)

        setContentView(layout)
    }
}