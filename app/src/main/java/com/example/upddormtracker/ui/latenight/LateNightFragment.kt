package com.example.upddormtracker.ui.latenight

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.UserViewModel
import com.example.upddormtracker.databinding.FragmentLateNightBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.reflect.KMutableProperty0

class LateNightFragment : Fragment() {

    private var _binding: FragmentLateNightBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var selectedPass: String? = null
    private var selectedDepartureDate: String? = null
    private var selectedArrivalDate: String? = null
    private var selectedDestination: String? = null
    private var selectedReason: String? = null
    private var selectedDorm: String? = null
    private var selectedStudentNumber: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val lateNightViewModel =
            ViewModelProvider(this).get(LateNightViewModel::class.java)

        _binding = FragmentLateNightBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        lateNightViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = binding.spinnerRequestType

        // Create a list of items for the dropdown
        val requestTypes = listOf("Late Night Pass", "Overnight Pass")

        // Create an ArrayAdapter for the Spinner
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, requestTypes)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        // Set the adapter to the Spinner
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedPass = requestTypes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle no selection
            }
        }

        binding.btnSelectDateDeparture.setOnClickListener {
            showDatePicker(binding.btnSelectDateDeparture, ::selectedDepartureDate)
        }

        binding.btnSelectDateArrival.setOnClickListener {
            showDatePicker(binding.btnSelectDateArrival, ::selectedArrivalDate)
        }

        binding.etDestination.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                selectedDestination = "$s"
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etReason.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                selectedReason = "$s"
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSubmit.setOnClickListener {
            saveRequestToFirestore()
        }

        binding.textView2.text = "Late Night/Overnight Pass"
        binding.textView3.text = "Please answer all of the questions below"
        binding.textView4.text = "Late Night or Overnight?"
        binding.textView5.text = "Departure Date"
        binding.textView6.text = "Arrival Date"
        binding.textView7.text = "Destination"
        binding.textView8.text = "Reason"


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
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                // Save the selected date
                variable.set(dateFormat.format(selectedCalendar.time))

                // Update TextView
                tvSelectedDate.text = dateFormat.format(selectedCalendar.time)
            }, year, month, day)
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
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

        val pass = selectedPass?.trim()
        val departureDate = selectedDepartureDate?.trim()
        val arrivalDate = selectedArrivalDate?.trim()
        val destination = selectedDestination?.trim()
        val reason = selectedReason?.trim()
        val dorm = selectedDorm?.trim()
        val studentNumber = selectedStudentNumber?.trim()

        // Validate inputs
        if (pass.isNullOrEmpty() || departureDate.isNullOrEmpty() || arrivalDate.isNullOrEmpty() ||
            destination.isNullOrEmpty() || reason.isNullOrEmpty() || dorm.isNullOrEmpty() || studentNumber.isNullOrEmpty()
        ) {

            Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT)
                .show()
            return  // Stop execution if any field is empty
        }

        // Prepare data for Firestore
        val requestData = hashMapOf(
            "userId" to userId,
            "name" to displayName,
            "type" to "pass",
            "dorm" to dorm,
            "pass" to pass,
            "departureDate" to departureDate,
            "arrivalDate" to arrivalDate,
            "destination" to destination,
            "reason" to reason,
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}