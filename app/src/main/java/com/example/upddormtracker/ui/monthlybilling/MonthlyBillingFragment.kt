package com.example.upddormtracker.ui.monthlybilling

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.UserViewModel
import com.example.upddormtracker.databinding.FragmentMonthlyBillingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.reflect.KMutableProperty0

class MonthlyBillingFragment : Fragment() {

    private var _binding: FragmentMonthlyBillingBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var selectedStartDate: String? = null
    private var selectedEndDate: String? = null
    private var selectedDorm: String? = null
    private var selectedStudentNumber: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val MonthlyBillingViewModel =
            ViewModelProvider(this).get(MonthlyBillingViewModel::class.java)

        _binding = FragmentMonthlyBillingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        MonthlyBillingViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSelectStart.setOnClickListener {
            showDatePicker(binding.btnSelectStart, ::selectedStartDate)
        }

        binding.btnSelectEnd.setOnClickListener {
            showDatePicker(binding.btnSelectEnd, ::selectedEndDate)
        }

        binding.btnSubmit.setOnClickListener {
            saveRequestToFirestore()
        }

        binding.textView2.text = "Monthly Billing"
        binding.textView3.text = "Please answer all of the questions below"
        binding.textView5.text = "Start Date"
        binding.textView6.text = "End Date"


        userViewModel.dorm.observe(viewLifecycleOwner) { dorm ->
            selectedDorm = dorm
        }

        userViewModel.studentNumber.observe(viewLifecycleOwner) { studentNumber ->
            selectedStudentNumber = studentNumber
        }
    }

    private fun showDatePicker(tvSelectedDate: TextView, variable: KMutableProperty0<String?>) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, _ ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, 1) // Always set day to 1

            val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault()) // Month-Year format

            // Save the selected date
            variable.set(dateFormat.format(selectedCalendar.time))

            // Update TextView
            tvSelectedDate.text = dateFormat.format(selectedCalendar.time)
        }, year, month, 1) // Setting the day to 1
        datePickerDialog.show()
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

        val startDate = selectedStartDate?.trim()
        val endDate = selectedEndDate?.trim()
        val dorm = selectedDorm?.trim()
        val studentNumber = selectedStudentNumber?.trim()

        // Validate inputs
        if (startDate.isNullOrEmpty() || endDate.isNullOrEmpty() ||
            dorm.isNullOrEmpty() || studentNumber.isNullOrEmpty()) {

            Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            return  // Stop execution if any field is empty
        }

        // Prepare data for Firestore
        val requestData = hashMapOf(
            "type" to "billing",
            "userId" to userId,
            "name" to displayName,
            "dorm" to dorm,
            "startDate" to startDate,
            "endDate" to endDate,
            "studentNumber" to studentNumber,
            "resolved" to false,
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}