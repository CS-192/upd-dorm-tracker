package com.example.upddormtracker.ui.scan_ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R

class UnsuccessfulScanFragment : Fragment(R.layout.fragment_unsuccessful_scan) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tryAgainButton = view.findViewById<Button>(R.id.try_again)
        tryAgainButton.setOnClickListener {
            findNavController().navigate(R.id.action_unsuccessfulScanFragment_to_scanFragment)
        }
    }
}
