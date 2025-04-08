package com.example.upddormtracker.ui.managedormers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentManageDormersBinding

class ManageDormersFragment : Fragment() {

    private var _binding: FragmentManageDormersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val manageDormersViewModel =
            ViewModelProvider(this).get(ManageDormersViewModel::class.java)

        _binding = FragmentManageDormersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Add click listener for Potter's view action
        binding.viewActionPotter.setOnClickListener {
            findNavController().navigate(R.id.action_nav_manage_dormers_to_editDormerFragment)
        }

        binding.addDormerButton.setOnClickListener {
           findNavController().navigate(R.id.addDormerFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}