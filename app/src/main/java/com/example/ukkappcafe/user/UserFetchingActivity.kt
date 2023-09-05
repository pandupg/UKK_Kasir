package com.example.ukkappcafe.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.R
import com.example.ukkappcafe.user.adapter.UserAdapter
import com.google.firebase.database.*
import org.w3c.dom.Text

class UserFetchingActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var tvLoadingDataUser: TextView
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_fetching)

        userRecyclerView = findViewById(R.id.rvUser)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        tvLoadingDataUser = findViewById(R.id.tvLoadingDataUser)

        userList = arrayListOf<UserModel>()

        getUsersData()
    }

    private fun getUsersData() {
        userRecyclerView.visibility = View.GONE
        tvLoadingDataUser.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()) {
                    for (userSnap in snapshot.children){
                        val userData = userSnap.getValue(UserModel::class.java)
                        userList.add(userData!!)
                    }
                    val uAdapter = UserAdapter(userList)
                    userRecyclerView.adapter = uAdapter

                    uAdapter.setOnItemClickListener(object :UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@UserFetchingActivity, UserDetailsActivity::class.java)

                            intent.putExtra("userId", userList[position].userId)
                            intent.putExtra("userName", userList[position].userName)
                            intent.putExtra("userUsername", userList[position].userUsername)
                            intent.putExtra("userPassword", userList[position].userPassword)
                            startActivity(intent)
                        }
                    })

                    userRecyclerView.visibility = View.VISIBLE
                    tvLoadingDataUser.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}