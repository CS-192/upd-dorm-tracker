package com.example.upddormtracker.ui.dormer_profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upddormtracker.UserViewModel
import com.example.upddormtracker.adapter.RequestTrackerAdapter
import com.example.upddormtracker.databinding.FragmentDormerProfileBinding
import com.example.upddormtracker.datamodel.Request
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

class DormerProfileFragment : Fragment() {

    private var _binding: FragmentDormerProfileBinding? = null
    private var firestore = Firebase.firestore
    private var requestList = mutableListOf<Request>()
    private lateinit var requestAdapter: RequestTrackerAdapter
    private lateinit var user: UserViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        _binding = FragmentDormerProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root



        binding.name.text = "${user.name.value}"
        binding.email.text = "Email: " + "${user.email.value}"
        binding.dorm.text = "Dorm: " +  "${user.dorm.value}".uppercase()
        val isAdminL = user.isAdmin.value?: false
        if (isAdminL) {
            binding.role.text = "Role: ADMIN"
        } else {
            binding.role.text = "Role: DORMER"
        }

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestAdapter = RequestTrackerAdapter(requestList, firestore)
        binding.requestRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = requestAdapter
        }
        fetchRequests()
    }

    private fun fetchRequests() {
        firestore.collection("requests")
            .whereEqualTo("name", user.name.value.toString())
            .get()
            .addOnSuccessListener { result ->
                requestList.clear()

                for (document in result) {
                    val timestamp = document.getTimestamp("timestamp") ?: run{
                        Log.d("Firestore", "Timestamp is null")
                        Timestamp.now()
                    }
                    val dateOnly = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(timestamp.toDate())
                    val request = Request(
                        document.id,
                        dateOnly.toString().trim(),
                        document.getString("type") ?: "",
                        document.getString("dorm") ?: "",
                        document.getString("name") ?: "",

                        )
                    requestList.add(request)
                }
                // sort
                requestList.sortWith(compareByDescending { it.timestamp })
                // Notify adapter of data change
                requestAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error fetching requests", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}