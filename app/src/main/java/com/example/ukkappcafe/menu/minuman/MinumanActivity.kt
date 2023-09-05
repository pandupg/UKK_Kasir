package com.example.ukkappcafe.menu.minuman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ukkappcafe.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MinumanActivity : AppCompatActivity() {
    private lateinit var btnInsertDataMinuman: Button
    private lateinit var btnFetchDataMinuman: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minuman)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertDataMinuman = findViewById(R.id.btnInsertDataMinuman)
        btnFetchDataMinuman = findViewById(R.id.btnFetchDataMinuman)

        btnInsertDataMinuman.setOnClickListener {
            val intent = Intent(this, MinumanInsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchDataMinuman.setOnClickListener {
            val intent = Intent(this, MinumanFetchingActivity::class.java)
            startActivity(intent)
        }
    }
}