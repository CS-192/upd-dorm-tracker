package com.example.upddormtracker.ui.announcement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upddormtracker.R
import com.example.upddormtracker.adapter.AnnouncementAdapter
import com.example.upddormtracker.databinding.FragmentAnnouncementBinding
import com.example.upddormtracker.datamodel.Announcement
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AnnouncementFragment : Fragment() {

    private var _binding: FragmentAnnouncementBinding? = null
    private val binding get() = _binding!!
    private val firestore = Firebase.firestore
    private val announcementList = mutableListOf<Announcement>()
    private lateinit var announcementAdapter: AnnouncementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAnnouncementBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        announcementAdapter = AnnouncementAdapter(announcementList, firestore){ id: String ->
            val action = AnnouncementFragmentDirections.actionAnnouncementFragmentToEditAnnouncementFragment(id)
            findNavController().navigate(action)
        }

        // Setup RecyclerView
        binding.announcementRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = announcementAdapter
        }

        // Fetch announcements from Firestore
        fetchAnnouncements()

        // Handle create button click
        binding.createAnnouncement.setOnClickListener {
            findNavController().navigate(R.id.createAnnouncementFragment)
        }
    }

    private fun fetchAnnouncements() {
        firestore.collection("announcements")
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

                // Notify adapter of data change
                announcementAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error fetching announcements", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}