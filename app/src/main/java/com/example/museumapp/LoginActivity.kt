package com.example.museumapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        // 🔥 ЕФЕКТ ДЛЯ ТЕКСТУ (НАТИСКАННЯ)
        tvRegister.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.scaleX = 1.1f
                    v.scaleY = 1.1f
                    v.alpha = 0.7f
                }
                MotionEvent.ACTION_UP -> {
                    v.scaleX = 1f
                    v.scaleY = 1f
                    v.alpha = 1f

                    // 👉 КЛІК
                    startActivity(Intent(this, RegisterActivity::class.java))
                    finish()
                }
                MotionEvent.ACTION_CANCEL -> {
                    v.scaleX = 1f
                    v.scaleY = 1f
                    v.alpha = 1f
                }
            }
            true
        }

        // 🔥 ЕФЕКТ КНОПКИ
        btnLogin.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.scaleX = 1.05f
                    v.scaleY = 1.05f
                }
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    v.scaleX = 1f
                    v.scaleY = 1f
                }
            }
            false
        }

        // 🔥 ЛОГІН
        btnLogin.setOnClickListener {

            val login = etLogin.text.toString()
            val password = etPassword.text.toString()

            val savedLogin = sharedPref.getString("login", "")
            val savedPassword = sharedPref.getString("password", "")

            if (login == savedLogin && password == savedPassword) {

                sharedPref.edit().putBoolean("isAuthorized", true).apply()

                Toast.makeText(this, "Успішний вхід!", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}