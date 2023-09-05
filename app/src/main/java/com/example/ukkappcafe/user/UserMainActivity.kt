package com.example.ukkappcafe.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ukkappcafe.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserMainActivity : AppCompatActivity() {

    private lateinit var btnInsertDataUser: Button
    private lateinit var btnFetchDataUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertDataUser = findViewById(R.id.btnInsertDataUser)
        btnFetchDataUser = findViewById(R.id.btnFetchDataUser)

        btnInsertDataUser.setOnClickListener {
            val intent = Intent(this, UserInsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchDataUser.setOnClickListener {
            val intent = Intent(this, UserFetchingActivity::class.java)
            startActivity(intent)
        }
    }
}