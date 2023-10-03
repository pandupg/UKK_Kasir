package com.example.ukkappcafe.meja

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.MainActivity
import com.example.ukkappcafe.R
import com.example.ukkappcafe.meja.adapter.MejaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MejaActivity : AppCompatActivity() {
    private lateinit var btnInsertDataMeja: FloatingActionButton
    private lateinit var btnBackFromMeja: ImageButton

    private lateinit var mejaRecyclerView: RecyclerView
    private lateinit var mejaList: ArrayList<MejaModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meja)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertDataMeja = findViewById(R.id.btnInsertDataMeja)
        btnInsertDataMeja.setOnClickListener {
            val intent = Intent(this, MejaInsertionActivity::class.java)
            startActivity(intent)
        }

        btnBackFromMeja = findViewById(R.id.btnBackFromMeja)
        btnBackFromMeja.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        mejaRecyclerView = findViewById(R.id.rvMeja)
        mejaRecyclerView.layoutManager = LinearLayoutManager(this)
        mejaRecyclerView.setHasFixedSize(true)

        mejaList = arrayListOf<MejaModel>()

        getMejaData()
    }

    private fun getMejaData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Meja")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mejaList.clear()
                if(snapshot.exists()){
                    for(mejaSnap in snapshot.children){
                        val mejaData = mejaSnap.getValue(MejaModel::class.java)
                        mejaList.add(mejaData!!)
                    }
                    val mejaAdapter = MejaAdapter(mejaList)
                    mejaRecyclerView.adapter = mejaAdapter

                    mejaAdapter.setOnItemClickListener(object : MejaAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@MejaActivity, MejaDetailsActivity::class.java)

                            intent.putExtra("mejaId", mejaList[position].mejaId)
                            intent.putExtra("nomorMeja", mejaList[position].nomorMeja)
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