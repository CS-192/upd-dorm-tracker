package com.example.upddormtracker.ui.managedormers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upddormtracker.R
import com.example.upddormtracker.databinding.FragmentManageDormersBinding
import com.google.firebase.firestore.FirebaseFirestore

class ManageDormersFragment : Fragment() {

    private var _binding: FragmentManageDormersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageDormersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Add click listener for Potter's view action

        binding.addDormerButton.setOnClickListener {
            findNavController().navigate(R.id.addDormerFragment)
        }

        binding.rvDormers.layoutManager = LinearLayoutManager(requireContext())
        loadDormers()

        return root
    }

    private fun loadDormers() {
        val db = FirebaseFirestore.getInstance()
        val dormerList = mutableListOf<Dormer>()

        db.collection("dormers")
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val dormer = doc.toObject(Dormer::class.java).copy(docId = doc.id)
                    dormerList.add(dormer)
                }
                binding.rvDormers.adapter = DormerAdapter(dormerList)
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