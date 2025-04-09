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
import com.example.upddormtracker.datamodel.FAQ
import com.google.firebase.firestore.FirebaseFirestore


class FAQAdapter(private val faqList: MutableList<FAQ>,
                 private val firestore: FirebaseFirestore, private val onEditClick: (String) -> Unit
): RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.faq_card,
            parent, false)
        return FAQViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return faqList.size
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        val currentItem = faqList[position]
        holder.bind(currentItem, position)
    }



    inner class FAQViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val question: TextView = itemView.findViewById(R.id.tvQuestion)
        val answer: TextView = itemView.findViewById(R.id.tvAnswer)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_faq_button)
        private val editButton: Button = itemView.findViewById(R.id.edit_faq_button)

        fun bind(faq: FAQ, position: Int) {
            // Set text for TextViews
            question.text = faq.question
            answer.text = faq.answer

            deleteButton.setOnClickListener {
                showDeleteConfirmationDialog(faq, position)
            }

            // Set up edit button
            editButton.setOnClickListener {
                onEditClick(faq.documentId)
            }

        }

        private fun showDeleteConfirmationDialog(faq: FAQ, position: Int) {
            AlertDialog.Builder(itemView.context)
                .setTitle("Delete FAQ")
                .setMessage("Are you sure you want to delete this FAQ?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteFAQ(faq, position)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        private fun deleteFAQ(faq: FAQ, position: Int) {
            firestore.collection("faqs")
                .document(faq.documentId)
                .delete()
                .addOnSuccessListener {
                    // Remove the item from the list
                    faqList.removeAt(position)
                    // Notify the adapter about the item removal
                    notifyItemRemoved(position)

                    // Optional: Show a success toast
                    Toast.makeText(itemView.context, "FAQ deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(itemView.context, "Failed to delete FAQ", Toast.LENGTH_SHORT).show()
                }
        }
    }


}