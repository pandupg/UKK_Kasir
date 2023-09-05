package com.example.ukkappcafe.menu.minuman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ukkappcafe.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MinumanInsertionActivity : AppCompatActivity() {
    private lateinit var etMinumanName: EditText
    private lateinit var etMinumanDeskripsi: EditText
    private lateinit var etMinumanHarga: EditText
    private lateinit var btnSaveDataMinuman: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minuman_insertion)

        etMinumanName = findViewById(R.id.etMinumanName)
        etMinumanDeskripsi = findViewById(R.id.etMinumanDeskripsi)
        etMinumanHarga = findViewById(R.id.etMinumanHarga)
        btnSaveDataMinuman = findViewById(R.id.btnSaveMinuman)

        dbRef = FirebaseDatabase.getInstance().getReference("Minuman")

        btnSaveDataMinuman.setOnClickListener {
            saveMinumanData()
        }
    }

    private fun saveMinumanData() {
        val minumanName = etMinumanName.text.toString()
        val minumanDeskripsi = etMinumanDeskripsi.text.toString()
        val minumanHarga = etMinumanHarga.text.toString()

        if (minumanName.isEmpty()){
            etMinumanName.error = "Masukkan nama"
        }
        if (minumanDeskripsi.isEmpty()){
            etMinumanDeskripsi.error = "Masukkan deskripsi"
        }
        if (minumanHarga.isEmpty()){
            etMinumanHarga.error = "Masukkan harga"
        }

        val minumanId = dbRef.push().key!!

        val minuman = MinumanModel(minumanId, minumanName, minumanDeskripsi, minumanHarga)

        dbRef.child(minumanId).setValue(minuman)
            .addOnCompleteListener {
            Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_LONG).show()

            etMinumanName.text.clear()
            etMinumanDeskripsi.text.clear()
            etMinumanHarga.text.clear()
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }
}