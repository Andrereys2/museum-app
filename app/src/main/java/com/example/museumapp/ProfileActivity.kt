package com.example.museumapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private val PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val container = findViewById<LinearLayout>(R.id.container)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val imgProfile = findViewById<ImageView>(R.id.imgProfile)

        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        val nickname = sharedPref.getString("nickname", "User") ?: "User"
        val name = sharedPref.getString("name", "") ?: ""
        val email = sharedPref.getString("email", "") ?: ""
        val birth = sharedPref.getString("birth", "") ?: ""
        val savedImage = sharedPref.getString("image", "")

        //ФОТО
        if (!savedImage.isNullOrEmpty()) {
            imgProfile.setImageURI(Uri.parse(savedImage))
        } else {
            imgProfile.setImageResource(android.R.drawable.ic_menu_camera)
        }

        imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        //НІК
        val tvNick = TextView(this)
        tvNick.text = nickname
        tvNick.textSize = 20f
        tvNick.gravity = Gravity.CENTER
        tvNick.setPadding(0, 10, 0, 20)

        val tvNameLabel = TextView(this)
        tvNameLabel.text = "Ім'я"

        val etName = EditText(this)
        etName.setText(name)

        val tvEmailLabel = TextView(this)
        tvEmailLabel.text = "Email"

        val etEmail = EditText(this)
        etEmail.setText(email)

        val btnBirth = Button(this)
        btnBirth.text = if (birth.isEmpty()) "Дата народження" else birth

        btnBirth.setOnClickListener {
            val cal = Calendar.getInstance()

            DatePickerDialog(
                this,
                { _, y, m, d ->
                    val date = "$d/${m + 1}/$y"
                    btnBirth.text = date
                    sharedPref.edit().putString("birth", date).apply()
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val btnSave = Button(this)
        btnSave.text = "Зберегти"

        btnSave.setOnClickListener {
            val oldImage = sharedPref.getString("image", "")

            sharedPref.edit()
                .putString("name", etName.text.toString())
                .putString("email", etEmail.text.toString())
                .putString("image", imageUri?.toString() ?: oldImage)
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

        container.removeAllViews()

        container.addView(imgProfile)

        container.addView(tvNick)

        container.addView(tvNameLabel)
        container.addView(etName)

        container.addView(tvEmailLabel)
        container.addView(etEmail)

        container.addView(btnBirth)
        container.addView(btnSave)
        container.addView(btnLogout)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_main -> {
                    startActivity(Intent(this, MainActivity::class.java))
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
                R.id.nav_profile -> true
                else -> false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data?.data
            findViewById<ImageView>(R.id.imgProfile).setImageURI(imageUri)
        }
    }
}