package com.example.upddormtracker.ui.managerequests

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentRequestDetailsBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RequestDetailsFragment : Fragment() {

    private var _binding: FragmentRequestDetailsBinding? = null
    private val binding get() = _binding!!

    private var docId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            docId = it.getString("requestId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use `binding` to access views
        val docId = arguments?.getString("requestId") ?: return
        fetchRequestDetails(docId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchRequestDetails(docId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("requests").document(docId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Pass the entire document to the formatting function
                    val detailsList = formatDetails(document)

                    // Set up the adapter for ListView
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, detailsList)
                    binding.lvRequestDetails.adapter = adapter
                    binding.title.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                } else {
                    Toast.makeText(requireContext(), "Request not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to fetch details", Toast.LENGTH_SHORT).show()
            }
    }

    // Helper function to format the details based on pass type, using the document
    private fun formatDetails(document: DocumentSnapshot): List<String> {
        val passType = document.getString("type") ?: "N/A"

        return when (passType) {
            "pass" -> formatLateNightOvernight(document)
            "billing" -> formatBilling(document)
            "report" -> formatReportConcern(document)
            else -> listOf("Unknown request type")
        }
    }

    // Format for Late Night/Overnight requests
    private fun formatLateNightOvernight(document: DocumentSnapshot): List<String> {
        binding.title.text = "Late Night/\nOvernight Pass"
        val name = document.getString("name") ?: "N/A"
        val studentNumber = document.getString("studentNumber") ?: "N/A"
        val destination = document.getString("destination") ?: "N/A"
        val departureDate = document.getString("departureDate") ?: "N/A"
        val arrivalDate = document.getString("arrivalDate") ?: "N/A"
        val createdTimestamp = document.getTimestamp("createdAt")?.toDate() ?: Date()

        return listOf(
            "Name: $name",
            "Student Number: $studentNumber",
            "Created: ${formatTimestamp(createdTimestamp)}",
            "Destination: $destination",
            "Departure Date: $departureDate",
            "Arrival Date: $arrivalDate",
        )
    }

    // Format for Billing requests
    private fun formatBilling(document: DocumentSnapshot): List<String> {
        binding.title.text = "Monthly Billing"
        val name = document.getString("name") ?: "N/A"
        val studentNumber = document.getString("studentNumber") ?: "N/A"
        val startDate = document.getString("startDate") ?: "N/A"
        val endDate = document.getString("endDate") ?: "N/A"
        val resolved = document.getBoolean("resolved") ?: false
        val createdTimestamp = document.getTimestamp("createdAt")?.toDate() ?: Date()

        return listOf(
            "Name: $name",
            "Student Number: $studentNumber",
            "Created: ${formatTimestamp(createdTimestamp)}",
            "Start Date: $startDate",  // More specific wording for billing
            "End Date: $endDate",           // Change to reflect due date for billing
            "Resolved: ${if (resolved) "Yes" else "No"}",
        )
    }

    // Format for Report/Concern requests
    private fun formatReportConcern(document: DocumentSnapshot): List<String> {
        binding.title.text = "Report/Concern"
        val name = document.getString("name") ?: "N/A"
        val studentNumber = document.getString("studentNumber") ?: "N/A"
        val subject = document.getString("subject") ?: "N/A"
        val details = document.getString("details") ?: "N/A"
        val createdTimestamp = document.getTimestamp("createdAt")?.toDate() ?: Date()

        return listOf(
            "Name: $name",
            "Student Number: $studentNumber",
            "Created: ${formatTimestamp(createdTimestamp)}",
            "Subject: $subject", // Treat destination as the issue description
            "Details: $details",           // Date of report
        )
    }


    private fun formatTimestamp(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}
