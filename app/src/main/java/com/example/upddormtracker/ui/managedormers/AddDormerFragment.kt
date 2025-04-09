package com.example.upddormtracker.ui.managedormers

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R
import com.example.upddormtracker.UserViewModel
import com.example.upddormtracker.databinding.FragmentAddDormerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.reflect.KMutableProperty0

class AddDormerFragment : Fragment() {

    private var _binding: FragmentAddDormerBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    private var selectedFirstName: String? = null
    private var selectedLastName: String? = null
    private var selectedMiddleName: String? = null
    private var selectedBirthday: String? = null
    private var selectedSex: String? = null
    private var selectedStudentNumber: String? = null
    private var selectedEmail: String? = null
    private var selectedPhoneNumber: String? = null
    private var selectedRoomNumber: String? = null
    private var selectedDorm: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addDormerViewModel =
            ViewModelProvider(this).get(AddDormerViewModel::class.java)

        _binding = FragmentAddDormerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Watchers for each EditText
        binding.etFirstName.addTextChangedListener(makeTextWatcher { selectedFirstName = it })
        binding.etLastName.addTextChangedListener(makeTextWatcher { selectedLastName = it })
        binding.etMiddleName.addTextChangedListener(makeTextWatcher { selectedMiddleName = it })
        binding.etStudentNumber.addTextChangedListener(makeTextWatcher {
            selectedStudentNumber = it
        })
        binding.etEmail.addTextChangedListener(makeTextWatcher { selectedEmail = it })
        binding.etPhoneNumber.addTextChangedListener(makeTextWatcher { selectedPhoneNumber = it })
        binding.etRoomNumber.addTextChangedListener(makeTextWatcher { selectedRoomNumber = it })

        val spinner = binding.etSex
        val sex = listOf("Select Sex", "Male", "Female")
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            sex
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0 // Disable the first item
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(resources.getColor(R.color.gray))
                } else {
                    tv.setTextColor(resources.getColor(R.color.black))
                }
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSex = if (position == 0) null else sex[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedSex = null
            }
        }

        binding.etBirthday.setOnClickListener {
            showDatePicker(binding.etBirthday, ::selectedBirthday)
        }

        // Observe dorm
        userViewModel.dorm.observe(viewLifecycleOwner) {
            selectedDorm = it
        }

        // Submit button
        binding.btnSubmit.setOnClickListener {
            saveDormerToFirestore()
        }
    }

    private fun makeTextWatcher(update: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                update(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
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
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun saveDormerToFirestore() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val studentNumber = selectedStudentNumber?.trim()
        val lastName = selectedLastName?.trim()
        val firstName = selectedFirstName?.trim()
        val middleName = selectedMiddleName?.trim()
        val birthday = selectedBirthday?.trim()
        val sex = selectedSex?.trim()
        val email = selectedEmail?.trim()
        val phoneNumber = selectedPhoneNumber?.trim()
        val roomNumber = selectedRoomNumber?.trim()
        val dorm = selectedDorm?.trim()

        // Validate inputs first
        if (studentNumber.isNullOrEmpty() || lastName.isNullOrEmpty() || firstName.isNullOrEmpty()
            || middleName.isNullOrEmpty() || birthday.isNullOrEmpty() || sex.isNullOrEmpty()
            || email.isNullOrEmpty() || phoneNumber.isNullOrEmpty() || roomNumber.isNullOrEmpty()
            || dorm.isNullOrEmpty()
        ) {
            Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        binding.btnSubmit.apply {
            isEnabled = false
            alpha = 0.5f
        }

        // 🔍 Check if dormer already exists
        checkIfDormerExists(studentNumber, email) { exists ->
            if (exists) {
                Toast.makeText(requireContext(), "Dormer already exists!", Toast.LENGTH_SHORT)
                    .show()
                binding.btnSubmit.apply {
                    isEnabled = true
                    alpha = 1f
                }
            } else {
                // 📝 Save to Firestore if not existing
                val dormerData = hashMapOf(
                    "studentNumber" to studentNumber,
                    "lastName" to lastName,
                    "firstName" to firstName,
                    "middleName" to middleName,
                    "birthday" to birthday,
                    "sex" to sex,
                    "email" to email,
                    "phoneNumber" to phoneNumber,
                    "roomNumber" to roomNumber,
                    "dorm" to dorm,
                    "timestamp" to FieldValue.serverTimestamp()
                )

                val db = FirebaseFirestore.getInstance()


                db.collection("dormers")
                    .add(dormerData)
                    .addOnSuccessListener {
                        updateUserDormByEmail(email, dorm)
                        Toast.makeText(
                            requireContext(),
                            "Dormer added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigateUp()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            requireContext(),
                            "Failed to add dormer: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.btnSubmit.apply {
                            isEnabled = true
                            alpha = 1f
                        }
                    }
            }
        }
    }

    private fun checkIfDormerExists(
        studentNumber: String?,
        email: String?,
        onResult: (exists: Boolean) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection("dormers")
            .whereEqualTo("studentNumber", studentNumber)
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                onResult(!documents.isEmpty)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error checking dormer existence", e)
                Toast.makeText(
                    requireContext(),
                    "Failed to check dormer: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                onResult(false) // Treat as not existing on failure
            }
    }

    private fun updateUserDormByEmail(email: String, dorm: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    for (document in querySnapshot.documents) {
                        val docRef = db.collection("users").document(document.id)
                        docRef.update("dorm", dorm)
                            .addOnSuccessListener {
                                Log.d("Firestore", "User dorm updated successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Failed to update user dorm", e)
                            }
                    }
                } else {
                    Log.d("Firestore", "No user found with email: $email")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error retrieving user", e)
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
