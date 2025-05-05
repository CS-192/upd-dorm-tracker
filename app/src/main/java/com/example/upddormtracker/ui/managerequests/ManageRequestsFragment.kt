package com.example.upddormtracker.ui.managerequests

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import com.example.upddormtracker.UserViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

class ManageRequestsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RequestAdapter
    private val db = Firebase.firestore
    private val allRequests = mutableListOf<Request>()
    lateinit var tvLateNight: TextView
    lateinit var tvMonthlyBill: TextView
    lateinit var tvReports: TextView
    lateinit var tvthirdColumn: TextView
    lateinit var activeFilterTextView: TextView
    private val userViewModel: UserViewModel by activityViewModels()
    private var userDorm: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manage_requests, container, false)
        recyclerView = view.findViewById(R.id.rvRequests)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RequestAdapter(allRequests)
        recyclerView.adapter = adapter
        tvLateNight = view.findViewById(R.id.tvLateNight)
        tvMonthlyBill = view.findViewById(R.id.tvMonthlyBill)
        tvReports = view.findViewById(R.id.tvReports)

        userViewModel.dorm.observe(viewLifecycleOwner) {
            userDorm = it
            fetchRequests(it)
            filter("pass", tvLateNight, view)
        }

        tvLateNight.setOnClickListener { filter("pass", tvLateNight, view) }
        tvMonthlyBill.setOnClickListener { filter("billing", tvMonthlyBill, view) }
        tvReports.setOnClickListener { filter("report", tvReports, view) }

        return view
    }

    private fun fetchRequests(dormName: String) {
        Log.d("Check", dormName)
        db.collection("requests")
            .whereEqualTo("dorm", dormName) // Filter by dorm
            .orderBy("timestamp", Query.Direction.DESCENDING) // Sort by latest
            .get()
            .addOnSuccessListener { result ->
                val requestList = mutableListOf<Request>()
                for (document in result) {
                    val fullName = document.getString("name") ?: ""
                    val timestamp = document.getTimestamp("timestamp")
                    val type = document.getString("type") ?: ""
                    val details = when (type) {
                        "pass" -> document.getString("pass") ?: ""
                        "report" -> document.getString("subject") ?: ""
                        "billing" -> when (document.getBoolean("resolved") ?: null) {
                            true -> "Resolved"
                            false -> "Unresolved"
                            else -> formatMonthRange(
                                "${document.getString("startDate")}-${
                                    document.getString(
                                        "endDate"
                                    )
                                }"
                            )
                        }

                        else -> ""
                    }

                    val dateFormatted = timestamp?.toDate()?.let { date ->
                        SimpleDateFormat("MM/dd", Locale.getDefault()).format(date)
                    } ?: "N/A"

                    requestList.add(Request(fullName, dateFormatted, type, details))
                }

                allRequests.clear()
                allRequests.addAll(requestList)
                adapter.updateList(allRequests.filter { it.type == "pass" })
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to fetch requests", Toast.LENGTH_SHORT)
                    .show()
            }
    }


    private fun filter(type: String, selectedTextView: TextView, view: View) {
        val filtered = allRequests.filter { it.type == type }
        adapter.updateList(filtered)

        tvthirdColumn = view.findViewById(R.id.tvThirdRow)
        tvthirdColumn.setText(
            when (type) {
                "pass" -> "Type"
                "billing" -> "Resolved"
                "report" -> "Subject"
                else -> ""
            }
        )

        // Reset previous selection (if exists)
        if (::activeFilterTextView.isInitialized) {
            activeFilterTextView.setTypeface(null, Typeface.NORMAL)
            activeFilterTextView.setBackgroundTintList(null) // Reset background tint
            activeFilterTextView.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Small)
        }

        // Apply styles to selected filter
        selectedTextView.setTypeface(null, Typeface.BOLD)
        selectedTextView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F6A0A0"))) // Light blue or any color
        selectedTextView.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body1)

        activeFilterTextView = selectedTextView
    }

    fun formatMonthRange(input: String): String {
        val parts = input.split("–", "-", "—")
        if (parts.size != 4) return input // Expecting [yyyy, mm, yyyy, mm]

        val startYear = parts[0].trim()
        val startMonth = parts[1].trim().padStart(2, '0')
        val endYear = parts[2].trim()
        val endMonth = parts[3].trim().padStart(2, '0')

        return when {
            startYear == endYear && startMonth == endMonth ->
                "$startMonth/$startYear"

            startYear == endYear ->
                "$startMonth–$endMonth/$endYear"

            else ->
                "$startMonth/$startYear–$endMonth/$endYear"
        }
    }

}