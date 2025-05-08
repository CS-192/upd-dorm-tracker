package com.example.upddormtracker.ui.entryexitlogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import com.example.upddormtracker.UserViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EntryExitLogsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var logAdapter: EntryExitLogAdapter
    private val logList = mutableListOf<EntryExitLog>()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_entry_exit_logs, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewLogs)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        logAdapter = EntryExitLogAdapter(logList)
        recyclerView.adapter = logAdapter

        userViewModel.dorm.observe(viewLifecycleOwner) { dorm ->
            fetchLogs(dorm)
        }

        return view
    }

    private fun fetchLogs(dorm: String) {
        val logsCollection = Firebase.firestore.collection("entry-or-exit")
        logsCollection
            .whereEqualTo("dorm", dorm)
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(50)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(context, "No logs found.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val logs = documents.mapNotNull { doc ->
                    val date = doc.getTimestamp("date")?.toDate()
                    val studentNumber = doc.getString("studentNumber")
                    val entryOrExit = doc.getString("entryOrExit")

                    if (date != null && studentNumber != null && entryOrExit != null) {
                        EntryExitLog(date, studentNumber, entryOrExit)
                    } else null
                }

                logAdapter.submitList(logs)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to fetch logs.", Toast.LENGTH_SHORT).show()
            }
    }
}
