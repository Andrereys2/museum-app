package com.example.museumapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ExhibitsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var sharedPref: android.content.SharedPreferences
    private var list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibits)

        listView = findViewById(R.id.listView)
        sharedPref = getSharedPreferences("Exhibits", MODE_PRIVATE)

        // 🔥 НИЖНЄ МЕНЮ
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_main -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.nav_home -> true

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

        loadData()
        setupList()
    }

    private fun loadData() {
        val data = sharedPref.getString("list", "") ?: ""

        list = if (data.isEmpty()) mutableListOf()
        else data.split(";;").toMutableList()

        list.sortBy {
            it.split("|")[0].lowercase()
        }
    }

    private fun saveData() {
        val updated = list.joinToString(";;")
        sharedPref.edit().putString("list", updated).apply()
    }

    private fun setupList() {

        val adapter = object : BaseAdapter() {

            override fun getCount(): Int = list.size
            override fun getItem(position: Int): Any = list[position]
            override fun getItemId(position: Int): Long = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

                val view = layoutInflater.inflate(R.layout.item_exhibit, parent, false)

                val parts = list[position].split("|")

                val name = view.findViewById<TextView>(R.id.tvName)
                val author = view.findViewById<TextView>(R.id.tvAuthor)
                val type = view.findViewById<TextView>(R.id.tvType)
                val number = view.findViewById<TextView>(R.id.tvNumber)

                if (parts.size >= 4) {
                    name.text = parts[0]
                    author.text = "Автор: ${parts[1]}"
                    type.text = "Тип: ${parts[2]}"
                    number.text = "№: ${parts[3]}"
                }

                return view
            }
        }

        listView.adapter = adapter

        // 🔥 РЕДАГУВАННЯ
        listView.setOnItemClickListener { _, _, position, _ ->

            val parts = list[position].split("|").toMutableList()

            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.setPadding(20, 20, 20, 20)

            val etName = EditText(this)
            etName.setText(parts[0])

            val etAuthor = EditText(this)
            etAuthor.setText(parts[1])

            val etType = EditText(this)
            etType.setText(parts[2])

            val etNumber = EditText(this)
            etNumber.setText(parts[3])

            layout.addView(etName)
            layout.addView(etAuthor)
            layout.addView(etType)
            layout.addView(etNumber)

            AlertDialog.Builder(this)
                .setTitle("Редагувати експонат")
                .setView(layout)
                .setPositiveButton("Зберегти") { _, _ ->

                    val updated = "${etName.text}|${etAuthor.text}|${etType.text}|${etNumber.text}"
                    list[position] = updated

                    saveData()
                    loadData()
                    setupList()
                }
                .setNegativeButton("Скасувати", null)
                .show()
        }

        // 🔥 ВИДАЛЕННЯ
        listView.setOnItemLongClickListener { _, _, position, _ ->

            AlertDialog.Builder(this)
                .setTitle("Видалити")
                .setMessage("Видалити цей експонат?")
                .setPositiveButton("Так") { _, _ ->

                    list.removeAt(position)
                    saveData()
                    loadData()
                    setupList()

                    Toast.makeText(this, "Видалено", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Ні", null)
                .show()

            true
        }
    }
}