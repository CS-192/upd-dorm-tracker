package com.example.upddormtracker.ui.manage_requests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentManageRequestsBinding

class ManageRequestsFragment : Fragment() {

    private var _binding: FragmentManageRequestsBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedTab: TextView
    private lateinit var viewModel: ManageRequestsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ManageRequestsViewModel::class.java]
        _binding = FragmentManageRequestsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupTabs()

        // Load initial data
        loadOvernightPassData()

        return root
    }

    private fun setupTabs() {
        // Initialize tabs
        val tabOvernightPass = binding.tabOvernightPass
        val tabMonthlyBill = binding.tabMonthlyBill
        val tabReports = binding.tabReports

        // Set initial selected tab
        selectedTab = tabOvernightPass

        // Set click listeners for each tab
        tabOvernightPass.setOnClickListener {
            updateSelectedTab(tabOvernightPass)
            // Load overnight pass data
            loadOvernightPassData()
        }

        tabMonthlyBill.setOnClickListener {
            updateSelectedTab(tabMonthlyBill)
            // Load monthly bill data
            loadMonthlyBillData()
        }

        tabReports.setOnClickListener {
            updateSelectedTab(tabReports)
            // Load reports data
            loadReportsData()
        }
    }

    private fun updateSelectedTab(newSelectedTab: TextView) {
        // Remove selection from previously selected tab
        selectedTab.setBackgroundResource(0)

        // Apply selection to newly selected tab
        newSelectedTab.setBackgroundResource(R.drawable.tab_selected)

        // Update the selectedTab reference
        selectedTab = newSelectedTab
    }

    private fun loadOvernightPassData() {
        // Clear existing content
        binding.tableContent.removeAllViews()

        // Get data from ViewModel
        val requests = viewModel.getOvernightPassData()

        // Populate the table
        for (request in requests) {
            addRowToTable(request)
        }
    }

    private fun loadMonthlyBillData() {
        // Clear existing content
        binding.tableContent.removeAllViews()

        // Get data from ViewModel
        val requests = viewModel.getMonthlyBillData()

        // Populate the table
        for (request in requests) {
            addRowToTable(request)
        }
    }

    private fun loadReportsData() {
        // Clear existing content
        binding.tableContent.removeAllViews()

        // Get data from ViewModel
        val requests = viewModel.getReportsData()

        // Populate the table
        for (request in requests) {
            addRowToTable(request)
        }
    }

    private fun addRowToTable(request: ManageRequestsViewModel.RequestItem) {
        // Inflate row layout
        val inflater = LayoutInflater.from(requireContext())
        val rowView = inflater.inflate(R.layout.item_request_row, binding.tableContent, false)

        // Set data to views
        rowView.findViewById<TextView>(R.id.text_date).text = request.date
        rowView.findViewById<TextView>(R.id.text_student_number).text = request.studentNumber

        // Set up click listeners for actions
        val detailsEdit = rowView.findViewById<TextView>(R.id.text_details_edit)
        val delete = rowView.findViewById<TextView>(R.id.text_delete)

        detailsEdit.setOnClickListener {
            // Handle details/edit click
            // For now just a placeholder
        }

        delete.setOnClickListener {
            // Handle delete click
            // For now just a placeholder
        }

        // Add row to table
        binding.tableContent.addView(rowView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}