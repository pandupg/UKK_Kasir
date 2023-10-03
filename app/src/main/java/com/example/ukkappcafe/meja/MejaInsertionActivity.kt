package com.example.ukkappcafe.meja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ukkappcafe.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MejaInsertionActivity : AppCompatActivity() {
    private lateinit var etMejaNomor: EditText
    private lateinit var btnSaveDataMeja: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meja_insertion)

        etMejaNomor = findViewById(R.id.etMejaNomor)
        btnSaveDataMeja = findViewById(R.id.btnSaveMeja)

        dbRef = FirebaseDatabase.getInstance().getReference("Meja")

        btnSaveDataMeja.setOnClickListener {
            saveMejaData()
        }
    }

    private fun saveMejaData() {
        val nomorMeja = etMejaNomor.text.toString()

        if(nomorMeja.isEmpty()){
            etMejaNomor.error = "Please enter name"
        }

        val mejaId = dbRef.push().key!!

        val meja = MejaModel(mejaId, nomorMeja)

        dbRef.child(mejaId).setValue(meja)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etMejaNomor.text.clear()
        }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}