package com.example.ukkappcafe.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.MainActivity
import com.example.ukkappcafe.R
import com.example.ukkappcafe.menu.adapter.MenuAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MenuActivity : AppCompatActivity() {
    private lateinit var btnInsertDataMenu: FloatingActionButton
    private lateinit var btnBackFromMenu: ImageButton

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var menuList: ArrayList<MenuModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertDataMenu = findViewById(R.id.btnInsertDataMenu)
        btnInsertDataMenu.setOnClickListener {
            val intent = Intent(this, MenuInsertionActivity::class.java)
            startActivity(intent)
        }

        btnBackFromMenu = findViewById(R.id.btnBackFromMenu)
        btnBackFromMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        menuRecyclerView = findViewById(R.id.rvMenu)
        menuRecyclerView.layoutManager = LinearLayoutManager(this)
        menuRecyclerView.setHasFixedSize(true)

        menuList = arrayListOf<MenuModel>()

        getMenuData()
    }

    private fun getMenuData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Menu")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                menuList.clear()
                if (snapshot.exists()) {
                    for (menuSnap in snapshot.children) {
                        val menuData = menuSnap.getValue(MenuModel::class.java)
                        menuList.add(menuData!!)
                    }
                    val menuAdapter = MenuAdapter(menuList)
                    menuRecyclerView.adapter = menuAdapter

                    menuAdapter.setOnItemClickListener(object: MenuAdapter.onItemCLickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@MenuActivity, MenuDetailsActivity::class.java)

                            intent.putExtra("menuId", menuList[position].menuId)
                            intent.putExtra("menuName", menuList[position].menuName)
                            intent.putExtra("menuJenis", menuList[position].menuJenis)
                            intent.putExtra("menuDeskripsi", menuList[position].menuDeskripsi)
                            intent.putExtra("menuHarga", menuList[position].menuHarga)
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