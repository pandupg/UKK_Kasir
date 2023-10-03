package com.example.ukkappcafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ukkappcafe.meja.MejaActivity
import com.example.ukkappcafe.menu.MenuActivity
import com.example.ukkappcafe.menu.makanan.MakananActivity
import com.example.ukkappcafe.user.UserMainActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btnUser: Button
    private lateinit var btnMeja: Button
    private lateinit var btnMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnUser = findViewById(R.id.btn_keUser)
        btnMenu = findViewById(R.id.btn_keMenu)
        btnMeja = findViewById(R.id.btn_keMeja)

        btnUser.setOnClickListener {
            val move = Intent(this, UserMainActivity::class.java)
            startActivity(move)
        }

        btnMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        btnMeja.setOnClickListener {
            val intent = Intent(this, MejaActivity::class.java)
            startActivity(intent)
        }
    }
}