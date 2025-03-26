package com.example.upddormtracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import com.example.upddormtracker.datamodel.Announcement


class AnnouncementAdapter(
    private val announcementList: List<Announcement>
): RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.announcement_card,
            parent, false)
        return AnnouncementViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        val currentItem = announcementList[position]
        holder.subject.text = currentItem.subject
        holder.details.text = currentItem.details
        holder.date.text = currentItem.date
    }

    class AnnouncementViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val subject: TextView = itemView.findViewById(R.id.tvSubject)
        val details: TextView = itemView.findViewById(R.id.tvDetails)
        val date: TextView = itemView.findViewById(R.id.tvDate)
    }
}


