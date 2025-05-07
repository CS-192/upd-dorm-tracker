package com.example.upddormtracker.ui.admin_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.upddormtracker.UserViewModel
import com.example.upddormtracker.databinding.FragmentAdminProfileBinding

class AdminProfileFragment : Fragment() {

    private var _binding: FragmentAdminProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adminProfileViewModel =
            ViewModelProvider(this)[AdminProfileViewModel::class.java]
        val userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        _binding = FragmentAdminProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAdminProfile
        adminProfileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.name.text = "${userViewModel.name.value}"
        binding.email.text = "${binding.email.text}" + "${userViewModel.email.value}"
        binding.dorm.text = "${binding.dorm.text}" +  "${userViewModel.dorm.value}".uppercase()
        val isAdminL = userViewModel.isAdmin.value?: false
        if (isAdminL) {
            binding.role.text = "${binding.role.text} ADMIN"
        } else {
            binding.role.text = "${binding.role.text} DORMER"
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}