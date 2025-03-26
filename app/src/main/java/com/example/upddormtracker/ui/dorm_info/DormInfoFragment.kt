package com.example.upddormtracker.ui.dorm_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.upddormtracker.databinding.FragmentDormInfoBinding

class DormInfoFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var _binding: FragmentDormInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dormInfoViewModel =
            ViewModelProvider(this)[DormInfoViewModel::class.java]

        _binding = FragmentDormInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDormInfo
        dormInfoViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access buttons using View Binding
        val editButton: Button = binding.editDormInfoButton
        editButton.text = "Edit"

        // Set click listeners for buttons
        editButton.setOnClickListener {
//            // Handle "Announcement" button click
//            findNavController().navigate(R.id.createAnnouncementFragment)
            println("Announcement Button Clicked!")
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}