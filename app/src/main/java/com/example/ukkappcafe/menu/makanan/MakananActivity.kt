package com.example.ukkappcafe.menu.makanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ukkappcafe.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MakananActivity : AppCompatActivity() {
    private lateinit var btnInsertDataMakanan: Button
    private lateinit var btnFetchDataMakanan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertDataMakanan = findViewById(R.id.btnInsertDataMakanan)
        btnFetchDataMakanan = findViewById(R.id.btnFetchDataMakanan)

        btnInsertDataMakanan.setOnClickListener {
            val intent = Intent(this, MakananInsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchDataMakanan.setOnClickListener {
            val intent = Intent(this, MakananFetchingActivity::class.java)
            startActivity(intent)
        }
    }
}