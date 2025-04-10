package com.example.upddormtracker.ui.scan_ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R

class SuccessfulScanFragment : Fragment(R.layout.fragment_successful_scan) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scanAgainButton = view.findViewById<Button>(R.id.scan_again_button)
        scanAgainButton.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.scanPageFragment)
        }


    }
}
