package com.example.upddormtracker.ui.managedormers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentEditDormerBinding

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}