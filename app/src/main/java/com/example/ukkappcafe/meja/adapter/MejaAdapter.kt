package com.example.ukkappcafe.meja.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.R
import com.example.ukkappcafe.meja.MejaModel

class MejaAdapter (private val mejaList: ArrayList<MejaModel>)
    : RecyclerView.Adapter<MejaAdapter.ViewHolder>() {
    private lateinit var mejaListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mejaListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meja_list_item, parent, false)
        return ViewHolder(itemView, mejaListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMeja = mejaList[position]
        holder.tvMejaNomor.text = currentMeja.nomorMeja
    }

    override fun getItemCount(): Int {
        return mejaList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvMejaNomor: TextView = itemView.findViewById(R.id.tvMejaNomor)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}