package com.example.ukkappcafe.menu

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

class MenuDetailsActivity : AppCompatActivity() {
    private lateinit var tvMenuId: TextView
    private lateinit var tvMenuName: TextView
    private lateinit var tvMenuJenis: TextView
    private lateinit var tvMenuDeskripsi: TextView
    private lateinit var tvMenuHarga: TextView
    private lateinit var btnUpdateMenu: Button
    private lateinit var btnDeleteMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_details)

        initView()
        setValuesToViews()

        btnUpdateMenu.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("menuId").toString(),
                intent.getStringExtra("menuName").toString()
            )
        }

        btnDeleteMenu.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("menuId").toString()
            )
        }
    }

    private fun initView() {
        tvMenuId = findViewById(R.id.tvMenuId)
        tvMenuName = findViewById(R.id.tvMenuName)
        tvMenuJenis =  findViewById(R.id.tvMenuJenis)
        tvMenuDeskripsi = findViewById(R.id.tvMenuDeskripsi)
        tvMenuHarga = findViewById(R.id.tvMenuHarga)

        btnUpdateMenu = findViewById(R.id.btnUpdateMenu)
        btnDeleteMenu = findViewById(R.id.btnDeleteMenu)
    }

    private fun setValuesToViews() {
        tvMenuId.text = intent.getStringExtra("menuId")
        tvMenuName.text = intent.getStringExtra("menuName")
        tvMenuJenis.text = intent.getStringExtra("menuJenis")
        tvMenuDeskripsi.text = intent.getStringExtra("menuDeskripsi")
        tvMenuHarga.text = intent.getStringExtra("menuHarga")
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(id)
        val menuTask = dbRef.removeValue()

        menuTask.addOnSuccessListener {
            Toast.makeText(this, "Menu data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MenuActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        menuId: String,
        menuName: String
    ){
        val menuDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val menuDialogView = inflater.inflate(R.layout.menu_update_dialog, null)

        menuDialog.setView(menuDialogView)

        val etMenuName = menuDialogView.findViewById<EditText>(R.id.etMenuName)
        val etMenuJenis = menuDialogView.findViewById<EditText>(R.id.etMenuJenis)
        val etMenuDeskripsi = menuDialogView.findViewById<EditText>(R.id.etMenuDeskripsi)
        val etMenuHarga = menuDialogView.findViewById<EditText>(R.id.etMenuHarga)

        val btnUpdateDataMenu = menuDialogView.findViewById<Button>(R.id.btnUpdateDataMenuDialog)

        etMenuName.setText(intent.getStringExtra("menuName").toString())
        etMenuJenis.setText(intent.getStringExtra("menuJenis").toString())
        etMenuDeskripsi.setText(intent.getStringExtra("menuDeskripsi").toString())
        etMenuHarga.setText(intent.getStringExtra("menuHarga").toString())

        menuDialog.setTitle("Updating $menuName Record")

        val alertDialog = menuDialog.create()
        alertDialog.show()

        btnUpdateDataMenu.setOnClickListener {
            updateMenuData(
                menuId,
                etMenuName.text.toString(),
                etMenuJenis.text.toString(),
                etMenuDeskripsi.text.toString(),
                etMenuHarga.text.toString()
            )

            Toast.makeText(applicationContext, "Menu Data Updated", Toast.LENGTH_LONG).show()

            tvMenuName.text = etMenuName.text.toString()
            tvMenuJenis.text = etMenuJenis.text.toString()
            tvMenuDeskripsi.text = etMenuDeskripsi.text.toString()
            tvMenuHarga.text = etMenuHarga.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateMenuData(
        menuId: String,
        menuName: String,
        menuJenis: String,
        menuDeskripsi: String,
        menuHarga: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(menuId)
        val menuInfo = MenuModel(menuId, menuName, menuJenis, menuDeskripsi, menuHarga)
        dbRef.setValue(menuInfo)
    }
}