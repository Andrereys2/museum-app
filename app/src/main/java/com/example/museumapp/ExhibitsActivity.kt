package com.example.museumapp

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ExhibitsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var sharedPref: android.content.SharedPreferences
    private var list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exhibits)

        listView = findViewById(R.id.listView)
        sharedPref = getSharedPreferences("Exhibits", MODE_PRIVATE)

        loadData()
        setupList()
    }

    private fun loadData() {
        val data = sharedPref.getString("list", "") ?: ""
        list = if (data.isEmpty()) mutableListOf() else data.split(";;").toMutableList()
    }

    private fun saveData() {
        val updated = list.joinToString(";;")
        sharedPref.edit().putString("list", updated).apply()
    }

    private fun setupList() {

        listView.adapter = object : BaseAdapter() {

            override fun getCount(): Int = list.size

            override fun getItem(position: Int): Any = list[position]

            override fun getItemId(position: Int): Long = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

                val view = convertView ?: LayoutInflater.from(this@ExhibitsActivity)
                    .inflate(R.layout.item_exhibit, parent, false)

                val parts = list[position].split("|")

                val img = view.findViewById<ImageView>(R.id.img)
                val name = view.findViewById<TextView>(R.id.tvName)
                val author = view.findViewById<TextView>(R.id.tvAuthor)
                val type = view.findViewById<TextView>(R.id.tvType)
                val number = view.findViewById<TextView>(R.id.tvNumber)

                if (parts.size >= 5) {
                    name.text = parts[0]
                    author.text = "Автор: ${parts[1]}"
                    type.text = "Тип: ${parts[2]}"
                    number.text = "№: ${parts[3]}"

                    try {
                        img.setImageURI(Uri.parse(parts[4]))
                    } catch (e: Exception) {
                        img.setImageResource(android.R.drawable.ic_menu_gallery)
                    }
                }

                return view
            }
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