package com.example.museumapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPref: android.content.SharedPreferences
    private lateinit var frame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frame = findViewById(R.id.frameLayout)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        sharedPref = getSharedPreferences("Exhibits", Context.MODE_PRIVATE)

        supportActionBar?.title = "Головна"

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_main -> {
                    supportActionBar?.title = "Головна"
                    showMain()
                    true
                }

                R.id.nav_home -> {
                    startActivity(Intent(this, ExhibitsActivity::class.java))
                    true
                }

                R.id.nav_add -> {
                    startActivity(Intent(this, AddExhibitActivity::class.java))
                    true
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                else -> false
            }
        }

        showMain()
    }

    // ГОЛОВНА
    private fun showMain() {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)

        val title = TextView(this)
        title.text = "Museum App 👋"
        title.textSize = 24f

        val list = sharedPref.getString("list", "") ?: ""
        val count = if (list.isEmpty()) 0 else list.split(";;").size

        val countText = TextView(this)
        countText.text = "Кількість експонатів: $count"
        countText.textSize = 18f

        val btnExhibits = Button(this)
        btnExhibits.text = "Перейти до експонатів"
        btnExhibits.setOnClickListener {
            startActivity(Intent(this, ExhibitsActivity::class.java))
        }

        val btnAdd = Button(this)
        btnAdd.text = "Додати експонат"
        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddExhibitActivity::class.java))
        }

        layout.addView(title)
        layout.addView(countText)
        layout.addView(btnExhibits)
        layout.addView(btnAdd)

        frame.removeAllViews()
        frame.addView(layout)
    }
}