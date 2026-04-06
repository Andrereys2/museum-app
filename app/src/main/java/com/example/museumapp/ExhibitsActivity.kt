package com.example.museumapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ExhibitsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listView = ListView(this)

        val exhibits = listOf(
            "Картина Мона Ліза",
            "Старовинний меч",
            "Єгипетська мумія"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, exhibits)
        listView.adapter = adapter

        setContentView(listView)
    }
}