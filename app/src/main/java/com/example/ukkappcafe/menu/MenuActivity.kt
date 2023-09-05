package com.example.ukkappcafe.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ukkappcafe.R
import com.example.ukkappcafe.menu.makanan.MakananActivity
import com.example.ukkappcafe.menu.minuman.MinumanActivity

class MenuActivity : AppCompatActivity() {
    private lateinit var btnKeMakanan: Button
    private lateinit var btnKeMinuman: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnKeMakanan = findViewById(R.id.btn_keMakanan)
        btnKeMinuman = findViewById(R.id.btn_keMinuman)

        btnKeMakanan.setOnClickListener {
            val intent = Intent(this, MakananActivity::class.java)
            startActivity(intent)
        }

        btnKeMinuman.setOnClickListener {
            val intent = Intent(this, MinumanActivity::class.java)
            startActivity(intent)
        }
    }
}