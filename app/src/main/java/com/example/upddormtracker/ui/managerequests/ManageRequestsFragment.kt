package com.example.upddormtracker.ui.managerequests

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import com.example.upddormtracker.UserViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ManageRequestsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManageRequestsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RequestAdapter
    private val db = Firebase.firestore
    private val allRequests = mutableListOf<Request>()
    lateinit var tvLateNight: TextView
    lateinit var tvMonthlyBill: TextView
    lateinit var tvReports: TextView
    lateinit var activeFilterTextView: TextView
    private val userViewModel: UserViewModel by activityViewModels()
    private var userDorm: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_manage_requests, container, false)
        recyclerView = view.findViewById(R.id.rvRequests)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RequestAdapter(allRequests)
        recyclerView.adapter = adapter
        tvLateNight = view.findViewById(R.id.tvLateNight)
        tvMonthlyBill = view.findViewById(R.id.tvMonthlyBill)
        tvReports = view.findViewById(R.id.tvReports)

        userViewModel.dorm.observe(viewLifecycleOwner) {
            userDorm = it
            fetchRequests(it)
            filter("pass", tvLateNight)
        }

        tvLateNight.setOnClickListener { filter("pass", tvLateNight) }
        tvMonthlyBill.setOnClickListener { filter("billing", tvMonthlyBill) }
        tvReports.setOnClickListener { filter("report", tvReports) }

        return view
    }

    private fun fetchRequests(dormName: String) {
        Log.d("Check", dormName)
        db.collection("requests")
            .whereEqualTo("dorm", dormName) // Filter by dorm
            .orderBy("timestamp", Query.Direction.DESCENDING) // Sort by latest
            .get()
            .addOnSuccessListener { result ->
                val requestList = mutableListOf<Request>()
                for (document in result) {
                    val fullName = document.getString("name") ?: ""
                    val timestamp = document.getTimestamp("timestamp")
                    val type = document.getString("type") ?: ""

                    val dateFormatted = timestamp?.toDate()?.let { date ->
                        SimpleDateFormat("mm/dd", Locale.getDefault()).format(date)
                    } ?: "N/A"

                    requestList.add(Request(fullName, dateFormatted, type))
                }

                allRequests.clear()
                allRequests.addAll(requestList)
                adapter.updateList(allRequests.filter { it.type == "pass" })
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to fetch requests", Toast.LENGTH_SHORT)
                    .show()
            }
    }


    private fun filter(type: String, selectedTextView: TextView) {
        val filtered = allRequests.filter { it.type == type }
        adapter.updateList(filtered)

        // Reset previous selection (if exists)
        if (::activeFilterTextView.isInitialized) {
            activeFilterTextView.setTypeface(null, Typeface.NORMAL)
            activeFilterTextView.setBackgroundTintList(null) // Reset background tint
            activeFilterTextView.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Small)
        }

        // Apply styles to selected filter
        selectedTextView.setTypeface(null, Typeface.BOLD)
        selectedTextView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F6A0A0"))) // Light blue or any color
        selectedTextView.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body1)

        activeFilterTextView = selectedTextView
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ManageRequestsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ManageRequestsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}