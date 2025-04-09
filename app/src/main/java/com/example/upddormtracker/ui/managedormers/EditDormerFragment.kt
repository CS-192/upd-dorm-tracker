package com.example.upddormtracker.ui.managedormers

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentEditDormerBinding
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class EditDormerFragment : Fragment() {
    private var _binding: FragmentEditDormerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDormerBinding.inflate(inflater, container, false)

        // Navigate to Update Details page
        binding.btnUpdateDetails.setOnClickListener {
            findNavController().navigate(R.id.action_editDormerFragment_to_updateDormerDetailsFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dormer = arguments?.getSerializable("dormer") as? Dormer

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val updatedDormer = bundle.getSerializable("updatedDormer") as? Dormer

            // If there's an updated dormer, update the UI with the new details
            updatedDormer?.let {
                binding.tvStudentNumber.text = "Student Number: ${it.studentNumber}"
                binding.tvName.text = "Name: ${it.firstName} ${it.lastName}"
                binding.tvDorm.text = "Dorm: ${it.dorm}"
                binding.tvSex.text = "Sex: ${if (it.sex == "M") "Male" else "Female"}"
                binding.tvBirthDate.text = "Birthday: ${it.birthday}"
                binding.tvPhoneNumber.text = "Phone Number: ${it.phoneNumber}"
            }
        }

        dormer?.let {
            binding.tvStudentNumber.text = "Student Number: ${it.studentNumber}"
            binding.tvName.text = "Name: ${it.firstName} ${it.lastName}"
            binding.tvDorm.text = "Dorm: ${it.dorm}"
            binding.tvSex.text = "Sex: ${if (it.sex == "M") "Male" else "Female"}"
            binding.tvBirthDate.text = "Birthday: ${it.birthday}"
            binding.tvPhoneNumber.text = "Phone Number: ${it.phoneNumber}"
        }

        binding.btnRemoveDormer.setOnClickListener {
            dormer?.let {
                val db = FirebaseFirestore.getInstance()

                // Show a confirmation dialog before deleting
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Dormer")
                    .setMessage("Are you sure you want to delete ${it.firstName} ${it.lastName}?")
                    .setPositiveButton("Yes") { _, _ ->
                        // Delete the dormer from Firestore
                        db.collection("dormers").document(it.docId)
                            .delete()
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Dormer deleted successfully", Toast.LENGTH_SHORT).show()
                                // Optionally, navigate back or show updated list
                                findNavController().popBackStack()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(requireContext(), "Failed to delete dormer: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }

        binding.btnUpdateDetails.setOnClickListener {
            dormer?.let {
                val bundle = Bundle()
                bundle.putSerializable("dormer", it) // Pass the Dormer object

                // Navigate to the editing fragment
                findNavController().navigate(R.id.updateDormerDetailsFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}