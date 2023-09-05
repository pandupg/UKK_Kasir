package com.example.ukkappcafe.menu.minuman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ukkappcafe.R
import com.google.firebase.database.FirebaseDatabase

class MinumanDetailsActivity : AppCompatActivity() {
    private lateinit var tvMinumanId: TextView
    private lateinit var tvMinumanName: TextView
    private lateinit var tvMinumanDeskripsi: TextView
    private lateinit var tvMinumanHarga: TextView
    private lateinit var btnUpdateMinuman: Button
    private lateinit var btnDeleteMinuman: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minuman_details)

        initView()
        setValuesToViews()

        btnUpdateMinuman.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("minumanId").toString(),
                intent.getStringExtra("minumanName").toString()
            )
        }

        btnDeleteMinuman.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("minumanId").toString()
            )
        }
    }

    private fun initView() {
        tvMinumanId = findViewById(R.id.tvMinumanId)
        tvMinumanName = findViewById(R.id.tvMinumanName)
        tvMinumanDeskripsi = findViewById(R.id.tvMinumanDeskripsi)
        tvMinumanHarga = findViewById(R.id.tvMinumanHarga)

        btnUpdateMinuman = findViewById(R.id.btnUpdateMinuman)
        btnDeleteMinuman = findViewById(R.id.btnDeleteMinuman)
    }

    private fun setValuesToViews() {
        tvMinumanId.text = intent.getStringExtra("minumanId")
        tvMinumanName.text = intent.getStringExtra("minumanName")
        tvMinumanDeskripsi.text = intent.getStringExtra("minumanDeskripsi")
        tvMinumanHarga.text = intent.getStringExtra("minumanHarga")
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Minuman")
        val miTask = dbRef.removeValue()

        miTask.addOnSuccessListener {
            Toast.makeText(this, "Minuman data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MinumanFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        minumanId: String,
        minumanName: String
    ){
        val miDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val miDialogView = inflater.inflate(R.layout.minuman_update_dialog, null)

        miDialog.setView(miDialogView)

        val etMinumanName = miDialogView.findViewById<EditText>(R.id.etMinumanName)
        val etMinumanDeskripsi = miDialogView.findViewById<EditText>(R.id.etMinumanDeskripsi)
        val etMinumanharga = miDialogView.findViewById<EditText>(R.id.etMinumanHarga)

        val btnUpdateDataMinuman = miDialogView.findViewById<Button>(R.id.btnUpdateDataMinumanDialog)

        etMinumanName.setText(intent.getStringExtra("minumanName").toString())
        etMinumanDeskripsi.setText(intent.getStringExtra("minumanDeskripsi").toString())
        etMinumanharga.setText(intent.getStringExtra("minumanHarga").toString())

        miDialog.setTitle("Updating $minumanName Record")

        val alertDialog = miDialog.create()
        alertDialog.show()

        btnUpdateDataMinuman.setOnClickListener {
            updateMinumanData(
                minumanId,
                etMinumanName.text.toString(),
                etMinumanDeskripsi.text.toString(),
                etMinumanharga.text.toString()
            )

            Toast.makeText(applicationContext, "Minuman Data Updated", Toast.LENGTH_LONG).show()

            tvMinumanName.text = etMinumanName.text.toString()
            tvMinumanDeskripsi.text = etMinumanDeskripsi.text.toString()
            tvMinumanHarga.text = etMinumanharga.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateMinumanData(
        minumanId: String,
        minumanName: String,
        minumanDeskripsi: String,
        minumanHarga: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Minuman")
        val minumanInfo = MinumanModel(minumanId, minumanName, minumanDeskripsi, minumanHarga)
        dbRef.setValue(minumanInfo)
    }
}