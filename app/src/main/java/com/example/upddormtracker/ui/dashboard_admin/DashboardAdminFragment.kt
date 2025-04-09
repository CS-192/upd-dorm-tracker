package com.example.upddormtracker.ui.dashboard_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentDashboardAdminBinding


class DashboardAdminFragment : Fragment() {

    private var _binding: FragmentDashboardAdminBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardAdminViewModel =
            ViewModelProvider(this)[DashboardAdminViewModel::class.java]

        _binding = FragmentDashboardAdminBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardAdminViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access buttons using View Binding
        val buttonFirst: Button = binding.buttonFirst
        val buttonSecond: Button = binding.buttonSecond
        val buttonThird: Button = binding.buttonThird
        val buttonFourth: Button = binding.buttonFourth

        // Set click listeners for buttons
        buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.dormDetailsFragment)
        }

        buttonSecond.setOnClickListener {
            // Requests button
        }

        buttonThird.setOnClickListener {
            findNavController().navigate(R.id.nav_manage_dormers)
        }

        buttonFourth.setOnClickListener {
            //scan id button
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}