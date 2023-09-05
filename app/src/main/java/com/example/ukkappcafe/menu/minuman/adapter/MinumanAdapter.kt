package com.example.ukkappcafe.menu.minuman.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.R
import com.example.ukkappcafe.menu.minuman.MinumanModel

class MinumanAdapter (private val minumanList: ArrayList<MinumanModel>)
    : RecyclerView.Adapter<MinumanAdapter.ViewHolder>()  {
    private lateinit var miListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        miListener = clickListener
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvMinumanName: TextView = itemView.findViewById(R.id.tvMinumanName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.minuman_list_item, parent, false)
        return ViewHolder(itemView, miListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMinuman = minumanList[position]
        holder.tvMinumanName.text = currentMinuman.minumanName
    }

    override fun getItemCount(): Int {
        return minumanList.size
    }
}