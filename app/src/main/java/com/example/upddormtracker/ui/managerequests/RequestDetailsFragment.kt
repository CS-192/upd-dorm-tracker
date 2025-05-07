package com.example.upddormtracker.ui.managerequests

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.ui.text.toUpperCase
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentRequestDetailsBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
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

        binding.btnDeleteRequest.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        binding.btnEditComment.setOnClickListener {
            showEditCommentDialog()
        }

        binding.btnAddTag.setOnClickListener {
            showAddTagDialog()
        }
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
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        detailsList
                    )
                    binding.lvRequestDetails.adapter = adapter
                    binding.title.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                } else {
                    Toast.makeText(requireContext(), "Request not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to fetch details", Toast.LENGTH_SHORT)
                    .show()
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
        val comment = document.getString("comment") ?: "N/A"
        val tagsList = document.get("tags") as? List<*> ?: emptyList<Any>()
        val tags = if (tagsList.isNotEmpty()) tagsList.joinToString(", ") else "N/A"


        return listOf(
            "Name: $name",
            "Student Number: $studentNumber",
            "Created: ${formatTimestamp(createdTimestamp)}",
            "Destination: $destination",
            "Departure Date: $departureDate",
            "Arrival Date: $arrivalDate",
            "Comment: $comment",
            "Tags: $tags",
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
        val comment = document.getString("comment") ?: "N/A"
        val tagsList = document.get("tags") as? List<*> ?: emptyList<Any>()
        val tags = if (tagsList.isNotEmpty()) tagsList.joinToString(", ") else "N/A"


        return listOf(
            "Name: $name",
            "Student Number: $studentNumber",
            "Created: ${formatTimestamp(createdTimestamp)}",
            "Start Date: $startDate",  // More specific wording for billing
            "End Date: $endDate",           // Change to reflect due date for billing
            "Resolved: ${if (resolved) "Yes" else "No"}",
            "Comment: $comment",
            "Tags: $tags",
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
        val comment = document.getString("comment") ?: "N/A"
        val tagsList = document.get("tags") as? List<*> ?: emptyList<Any>()
        val tags = if (tagsList.isNotEmpty()) tagsList.joinToString(", ") else "N/A"

        return listOf(
            "Name: $name",
            "Student Number: $studentNumber",
            "Created: ${formatTimestamp(createdTimestamp)}",
            "Subject: $subject", // Treat destination as the issue description
            "Details: $details",           // Date of report
            "Comment: $comment",
            "Tags: $tags",
        )
    }


    private fun formatTimestamp(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Request")
            .setMessage("Are you sure you want to delete this request?")
            .setPositiveButton("Yes") { _, _ ->
                deleteRequest()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteRequest() {
        val documentId = docId ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("requests").document(documentId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Request deleted", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack() // Go back to previous fragment
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    requireContext(),
                    "Failed to delete: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun showEditCommentDialog() {
        val input = EditText(requireContext()).apply {
            hint = "Enter your comment"
            setLines(2)
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        }

        val container = FrameLayout(requireContext()).apply {
            val padding = resources.getDimensionPixelSize(R.dimen.dialog_input_padding)
            setPadding(padding, 0, padding, 0)
            addView(input)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Comment")
            .setView(container)
            .setPositiveButton("Save") { _, _ ->
                val commentText = input.text.toString().trim()
                if (commentText.isNotEmpty()) {
                    updateCommentField(commentText)
                } else {
                    Toast.makeText(requireContext(), "Comment cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateCommentField(comment: String) {
        val documentId = docId ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("requests").document(documentId)
            .update("comment", comment)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Comment updated", Toast.LENGTH_SHORT).show()
                fetchRequestDetails(documentId)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to update comment", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun showAddTagDialog() {
        val input = EditText(requireContext()).apply {
            hint = "Enter a tag"
            inputType = InputType.TYPE_CLASS_TEXT
        }

        val container = FrameLayout(requireContext()).apply {
            val padding = resources.getDimensionPixelSize(R.dimen.dialog_input_padding)
            setPadding(padding, 0, padding, 0)
            addView(input)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Add Tag")
            .setView(container)
            .setPositiveButton("Add") { _, _ ->
                val tag = input.text.toString().trim()
                if (tag.isEmpty()) {
                    Toast.makeText(requireContext(), "Tag cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                } else if (tag.length > 15) {
                    Toast.makeText(
                        requireContext(),
                        "Tag must be 15 characters or less",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (tag.trim().split("\\s+".toRegex()).size > 2) {
                    Toast.makeText(
                        requireContext(),
                        "Tag must be 1–2 words only",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    addTagToDocument(tag.uppercase())
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addTagToDocument(tag: String) {
        val documentId = docId ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("requests").document(documentId)
            .update("tags", FieldValue.arrayUnion(tag))
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Tag added", Toast.LENGTH_SHORT).show()
                fetchRequestDetails(documentId)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to add tag", Toast.LENGTH_SHORT).show()
            }
    }

}
