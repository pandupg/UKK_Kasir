package com.example.ukkappcafe.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.MainActivity
import com.example.ukkappcafe.R
import com.example.ukkappcafe.user.adapter.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class UserMainActivity : AppCompatActivity() {

    private lateinit var btnInsertDataUser: FloatingActionButton
    private lateinit var btnBackFromUser: ImageButton

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertDataUser = findViewById(R.id.btnInsertDataUser)
        btnInsertDataUser.setOnClickListener {
            val intent = Intent(this, UserInsertionActivity::class.java)
            startActivity(intent)
        }

        btnBackFromUser = findViewById(R.id.btnBackFromUser)
        btnBackFromUser.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        userRecyclerView = findViewById(R.id.rvUser)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userList = arrayListOf<UserModel>()

        getUsersData()
    }

    private fun getUsersData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()) {
                    for (userSnap in snapshot.children){
                        val userData = userSnap.getValue(UserModel::class.java)
                        userList.add(userData!!)
                    }
                    val uAdapter = UserAdapter(userList)
                    userRecyclerView.adapter = uAdapter

                    uAdapter.setOnItemClickListener(object : UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@UserMainActivity, UserDetailsActivity::class.java)

                            intent.putExtra("userId", userList[position].userId)
                            intent.putExtra("userName", userList[position].userName)
                            intent.putExtra("userUsername", userList[position].userUsername)
                            intent.putExtra("userPassword", userList[position].userPassword)
                            intent.putExtra("userRole", userList[position].userRole)
                            startActivity(intent)
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}