package com.example.upddormtracker.ui.scan_upid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.upddormtracker.databinding.FragmentScanUpIdBinding

class ScanUpIdFragment : Fragment() {

    private var _binding: FragmentScanUpIdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scanUpIdViewModel =
            ViewModelProvider(this)[ScanUpIdViewModel::class.java]

        _binding = FragmentScanUpIdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textScanUpId
        scanUpIdViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}