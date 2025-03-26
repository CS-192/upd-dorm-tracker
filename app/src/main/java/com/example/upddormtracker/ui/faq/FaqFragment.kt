package com.example.upddormtracker.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upddormtracker.R
import com.example.upddormtracker.adapter.FAQAdapter
import com.example.upddormtracker.databinding.FragmentFaqBinding
import com.example.upddormtracker.datamodel.FAQ

class FaqFragment: Fragment() {
    private val binding get() = _binding!!

    private var _binding: FragmentFaqBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val faqViewModel =
            ViewModelProvider(this).get(FaqViewModel::class.java)

        _binding = FragmentFaqBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFaq
        faqViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access buttons using View Binding
        val createButton: Button = binding.createFaq

        val faqList = listOf(
            FAQ("Meeting", "Dorm meeting at 6 PM", "Molave"),
            FAQ("Meeting", "Dorm meeting at 6 PM", "Molave"),
            FAQ("Meeting", "Dorm meeting at 6 PM", "Molave"),
            FAQ("Meeting", "Dorm meeting at 6 PM", "Molave"),

        )

        // Initialize RecyclerView
        binding.faqRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.faqRecyclerView.adapter = FAQAdapter(faqList)


        // Set click listeners for buttons
        createButton.setOnClickListener {
            // Handle "Announcement" button click
            findNavController().navigate(R.id.addFaqFragment)
            println("Create Button Clicked!")
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}