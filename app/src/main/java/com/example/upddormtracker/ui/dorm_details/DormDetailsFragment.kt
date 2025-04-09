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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DormDetailsFragment : Fragment() {

    private var _binding: FragmentDormDetailsBinding? = null
    private val firestore = Firebase.firestore

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dormDetailsViewModel =
            ViewModelProvider(this)[DormDetailsViewModel::class.java]

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

        // Set click listeners for buttons

            // Handle "Announcement" button click

        buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.announcementFragment)
        }


        buttonSecond.setOnClickListener {
            // Handle "FAQ" button click
            findNavController().navigate(R.id.faqFragment)
            println("FAQ Button Clicked!")
        }

        buttonThird.setOnClickListener {
            // Handle "Dorm Information" button click
            findNavController().navigate(R.id.dormInfoFragment)
            println("Dorm Information Button Clicked!")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}