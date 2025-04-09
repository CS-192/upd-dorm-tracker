package com.example.upddormtracker.ui.managedormers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.databinding.FragmentUpdateDormerDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class UpdateDormerDetailsFragment : Fragment() {
    private var _binding: FragmentUpdateDormerDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateDormerDetailsBinding.inflate(inflater, container, false)

        // Cancel button returns to previous fragment with a Snackbar
        binding.btnCancel.setOnClickListener {
            Snackbar.make(binding.root, "Cancelled changes", Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        // Save changes button shows a Snackbar and navigates back
        binding.btnSaveChanges.setOnClickListener {
            Snackbar.make(binding.root, "Successfully saved changes", Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dormer = arguments?.getSerializable("dormer") as? Dormer

        dormer?.let {
            // Populate the fields with the dormer's data
            binding.studentNumberInput.setText(it.studentNumber)
            binding.firstNameInput.setText(it.firstName)
            binding.lastNameInput.setText(it.lastName)
            binding.middleInitialInput.setText(it.middleName)
            binding.birthDateInput.setText(it.birthday)
            binding.phoneNumberInput.setText(it.phoneNumber)
            binding.dormInput.setText(it.dorm)
            binding.sexInput.setText(it.sex)
            binding.roomNumberInput.setText(it.roomNumber)
            binding.emailInput.setText(it.email)
        }

        // Handle the Save button click
        binding.btnSaveChanges.setOnClickListener {
            val updatedDormer = dormer?.let { it1 ->
                Dormer(
                    studentNumber = binding.studentNumberInput.text.toString(),
                    firstName = binding.firstNameInput.text.toString(),
                    lastName = binding.lastNameInput.text.toString(),
                    middleName = binding.middleInitialInput.text.toString(),
                    birthday = binding.birthDateInput.text.toString(),
                    phoneNumber = binding.phoneNumberInput.text.toString(),
                    dorm = binding.dormInput.text.toString(),
                    sex = binding.sexInput.text.toString(),
                    roomNumber = binding.roomNumberInput.text.toString(),
                    email = binding.emailInput.text.toString(),
                    docId = it1.docId // Retain the docId for Firestore update
                )
            }

            // Call a function to update the dormer in Firestore (not shown here)
            updateDormerInFirestore(updatedDormer)
        }
    }

    private fun updateDormerInFirestore(updatedDormer: Dormer?) {
        val db = FirebaseFirestore.getInstance()

        if (updatedDormer != null) {
            db.collection("dormers").document(updatedDormer.docId)
                .set(updatedDormer)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Dormer updated successfully", Toast.LENGTH_SHORT).show()
                    val resultBundle = Bundle()
                    resultBundle.putSerializable("updatedDormer", updatedDormer)
                    setFragmentResult("requestKey", resultBundle)  // "requestKey" can be any unique string

                    // Go back to previous screen
                    findNavController().popBackStack()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to update dormer: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}