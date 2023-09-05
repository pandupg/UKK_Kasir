package com.example.ukkappcafe.menu.makanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.R
import com.example.ukkappcafe.menu.makanan.adapter.MakananAdapter
import com.example.ukkappcafe.user.UserDetailsActivity
import com.google.firebase.database.*

class MakananFetchingActivity : AppCompatActivity() {
    private lateinit var makananRecyclerView: RecyclerView
    private lateinit var tvLoadingDataMakanan: TextView
    private lateinit var makananList: ArrayList<MakananModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan_fetching)

        makananRecyclerView = findViewById(R.id.rvMakanan)
        makananRecyclerView.layoutManager = LinearLayoutManager(this)
        makananRecyclerView.setHasFixedSize(true)
        tvLoadingDataMakanan = findViewById(R.id.tvLoadingDataMakanan)

        makananList = arrayListOf<MakananModel>()

        getMakananData()
    }

    private fun getMakananData() {
        makananRecyclerView.visibility = View.GONE
        tvLoadingDataMakanan.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Makanan")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                makananList.clear()
                if (snapshot.exists()){
                    for (makananSnap in snapshot.children){
                        val makananData = makananSnap.getValue(MakananModel::class.java)
                        makananList.add(makananData!!)
                    }
                    val mAdapter = MakananAdapter(makananList)
                    makananRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemCLickListener(object :MakananAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@MakananFetchingActivity, MakananDetailsActivity::class.java)

                            intent.putExtra("makananId", makananList[position].makananId)
                            intent.putExtra("makananName", makananList[position].makananName)
                            intent.putExtra("makananDeskripsi", makananList[position].makananDeskripsi)
                            intent.putExtra("makananHarga", makananList[position].makananHarga)
                            startActivity(intent)
                        }

                    })
                    makananRecyclerView.visibility = View.VISIBLE
                    tvLoadingDataMakanan.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}