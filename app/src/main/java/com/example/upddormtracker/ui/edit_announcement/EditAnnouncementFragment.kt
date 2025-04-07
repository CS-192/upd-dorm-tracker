package com.example.upddormtracker.ui.edit_announcement

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.databinding.FragmentEditAnnouncementBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class EditAnnouncementFragment: Fragment() {
    private val firestore = Firebase.firestore
    private var _binding: FragmentEditAnnouncementBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val editAnnouncementViewModel =
            ViewModelProvider(this)[EditAnnouncementViewModel::class.java]

        _binding = FragmentEditAnnouncementBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }


    private fun editAnnouncement(id: String, itemToBeEdited: Map<String, String>) {
        // Add document with auto-generated ID
        firestore.collection("announcements")
            .document(id)
            .update(itemToBeEdited)
            .addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Announcement has been successfully edited!",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error editing announcement", Toast.LENGTH_SHORT)
                    .show()

            }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[EditAnnouncementViewModel::class.java]

        // Access buttons using View Binding
        val saveButton: Button = binding.saveAnnouncementButton
        val cancelButton: Button = binding.cancelAnnouncementButton
        val inputSubject: com.google.android.material.textfield.TextInputEditText =
            binding.inputSubject
        val inputDetails: com.google.android.material.textfield.TextInputEditText =
            binding.inputDetails
        val inputSubjectLayout: com.google.android.material.textfield.TextInputLayout =
            binding.inputSubjectLayout
        val inputDetailsLayout: com.google.android.material.textfield.TextInputLayout =
            binding.inputDetailsLayout

        val args = EditAnnouncementFragmentArgs.fromBundle(requireArguments())
        val id = args.id

        firestore.collection("announcements")
            .document(id)
            .get()
            .addOnSuccessListener { result ->
                inputSubject.setText(result.getString("subject"))
                inputDetails.setText(result.getString("details"))



                inputSubject.doAfterTextChanged { text ->
                    viewModel.validateSubject(text.toString())
                }

                inputDetails.doAfterTextChanged { text ->
                    viewModel.validateDetails(text.toString())
                }

                viewModel.subjectError.observe(viewLifecycleOwner) { error ->
                    inputSubjectLayout.error = error
                }

                viewModel.detailsError.observe(viewLifecycleOwner) { error ->
                    inputDetailsLayout.error = error
                }


                // Handle save button click
                saveButton.setOnClickListener {
                    viewModel.validateSubject(inputSubject.text.toString())
                    viewModel.validateDetails(inputDetails.text.toString())
                    if (viewModel.isFormValid()) {
                        // Proceed with form submission
                        val subject = inputSubject.text.toString()
                        val details = inputDetails.text.toString()
                        val itemToBeEdited = mapOf(
                            "subject" to subject,
                            "details" to details,
                            "date" to LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                            "time" to LocalTime.now().toString(),
                        )
                        editAnnouncement(id, itemToBeEdited)
//                Toast.makeText(requireContext(), "Announcement has been successfully posted!", Toast.LENGTH_SHORT).show()
//                findNavController().navigateUp()
                    }


                }

                cancelButton.setOnClickListener {
                    // Handle cancel button click
                    findNavController().navigateUp()
                }
            }
    }
}