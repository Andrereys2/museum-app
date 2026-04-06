package com.example.museumapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val isAuthorized = sharedPref.getBoolean("isAuthorized", false)

        Handler(Looper.getMainLooper()).postDelayed({

            if (isAuthorized) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            finish()

        }, 1500) // 1.5 секунди
    }
}