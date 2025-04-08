package com.example.upddormtracker.ui.add_announcement

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
import com.example.upddormtracker.databinding.FragmentCreateAnnouncementBinding
import com.example.upddormtracker.datamodel.Announcement
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class CreateAnnouncementFragment : Fragment() {

    private val firestore = Firebase.firestore
    private var _binding: FragmentCreateAnnouncementBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val createAnnouncementViewModel =
            ViewModelProvider(this)[CreateAnnouncementViewModel::class.java]

        _binding = FragmentCreateAnnouncementBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }


    private fun addAnnouncement(announcement: Announcement){
            // Add document with auto-generated ID
        firestore.collection("announcements")
            .add(announcement)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Announcement has been successfully posted!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), "Error adding announcement", Toast.LENGTH_SHORT).show()

            }


    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[CreateAnnouncementViewModel::class.java]

        // Access buttons using View Binding
        val saveButton: Button = binding.saveAnnouncementButton
        val cancelButton: Button = binding.cancelAnnouncementButton
        val inputSubject: com.google.android.material.textfield.TextInputEditText = binding.inputSubject
        val inputDetails: com.google.android.material.textfield.TextInputEditText = binding.inputDetails
        val inputSubjectLayout: com.google.android.material.textfield.TextInputLayout = binding.inputSubjectLayout
        val inputDetailsLayout: com.google.android.material.textfield.TextInputLayout = binding.inputDetailsLayout

        inputSubject.doAfterTextChanged { text ->
            viewModel.validateSubject(text.toString().trim())
        }

        inputDetails.doAfterTextChanged { text ->
            viewModel.validateDetails(text.toString().trim())
        }

        viewModel.subjectError.observe(viewLifecycleOwner) { error ->
            inputSubjectLayout.error = error
        }

        viewModel.detailsError.observe(viewLifecycleOwner) { error ->
            inputDetailsLayout.error = error
        }


        // Handle save button click
        saveButton.setOnClickListener {
            viewModel.validateSubject(inputSubject.text.toString().trim())
            viewModel.validateDetails(inputDetails.text.toString().trim())
            if (viewModel.isFormValid()) {
                // Proceed with form submission
                val subject = inputSubject.text.toString().trim()
                val details = inputDetails.text.toString().trim()
                val item = Announcement(
                    "",
                    subject,
                    details,
                    LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    LocalTime.now().toString(),
                    "Molave"
                )
                addAnnouncement(item)
//                Toast.makeText(requireContext(), "Announcement has been successfully posted!", Toast.LENGTH_SHORT).show()
//                findNavController().navigateUp()
            }


        }

        cancelButton.setOnClickListener {
            // Handle cancel button click
            findNavController().navigateUp()
        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}