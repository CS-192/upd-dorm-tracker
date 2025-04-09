package com.example.upddormtracker.ui.managedormers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R

class DormerAdapter(private val dormers: List<Dormer>) : RecyclerView.Adapter<DormerAdapter.DormerViewHolder>() {

    class DormerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val btnViewDormer: TextView = itemView.findViewById(R.id.btnViewDormer)
        val tvRoomNumber: TextView = itemView.findViewById(R.id.tvRoomNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DormerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dormer, parent, false)
        return DormerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DormerViewHolder, position: Int) {
        val dormer = dormers[position]
        holder.tvName.text = "${dormer.firstName} ${dormer.lastName}"
        holder.tvRoomNumber.text = dormer.roomNumber
        holder.btnViewDormer.setOnClickListener{
            val bundle = Bundle().apply {
                putSerializable("dormer", dormer)
            }
            it.findNavController().navigate(R.id.editDormerFragment, bundle)
        }
    }

    override fun getItemCount() = dormers.size
}
