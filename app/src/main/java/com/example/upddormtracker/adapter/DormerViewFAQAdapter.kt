package com.example.upddormtracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import com.example.upddormtracker.datamodel.FAQ
import com.google.firebase.firestore.FirebaseFirestore


class DormerViewFAQAdapter(private val faqList: MutableList<FAQ>,
                           private val firestore: FirebaseFirestore
): RecyclerView.Adapter<DormerViewFAQAdapter.DormerViewFAQViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DormerViewFAQViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_faq_dormer,
            parent, false)
        return DormerViewFAQViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return faqList.size
    }

    override fun onBindViewHolder(holder: DormerViewFAQViewHolder, position: Int) {
        val currentItem = faqList[position]
        holder.bind(currentItem)
    }

    inner class DormerViewFAQViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val question: TextView = itemView.findViewById(R.id.tvQuestion)
        private val answer: TextView = itemView.findViewById(R.id.tvAnswer)



        fun bind(faq: FAQ) {
            // Set text for TextViews
            question.text = faq.question
            answer.text = faq.answer

            }

        }







    }



