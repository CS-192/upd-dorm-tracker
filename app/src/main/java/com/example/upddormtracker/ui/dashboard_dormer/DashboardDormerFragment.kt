package com.example.upddormtracker.ui.dashboard_dormer

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
import com.example.upddormtracker.UserViewModel
import com.example.upddormtracker.adapter.DormerViewAnnouncementAdapter
import com.example.upddormtracker.adapter.DormerViewFAQAdapter
import com.example.upddormtracker.databinding.FragmentDashboardDormerBinding
import com.example.upddormtracker.datamodel.Announcement
import com.example.upddormtracker.datamodel.FAQ
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardDormerFragment: Fragment() {
    private var _binding: FragmentDashboardDormerBinding? = null
    private val firestore = Firebase.firestore
    private val announcementList = mutableListOf<Announcement>()
    private val faqList = mutableListOf<FAQ>()
    private lateinit var announcementAdapter: DormerViewAnnouncementAdapter
    private lateinit var faqAdapter: DormerViewFAQAdapter
    private lateinit var user: UserViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardDormerViewModel =
            ViewModelProvider(this)[DashboardDormerViewModel::class.java]
         user = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        _binding = FragmentDashboardDormerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardDormerViewModel.text.observe(viewLifecycleOwner) {
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
        buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.lateNightFragment)
        }

        buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.monthlyBillingFragment)
        }

        buttonThird.setOnClickListener {
            findNavController().navigate(R.id.reportFragment)
        }

        buttonFourth.setOnClickListener {
            findNavController().navigate(R.id.dormInfoDormerFragment)
        }

        announcementAdapter = DormerViewAnnouncementAdapter(announcementList, firestore)
        faqAdapter = DormerViewFAQAdapter(faqList, firestore)

        binding.announcementRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = announcementAdapter
        }
        binding.faqRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = faqAdapter
        }

        user.dorm.observe(viewLifecycleOwner) { dormValue ->
            if (!dormValue.isNullOrEmpty()) {
                fetchAnnouncementsAndFAQs()
            }
        }
    }

    private fun fetchAnnouncementsAndFAQs() {
        firestore.collection("announcements")
            .whereEqualTo("dorm", user.dorm.value.toString())
            .get()
            .addOnSuccessListener { result ->
                announcementList.clear()

                // Add new announcements
                for (document in result) {
                    val announcement = Announcement(
                        document.id,
                        document.getString("subject") ?: "",
                        document.getString("details") ?: "",
                        document.getString("date") ?: "",
                        document.getString("time") ?: "",
                        document.getString("dorm") ?: "",

                        )
                    announcementList.add(announcement)
                }
                // sort
                announcementList.sortWith(compareByDescending<Announcement> { it.date }.thenByDescending { it.time })
                // Notify adapter of data change
                announcementAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error fetching announcements", Toast.LENGTH_SHORT).show()
            }
        firestore.collection("faqs")
            .whereEqualTo("dorm", user.dorm.value.toString())
            .get()
            .addOnSuccessListener { result ->
                faqList.clear()

                // Add new announcements
                for (document in result) {
                    val faq = FAQ(
                        document.id,
                        document.getString("question") ?: "",
                        document.getString("answer") ?: "",
                        document.getString("dorm") ?: "",

                        )
                    faqList.add(faq)
                }

                // Notify adapter of data change
                faqAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error fetching FAQ", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
