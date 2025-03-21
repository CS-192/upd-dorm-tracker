package com.example.upddormtracker.ui.announcement

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
import com.example.upddormtracker.databinding.FragmentAnnouncementBinding

class AnnouncementFragment : Fragment() {

    private var _binding: FragmentAnnouncementBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val announcementViewModel =
            ViewModelProvider(this).get(AnnouncementViewModel::class.java)

        _binding = FragmentAnnouncementBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAnnouncement
        announcementViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access buttons using View Binding
        val createButton: Button = binding.createAnnouncement


        // Set click listeners for buttons
        createButton.setOnClickListener {
            // Handle "Announcement" button click
            findNavController().navigate(R.id.createAnnouncementFragment)
            println("Announcement Button Clicked!")
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}