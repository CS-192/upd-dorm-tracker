package com.example.upddormtracker.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upddormtracker.R
import com.example.upddormtracker.adapter.FAQAdapter
import com.example.upddormtracker.databinding.FragmentFaqBinding
import com.example.upddormtracker.datamodel.FAQ
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FaqFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentFaqBinding? = null
    private val firestore = Firebase.firestore
    private val faqList = mutableListOf<FAQ>()
    private lateinit var faqAdapter: FAQAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val faqViewModel =
            ViewModelProvider(this)[FaqViewModel::class.java]

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
        faqAdapter = FAQAdapter(faqList)

        // Access buttons using View Binding
        val createButton: Button = binding.createFaq

        binding.faqRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = faqAdapter
        }

        fetchFAQs()

        // Set click listeners for buttons
        createButton.setOnClickListener {
            // Handle "Announcement" button click
            findNavController().navigate(R.id.addFaqFragment)
            println("Create Button Clicked!")
        }
    }

    private fun fetchFAQs() {
        firestore.collection("faqs")
            .get()
            .addOnSuccessListener { result ->
                faqList.clear()

                // Add new announcements
                for (document in result) {
                    val faq = FAQ(
                        document.getString("question") ?: "",
                        document.getString("answer") ?: "",
                        document.getString("dorm") ?: ""
                    )
                    faqList.add(faq)
                }

                // Notify adapter of data change
                faqAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error fetching FAQs", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}