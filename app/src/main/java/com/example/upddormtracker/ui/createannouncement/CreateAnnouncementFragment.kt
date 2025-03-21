package com.example.upddormtracker.ui.createannouncement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.databinding.FragmentCreateAnnouncementBinding

class CreateAnnouncementFragment : Fragment() {

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

        val textView: TextView = binding.textCreateAnnouncement
        createAnnouncementViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }







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
                Toast.makeText(requireContext(), "Announcement posted!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
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