package com.example.ukkappcafe.menu.makanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ukkappcafe.R
import com.example.ukkappcafe.user.UserFetchingActivity
import com.google.firebase.database.FirebaseDatabase

class MakananDetailsActivity : AppCompatActivity() {
    private lateinit var tvMakananId: TextView
    private lateinit var tvMakananName: TextView
    private lateinit var tvMakananDeskripsi: TextView
    private lateinit var tvMakananHarga: TextView
    private lateinit var btnUpdateMakanan: Button
    private lateinit var btnDeleteMakanan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan_details)

        initView()
        setValuesToViews()

        btnUpdateMakanan.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("makananId").toString(),
                intent.getStringExtra("makananName").toString()
            )
        }

        btnDeleteMakanan.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("makananId").toString()
            )
        }
    }

    private fun initView() {
        tvMakananId = findViewById(R.id.tvMakananId)
        tvMakananName = findViewById(R.id.tvMakananName)
        tvMakananDeskripsi = findViewById(R.id.tvMakananDeskripsi)
        tvMakananHarga = findViewById(R.id.tvMakananHarga)

        btnUpdateMakanan = findViewById(R.id.btnUpdateMakanan)
        btnDeleteMakanan = findViewById(R.id.btnDeleteMakanan)
    }

    private fun setValuesToViews() {
        tvMakananId.text = intent.getStringExtra("makananId")
        tvMakananName.text = intent.getStringExtra("makananName")
        tvMakananDeskripsi.text = intent.getStringExtra("makananDeskripsi")
        tvMakananHarga.text = intent.getStringExtra("makananHarga")
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Makanan").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Makanan data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MakananFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        makananId: String,
        makananName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.makanan_update_dialog, null)

        mDialog.setView(mDialogView)

        val etMakananName = mDialogView.findViewById<EditText>(R.id.etMakananName)
        val etMakananDeskripsi = mDialogView.findViewById<EditText>(R.id.etMakananDeskripsi)
        val etMakananHarga = mDialogView.findViewById<EditText>(R.id.etMakananHarga)

        val btnUpdateDataMakanan = mDialogView.findViewById<Button>(R.id.btnUpdateDataMakananDialog)

        etMakananName.setText(intent.getStringExtra("makananName").toString())
        etMakananDeskripsi.setText(intent.getStringExtra("makananDeskripsi").toString())
        etMakananHarga.setText(intent.getStringExtra("makananHarga").toString())

        mDialog.setTitle("Updating $makananName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateDataMakanan.setOnClickListener {
            updateMakananData(
                makananId,
                etMakananName.text.toString(),
                etMakananDeskripsi.text.toString(),
                etMakananHarga.text.toString()
            )

            Toast.makeText(applicationContext, "Makanan Data Updated", Toast.LENGTH_LONG).show()

            tvMakananName.text = etMakananName.text.toString()
            tvMakananDeskripsi.text = etMakananDeskripsi.text.toString()
            tvMakananHarga.text = etMakananHarga.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateMakananData(
        makananId: String,
        makananName: String,
        makananDeskripsi: String,
        makananHarga: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Makanan").child(makananId)
        val makananInfo = MakananModel(makananId, makananName, makananDeskripsi, makananHarga)
        dbRef.setValue(makananInfo)
    }
}