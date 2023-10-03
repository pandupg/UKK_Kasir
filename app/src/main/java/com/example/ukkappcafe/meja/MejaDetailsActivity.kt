package com.example.ukkappcafe.meja

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

class MejaDetailsActivity : AppCompatActivity() {
    private lateinit var tvMejaId: TextView
    private lateinit var tvMejaNomor: TextView
    private lateinit var btnUpdateMeja: Button
    private lateinit var btnDeleteMeja: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meja_details)

        initView()
        setValuesToViews()

        btnUpdateMeja.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("mejaId").toString(),
                intent.getStringExtra("nomorMeja").toString()
            )
        }

        btnDeleteMeja.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("mejaId").toString()
            )
        }
    }

    private fun initView(){
        tvMejaId = findViewById(R.id.tvMejaId)
        tvMejaNomor = findViewById(R.id.tvMejaNomor)

        btnUpdateMeja = findViewById(R.id.btnUpdateMeja)
        btnDeleteMeja = findViewById(R.id.btnDeleteMeja)
    }

    private fun setValuesToViews(){
        tvMejaId.text = intent.getStringExtra("mejaId")
        tvMejaNomor.text = intent.getStringExtra("nomorMeja")
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Meja").child(id)
        val mejaTask = dbRef.removeValue()

        mejaTask.addOnSuccessListener {
            Toast.makeText(this, "Meja data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MejaActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        mejaId: String,
        nomorMeja: String
    ){
        val mejaDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mejaDialogView = inflater.inflate(R.layout.meja_update_dialog, null)

        mejaDialog.setView(mejaDialogView)

        val etMejaNomor = mejaDialogView.findViewById<EditText>(R.id.etMejaNomor)

        val btnUpdateDataMeja = mejaDialogView.findViewById<Button>(R.id.btnUpdateDataMejaDialog)

        etMejaNomor.setText(intent.getStringExtra("nomorMeja").toString())

        mejaDialog.setTitle("Updating $nomorMeja Record")

        val alertDialog = mejaDialog.create()
        alertDialog.show()

        btnUpdateDataMeja.setOnClickListener {
            updateMejaData(
                mejaId,
                etMejaNomor.text.toString()
            )

            Toast.makeText(applicationContext, "Meja Data Updated", Toast.LENGTH_LONG).show()

            tvMejaNomor.text = etMejaNomor.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateMejaData(
        mejaId: String,
        nomorMeja: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Meja").child(mejaId)
        val mejaInfo = MejaModel(mejaId, nomorMeja)
        dbRef.setValue(mejaInfo)
    }
}