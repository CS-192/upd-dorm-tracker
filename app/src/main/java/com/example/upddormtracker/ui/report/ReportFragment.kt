package com.example.upddormtracker.ui.report

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.UserViewModel
import com.example.upddormtracker.databinding.FragmentReportBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ReportFragment : Fragment() {

    companion object {
        fun newInstance() = ReportFragment()
    }

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private var selectedSubject: String? = null
    private var selectedDetails: String? = null
    private var selectedDorm: String? = null
    private var selectedStudentNumber: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSubject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                selectedSubject = "$s"
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etDetails.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                selectedDetails = "$s"
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSubmit.setOnClickListener{
            saveRequestToFirestore()
        }

        userViewModel.dorm.observe(viewLifecycleOwner) { dorm ->
            selectedDorm = dorm
        }

        userViewModel.studentNumber.observe(viewLifecycleOwner) { studentNumber ->
            selectedStudentNumber = studentNumber
        }
    }

    private fun saveRequestToFirestore() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.e("Firestore", "User is not logged in")
            return
        }

        val userId = user.uid
        val displayName = user.displayName ?: "Unknown User"
        val db = FirebaseFirestore.getInstance()

        val subject = selectedSubject?.trim()
        val details = selectedDetails?.trim()
        val dorm = selectedDorm?.trim()
        val studentNumber = selectedStudentNumber?.trim()


        // Validate inputs
        if (subject.isNullOrEmpty() || details.isNullOrEmpty() ||
            dorm.isNullOrEmpty() || studentNumber.isNullOrEmpty()) {

            Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            return  // Stop execution if any field is empty
        }

        // Prepare data for Firestore
        val requestData = hashMapOf(
            "type" to "report",
            "userId" to userId,
            "name" to displayName,
            "dorm" to dorm,
            "subject" to subject,
            "details" to details,
            "studentNumber" to studentNumber,
            "timestamp" to FieldValue.serverTimestamp()
        )

        binding.btnSubmit.apply {
            isEnabled = false
            alpha = 0.5f  // Makes the button look disabled
        }
        db.collection("requests")
            .add(requestData)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Request saved with ID: ${documentReference.id}")
                Toast.makeText(requireContext(), "Succesfully Created Request", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error saving request", e)
                Toast.makeText(requireContext(), "Error saving request", Toast.LENGTH_SHORT).show()
                binding.btnSubmit.apply {
                    isEnabled = true
                    alpha = 1.0f
                }
            }
    }
}