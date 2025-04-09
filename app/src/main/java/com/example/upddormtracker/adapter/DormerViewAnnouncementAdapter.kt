package com.example.upddormtracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import com.example.upddormtracker.datamodel.Announcement
import com.google.firebase.firestore.FirebaseFirestore


class DormerViewAnnouncementAdapter(private val announcementList: MutableList<Announcement>,
                          private val firestore: FirebaseFirestore
): RecyclerView.Adapter<DormerViewAnnouncementAdapter.DormerViewAnnouncementViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DormerViewAnnouncementViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_announcement_dormer,
            parent, false)
        return DormerViewAnnouncementViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }

    override fun onBindViewHolder(holder: DormerViewAnnouncementViewHolder, position: Int) {
        val currentItem = announcementList[position]
        holder.bind(currentItem, position)
    }

    inner class DormerViewAnnouncementViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val subject: TextView = itemView.findViewById(R.id.tvSubject)
        private val date: TextView = itemView.findViewById(R.id.tvDate)
        private val details: TextView = itemView.findViewById(R.id.tvDetails)

        fun bind(announcement: Announcement, position: Int) {
            // Set text for TextViews
            subject.text = announcement.subject
            date.text = announcement.date
            details.text = announcement.details
        }
    }
}


