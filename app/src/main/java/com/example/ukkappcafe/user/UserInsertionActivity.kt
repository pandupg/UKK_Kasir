package com.example.ukkappcafe.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.ukkappcafe.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserInsertionActivity : AppCompatActivity() {
    private lateinit var etUserName: EditText
    private lateinit var etUserUsername: EditText
    private lateinit var etUserPassword: EditText
    private lateinit var etUserRole: EditText
    private lateinit var btnSaveDataUser: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_insertion)

        etUserName = findViewById(R.id.etUserName)
        etUserUsername = findViewById(R.id.etUserUsername)
        etUserPassword = findViewById(R.id.etUserPassword)
        etUserRole = findViewById(R.id.etUserRole)
        btnSaveDataUser = findViewById(R.id.btnSaveUser)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnSaveDataUser.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData() {
        val userName = etUserName.text.toString()
        val userUsername = etUserUsername.text.toString()
        val userPassword = etUserPassword.text.toString()
        val userRole = etUserRole.text.toString()

        if (userName.isEmpty()) {
            etUserName.error = "Please enter name"
        }
        if (userUsername.isEmpty()) {
            etUserUsername.error = "Please enter username"
        }
        if (userPassword.isEmpty()) {
            etUserPassword.error = "Please enter password"
        }
        if (userRole.isEmpty()) {
            etUserRole.error = "Please enter role"
        }

        val userId = dbRef.push().key!!

        val user = UserModel(userId, userName, userUsername, userPassword, userRole)

        dbRef.child(userId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etUserName.text.clear()
                etUserUsername.text.clear()
                etUserPassword.text.clear()
                etUserRole.text.clear()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}