package com.example.upddormtracker.ui.scan_ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R


class ScanFragment : Fragment(R.layout.fragment_scan_page) {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("ScanPrefs", Context.MODE_PRIVATE)
        val scanButton = view.findViewById<Button>(R.id.scan_now_button)

        scanButton.setOnClickListener {
            val scanCount = sharedPreferences.getInt("scanCount", 0)

            val nextFragment = if (scanCount % 2 == 0) {
                R.id.action_scanFragment_to_successfulScanFragment
            } else {
                R.id.action_scanFragment_to_unsuccessfulScanFragment
            }

            sharedPreferences.edit() { putInt("scanCount", scanCount + 1) }
            findNavController().navigate(nextFragment)
        }
    }
}
