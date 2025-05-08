package com.example.upddormtracker.ui.entryexitlogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault()).apply {
    timeZone = TimeZone.getTimeZone("Asia/Manila")
}

class EntryExitLogAdapter(private val logs: MutableList<EntryExitLog>) :
    RecyclerView.Adapter<EntryExitLogAdapter.LogViewHolder>() {

    class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvInOrOut: TextView = itemView.findViewById(R.id.tvInOrOut)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_log, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val log = logs[position]

        holder.tvTime.text = timeFormatter.format(log.date)
        holder.tvName.text = log.studentNumber
        holder.tvInOrOut.text = when (log.entryOrExit) {
            "entry" -> "IN"
            "exit" -> "OUT"
            else -> "-"
        }
    }

    override fun getItemCount() = logs.size

    fun submitList(newLogs: List<EntryExitLog>) {
        logs.clear()
        logs.addAll(newLogs)
        notifyDataSetChanged()
    }
}
