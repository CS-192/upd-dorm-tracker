package com.example.upddormtracker.ui.admin_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.upddormtracker.databinding.FragmentAdminProfileBinding

class   AdminProfileFragment : Fragment() {

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
            ViewModelProvider(this).get(AdminProfileViewModel::class.java)

        _binding = FragmentAdminProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAdminProfile
        adminProfileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}