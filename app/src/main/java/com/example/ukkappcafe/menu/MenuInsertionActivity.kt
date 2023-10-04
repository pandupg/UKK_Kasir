package com.example.ukkappcafe.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ukkappcafe.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MenuInsertionActivity : AppCompatActivity() {
    private lateinit var etMenuName: EditText
    private lateinit var etMenuJenis: EditText
    private lateinit var etMenuDeskripsi: EditText
    private lateinit var etMenuHarga: EditText
    private lateinit var btnSaveDataMenu: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_insertion)

        etMenuName = findViewById(R.id.etMenuName)
        etMenuJenis = findViewById(R.id.etMenuJenis)
        etMenuDeskripsi = findViewById(R.id.etMenuDeskripsi)
        etMenuHarga = findViewById(R.id.etMenuHarga)

        btnSaveDataMenu = findViewById(R.id.btnSaveMenu)

        dbRef = FirebaseDatabase.getInstance().getReference("Menu")

        btnSaveDataMenu.setOnClickListener {
            saveMenuData()
        }
    }

    private fun saveMenuData() {
        val menuName = etMenuName.text.toString()
        val menuJenis = etMenuJenis.text.toString()
        val menuDeskripsi = etMenuDeskripsi.text.toString()
        val menuHarga = etMenuHarga.text.toString()

        if (menuName.isEmpty()){
            etMenuName.error = "Please enter name"
        }

        if (menuJenis.isEmpty()){
            etMenuJenis.error = "Please enter jenis menu"
        }

        if (menuDeskripsi.isEmpty()){
            etMenuDeskripsi.error = "Please enter deskripsi"
        }

        if (menuHarga.isEmpty()){
            etMenuHarga.error = "Please enter harga"
        }

        val menuId = dbRef.push().key!!

        val menu = MenuModel(menuId, menuName, menuJenis, menuDeskripsi, menuHarga)

        dbRef.child(menuId).setValue(menu).addOnCompleteListener {
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

            etMenuName.text.clear()
            etMenuJenis.text.clear()
            etMenuDeskripsi.text.clear()
            etMenuHarga.text.clear()
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }
}