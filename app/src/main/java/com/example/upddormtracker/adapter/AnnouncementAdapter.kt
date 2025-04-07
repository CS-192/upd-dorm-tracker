package com.example.upddormtracker.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import com.example.upddormtracker.datamodel.Announcement
import com.google.firebase.firestore.FirebaseFirestore


class AnnouncementAdapter(private val announcementList: MutableList<Announcement>,
                          private val firestore: FirebaseFirestore, private val onEditClick: (String) -> Unit
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
        holder.bind(currentItem, position)


    }

    inner class AnnouncementViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val subject: TextView = itemView.findViewById(R.id.tvSubject)
        private val details: TextView = itemView.findViewById(R.id.tvDetails)
        private val date: TextView = itemView.findViewById(R.id.tvDate)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_announcement_button)
        private val editButton: Button = itemView.findViewById(R.id.edit_announcement_button)

        fun bind(announcement: Announcement, position: Int) {
            // Set text for TextViews
            subject.text = announcement.subject
            details.text = announcement.details
            date.text = announcement.date


            // Set up delete button
            deleteButton.setOnClickListener {
                showDeleteConfirmationDialog(announcement, position)
            }

            // Set up edit button
            editButton.setOnClickListener {
                onEditClick(announcement.documentId)
            }

        }
        private fun showDeleteConfirmationDialog(announcement: Announcement, position: Int) {
            AlertDialog.Builder(itemView.context)
                .setTitle("Delete Announcement")
                .setMessage("Are you sure you want to delete this announcement?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteAnnouncement(announcement, position)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        private fun deleteAnnouncement(announcement: Announcement, position: Int) {
            // Assuming your Firestore collection is named "announcements"
            // And that your Announcement model has a documentId field
            firestore.collection("announcements")
                .document(announcement.documentId)
                .delete()
                .addOnSuccessListener {
                    // Remove the item from the list
                    announcementList.removeAt(position)
                    // Notify the adapter about the item removal
                    notifyItemRemoved(position)

                    // Optional: Show a success toast
                    Toast.makeText(itemView.context, "Announcement deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(itemView.context, "Failed to delete announcement", Toast.LENGTH_SHORT).show()
                }
        }



    }
}


