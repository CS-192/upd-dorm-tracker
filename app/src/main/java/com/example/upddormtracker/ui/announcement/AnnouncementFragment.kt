package com.example.upddormtracker.ui.announcement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upddormtracker.R
import com.example.upddormtracker.adapter.AnnouncementAdapter
import com.example.upddormtracker.databinding.FragmentAnnouncementBinding
import com.example.upddormtracker.datamodel.Announcement

class AnnouncementFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentAnnouncementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val announcementViewModel =
            ViewModelProvider(this)[AnnouncementViewModel::class.java]

        _binding = FragmentAnnouncementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sample announcement list (Replace with Firestore data later)
        val announcementList = listOf(
            Announcement("Meeting", "Dorm meeting at 6 PM", "2025-03-24", "1:30AM", "Molave"),
            Announcement("Fire Drill", "Mandatory fire drill at 10 AM", "2025-03-25", "2:30PM", "Acacia"),
            Announcement("Maintenance", "Water supply disruption", "2025-03-26", "5:02AM", "Acacia")
        )



        // Initialize RecyclerView
        binding.announcementRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.announcementRecyclerView.adapter = AnnouncementAdapter(
            announcementList
        )

        // Handle button click
        binding.createAnnouncement.setOnClickListener {
            findNavController().navigate(R.id.createAnnouncementFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}