package com.example.upddormtracker.ui.managedormers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentUpdateDormerDetailsBinding
import com.google.android.material.snackbar.Snackbar

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}