package com.example.museumapp

import android.app.AlertDialog
import android.content.Context
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
                    supportActionBar?.title = "Експонати"
                    showExhibits()
                    true
                }

                R.id.nav_add -> {
                    supportActionBar?.title = "Додати"
                    startActivity(android.content.Intent(this, AddExhibitActivity::class.java))
                    true
                }

                R.id.nav_profile -> {
                    supportActionBar?.title = "Профіль"
                    showProfile()
                    true
                }

                else -> false
            }
        }

        showMain()
    }

    private fun getList(): MutableList<String> {
        val saved = sharedPref.getString("list", "") ?: ""
        return if (saved.isEmpty()) mutableListOf()
        else saved.split("|").toMutableList()
    }

    private fun saveList(list: List<String>) {
        val joined = list.joinToString("|")
        sharedPref.edit().putString("list", joined).apply()
    }

    private fun showMain() {
        val text = TextView(this)
        text.text = "Ласкаво просимо в Museum App 👋"
        text.textSize = 20f

        frame.removeAllViews()
        frame.addView(text)
    }

    private fun showExhibits() {
        val listView = ListView(this)
        val list = getList()

        if (list.isEmpty()) list.add("Немає експонатів")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->

            if (list[position] == "Немає експонатів") return@setOnItemClickListener

            val input = EditText(this)
            input.setText(list[position])

            AlertDialog.Builder(this)
                .setTitle("Редагувати")
                .setView(input)
                .setPositiveButton("Зберегти") { _, _ ->
                    list[position] = input.text.toString()
                    saveList(list)
                    showExhibits()
                }
                .setNegativeButton("Скасувати", null)
                .show()
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->

            if (list[position] == "Немає експонатів") return@setOnItemLongClickListener true

            AlertDialog.Builder(this)
                .setTitle("Видалити?")
                .setMessage(list[position])
                .setPositiveButton("Так") { _, _ ->
                    list.removeAt(position)
                    saveList(list)
                    showExhibits()
                }
                .setNegativeButton("Ні", null)
                .show()

            true
        }

        frame.removeAllViews()
        frame.addView(listView)
    }

    private fun showProfile() {
        val text = TextView(this)
        text.text = "Профіль користувача"
        text.textSize = 20f

        frame.removeAllViews()
        frame.addView(text)
    }
}