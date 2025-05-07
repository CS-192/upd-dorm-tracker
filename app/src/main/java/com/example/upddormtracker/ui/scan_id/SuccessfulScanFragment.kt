package com.example.upddormtracker.ui.scan_id

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R

class SuccessfulScanFragment : Fragment(R.layout.fragment_successful_scan) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the passed RFID from arguments
        val rfid = arguments?.getString("rfid") ?: "Unknown"

        // Set the RFID to the TextView
        val rfidTextView = view.findViewById<TextView>(R.id.rfid_text)
        rfidTextView.text = "RFID Number: $rfid"

        // Set up the scan again button
        val scanAgainButton = view.findViewById<Button>(R.id.scan_again_button)
        scanAgainButton.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.scanDormerIdFragment)
        }
    }
}
