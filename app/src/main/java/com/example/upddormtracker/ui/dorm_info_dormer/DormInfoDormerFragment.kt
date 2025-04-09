package com.example.upddormtracker.ui.dorm_info_dormer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.upddormtracker.databinding.FragmentDormInfoDormerBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DormInfoDormerFragment : Fragment() {

    private val binding get() = _binding!!

    private var _binding: FragmentDormInfoDormerBinding? = null
    private val firestore = Firebase.firestore

    private lateinit var dormName: TextView
    private lateinit var curfew: TextView
    private lateinit var address: TextView
    private lateinit var contactDetails: TextView
    private lateinit var history: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dormInfoViewModel =
            ViewModelProvider(this)[DormInfoDormerViewModel::class.java]

        _binding = FragmentDormInfoDormerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dormName = binding.dormName
        curfew = binding.curfew
        address = binding.address
        contactDetails = binding.contactDetails
        history = binding.history

        fetchInfoOfGivenDorm("molave") // replace with user dorm
    }

    private fun fetchInfoOfGivenDorm(dormValue: String) {
        firestore.collection("dorm-info")
            .whereEqualTo("dorm", dormValue)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Get the first matching document
                    val document = documents.documents[0]

                    dormName.text = (document.getString("dorm")?: "").uppercase()
                    curfew.text = document.getString("curfew") ?: ""
                    address.text = document.getString("address") ?: ""
                    contactDetails.text = document.getString("email") ?: ""
                    history.text = document.getString("history") ?: ""
                } else {
                    Toast.makeText(requireContext(), "No information found for your dorm", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Error getting dorm info", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}