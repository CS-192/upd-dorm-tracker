package com.example.upddormtracker.ui.managedormers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentManageDormersBinding
import com.google.firebase.firestore.FirebaseFirestore

class ManageDormersFragment : Fragment() {

    private var _binding: FragmentManageDormersBinding? = null
    private val binding get() = _binding!!
    private lateinit var dormerAdapter: DormerAdapter
    private val dormers = mutableListOf<Dormer>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageDormersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter with an empty list
        dormerAdapter = DormerAdapter(dormers)
        binding.rvDormers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dormerAdapter
        }

        // Setup search functionality
        setupSearchView()

        // Setup sorting
        setupSorting()

        // Load dormers from Firestore
        loadDormers()

        // Setup Add Dormer button
        binding.addDormerButton.setOnClickListener {
            findNavController().navigate(R.id.addDormerFragment)
        }
    }

    private fun setupSearchView() {
        binding.searchView.apply {
            // Make sure the search view is not iconified (expanded and ready for input)
            isIconified = false

            // Clear focus initially to avoid keyboard automatically popping up
            clearFocus()

            // Set up the query listener
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Filter the list based on search query
                    dormerAdapter.filter(newText ?: "")
                    return true
                }
            })
        }
    }

    private fun setupSorting() {
        // Room column sorting
        binding.roomColumnHeader.setOnClickListener {
            val sortState = dormerAdapter.sortByRoom()
            updateSortIndicator(binding.roomSortIndicator, sortState)
            binding.nameSortIndicator.visibility = View.GONE
        }

        // Name column sorting
        binding.nameColumnHeader.setOnClickListener {
            val sortState = dormerAdapter.sortByName()
            updateSortIndicator(binding.nameSortIndicator, sortState)
            binding.roomSortIndicator.visibility = View.GONE
        }
    }

    private fun updateSortIndicator(imageView: ImageView, sortState: SortState) {
        when (sortState) {
            SortState.NONE -> {
                imageView.visibility = View.GONE
            }
            SortState.ASCENDING -> {
                imageView.setImageResource(R.drawable.ic_sort_ascending)
                imageView.visibility = View.VISIBLE
            }
            SortState.DESCENDING -> {
                imageView.setImageResource(R.drawable.ic_sort_descending)
                imageView.visibility = View.VISIBLE
            }
        }
    }

    private fun loadDormers() {
        val db = FirebaseFirestore.getInstance()

        // Clear existing data
        dormers.clear()

        db.collection("dormers")
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val dormer = doc.toObject(Dormer::class.java).copy(docId = doc.id)
                    dormers.add(dormer)
                }

                // Update adapter with new data
                dormerAdapter.updateDormers(dormers)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load dormers", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}