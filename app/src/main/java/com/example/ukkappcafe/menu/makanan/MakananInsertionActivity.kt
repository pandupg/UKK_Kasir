package com.example.ukkappcafe.menu.makanan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ukkappcafe.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MakananInsertionActivity : AppCompatActivity() {
    private lateinit var etMakananName: EditText
    private lateinit var etMakananDeskripsi: EditText
    private lateinit var etMakananHarga: EditText
    private lateinit var btnSaveDataMakanan: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan_insertion)

        etMakananName = findViewById(R.id.etMakananName)
        etMakananDeskripsi = findViewById(R.id.etMakananDeskripsi)
        etMakananHarga = findViewById(R.id.etMakananHarga)
        btnSaveDataMakanan = findViewById(R.id.btnSaveMakanan)

        dbRef = FirebaseDatabase.getInstance().getReference("Makanan")

        btnSaveDataMakanan.setOnClickListener {
            saveMakananData()
        }
    }

    private fun saveMakananData() {
        val makananName = etMakananName.text.toString()
        val makananDeskripsi = etMakananDeskripsi.text.toString()
        val makananHarga = etMakananHarga.text.toString()

        if (makananName.isEmpty()){
            etMakananName.error = "Masukkan nama"
        }
        if (makananDeskripsi.isEmpty()){
            etMakananDeskripsi.error = "Masukkan deskripsi"
        }
        if (makananHarga.isEmpty()){
            etMakananHarga.error = "Masukkan harga"
        }

        val makananId = dbRef.push().key!!

        val makanan = MakananModel(makananId, makananName, makananDeskripsi, makananHarga)

        dbRef.child(makananId).setValue(makanan)
            .addOnCompleteListener {
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

             etMakananName.text.clear()
             etMakananDeskripsi.text.clear()
             etMakananHarga.text.clear()
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }
}