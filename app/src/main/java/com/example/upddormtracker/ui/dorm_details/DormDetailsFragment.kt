package com.example.upddormtracker.ui.dorm_details

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
import com.example.upddormtracker.databinding.FragmentDormDetailsBinding



class DormDetailsFragment : Fragment() {

    private var _binding: FragmentDormDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dormDetailsViewModel =
            ViewModelProvider(this).get(DormDetailsViewModel::class.java)

        _binding = FragmentDormDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        dormDetailsViewModel.text.observe(viewLifecycleOwner) {
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

        // Handle "Announcement" button click

        buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.announcementFragment)
        }


        buttonSecond.setOnClickListener {
            // Handle "FAQ" button click

            println("FAQ Button Clicked!")
        }

        buttonThird.setOnClickListener {
            // Handle "Dorm Information" button click
            println("Dorm Information Button Clicked!")
        }

        buttonFourth.setOnClickListener {
            findNavController().navigate(R.id.adminProfileFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}