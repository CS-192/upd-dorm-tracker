package com.example.upddormtracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import com.example.upddormtracker.datamodel.FAQ


class FAQAdapter(private val faqList: List<FAQ>): RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {


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
        holder.question.text = currentItem.question
        holder.answer.text = currentItem.answer
    }



    class FAQViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val question: TextView = itemView.findViewById(R.id.tvQuestion)
        val answer: TextView = itemView.findViewById(R.id.tvAnswer)
    }


}