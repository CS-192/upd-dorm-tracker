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
        val firstName = arguments?.getString("first_name") ?: "Unknown"
        val lastName = arguments?.getString("last_name") ?: "Unknown"
        val studentId = arguments?.getString("student_id") ?: "Unknown"

        val nameTextView = view.findViewById<TextView>(R.id.dormerName)
        val studentIdTextView = view.findViewById<TextView>(R.id.studentId)

        nameTextView.text = "Dormer Name: $firstName $lastName"
        studentIdTextView.text = "Student ID: $studentId"

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
