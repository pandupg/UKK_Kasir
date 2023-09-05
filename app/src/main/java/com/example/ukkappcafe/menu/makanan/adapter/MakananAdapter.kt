package com.example.ukkappcafe.menu.makanan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukkappcafe.R
import com.example.ukkappcafe.menu.makanan.MakananModel

class MakananAdapter (private val makananList: ArrayList<MakananModel>)
    : RecyclerView.Adapter<MakananAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemCLickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.makanan_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMakanan = makananList[position]
        holder.tvMakananName.text = currentMakanan.makananName
    }

    override fun getItemCount(): Int {
        return makananList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvMakananName: TextView = itemView.findViewById(R.id.tvMakananName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}