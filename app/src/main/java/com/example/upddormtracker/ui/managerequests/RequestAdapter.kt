package com.example.upddormtracker.ui.managerequests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R

class RequestAdapter(private var requestList: List<Request>) :
    RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val type: TextView = itemView.findViewById(R.id.tvType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_request, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requestList[position]
        holder.name.text = request.name.trim().substringAfterLast(" ")
        holder.date.text = request.date
        holder.type.text = request.type
    }

    override fun getItemCount() = requestList.size

    fun updateList(newList: List<Request>) {
        requestList = newList
        notifyDataSetChanged()
    }
}
