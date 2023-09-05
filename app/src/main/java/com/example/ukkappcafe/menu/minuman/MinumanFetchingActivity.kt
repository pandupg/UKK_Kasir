package com.example.ukkappcafe.menu.minuman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.R
import com.example.ukkappcafe.menu.minuman.adapter.MinumanAdapter
import com.google.firebase.database.*

class MinumanFetchingActivity : AppCompatActivity() {
    private lateinit var minumanRecyclerView: RecyclerView
    private lateinit var tvLoadingDataMinuman: TextView
    private lateinit var minumanList: ArrayList<MinumanModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minuman_fetching)

        minumanRecyclerView = findViewById(R.id.rvMinuman)
        minumanRecyclerView.layoutManager = LinearLayoutManager(this)
        minumanRecyclerView.setHasFixedSize(true)
        tvLoadingDataMinuman = findViewById(R.id.tvLoadingDataMinuman)

        minumanList = arrayListOf<MinumanModel>()

        getMinumanData()
    }

    private fun getMinumanData(){
        minumanRecyclerView.visibility = View.GONE
        tvLoadingDataMinuman.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Minuman")

        dbRef.addValueEventListener(object  :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                minumanList.clear()
                if (snapshot.exists()){
                    for (minumanSnap in snapshot.children){
                        val minumanData = minumanSnap.getValue(MinumanModel::class.java)
                        minumanList.add(minumanData!!)
                    }
                    val miAdapter = MinumanAdapter(minumanList)
                    minumanRecyclerView.adapter = miAdapter

                    miAdapter.setOnItemClickListener(object : MinumanAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@MinumanFetchingActivity, MinumanDetailsActivity::class.java)

                            intent.putExtra("minumanId", minumanList[position].minumanId)
                            intent.putExtra("minumanName", minumanList[position].minumanName)
                            intent.putExtra("minumanDeskripsi", minumanList[position].minumanDeskripsi)
                            intent.putExtra("minumanHarga", minumanList[position].minumanHarga)
                            startActivity(intent)
                        }

                    })
                    minumanRecyclerView.visibility = View.VISIBLE
                    tvLoadingDataMinuman.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}