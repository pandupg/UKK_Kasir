package com.example.ukkappcafe.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.R
import com.example.ukkappcafe.menu.MenuModel

class MenuAdapter (private val menuList: ArrayList<MenuModel>)
    : RecyclerView.Adapter<MenuAdapter.ViewHolder> () {
    private lateinit var menuListener: onItemCLickListener

    interface onItemCLickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemCLickListener){
        menuListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_list_item, parent, false)
        return ViewHolder(itemView, menuListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMenu = menuList[position]
        holder.tvMenuName.text = currentMenu.menuName
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemCLickListener) : RecyclerView.ViewHolder(itemView) {
        val tvMenuName: TextView = itemView.findViewById(R.id.tvMenuName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}