package com.example.upddormtracker.ui.managedormers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import java.util.Locale

class DormerAdapter(private var dormers: List<Dormer>) :
    RecyclerView.Adapter<DormerAdapter.DormerViewHolder>() {

    // Keep a copy of the original list for filtering and sorting
    private var allDormers: List<Dormer> = dormers.toList()

    // Search query for filtering
    private var currentQuery: String = ""

    // Sorting properties
    private var sortColumn: SortColumn = SortColumn.NONE
    private var roomSortState: SortState = SortState.NONE
    private var nameSortState: SortState = SortState.NONE

    enum class SortColumn {
        NONE, ROOM, NAME
    }

    class DormerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val btnViewDormer: TextView = itemView.findViewById(R.id.btnViewDormer)
        val tvRoomNumber: TextView = itemView.findViewById(R.id.tvRoomNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DormerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dormer, parent, false)
        return DormerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DormerViewHolder, position: Int) {
        val dormer = dormers[position]
        holder.tvName.text = "${dormer.firstName} ${dormer.lastName}"
        holder.tvRoomNumber.text = dormer.roomNumber
        holder.btnViewDormer.setOnClickListener{
            val bundle = Bundle().apply {
                putSerializable("dormer", dormer)
            }
            it.findNavController().navigate(R.id.editDormerFragment, bundle)
        }
    }

    override fun getItemCount() = dormers.size

    // Method to filter the list based on search query
    fun filter(query: String) {
        currentQuery = query
        applyFiltersAndSort()
    }

    // Method to sort by room number
    fun sortByRoom(): SortState {
        roomSortState = roomSortState.next()

        // Reset the other sort column if this one is active
        if (roomSortState != SortState.NONE) {
            nameSortState = SortState.NONE
            sortColumn = SortColumn.ROOM
        } else {
            sortColumn = SortColumn.NONE
        }

        applyFiltersAndSort()
        return roomSortState
    }

    // Method to sort by name
    fun sortByName(): SortState {
        nameSortState = nameSortState.next()

        // Reset the other sort column if this one is active
        if (nameSortState != SortState.NONE) {
            roomSortState = SortState.NONE
            sortColumn = SortColumn.NAME
        } else {
            sortColumn = SortColumn.NONE
        }

        applyFiltersAndSort()
        return nameSortState
    }

    // Method to apply both filter and sort
    private fun applyFiltersAndSort() {
        var filteredList = allDormers

        // Apply search filter if there's a query
        if (currentQuery.isNotEmpty()) {
            filteredList = filteredList.filter { dormer ->
                dormer.roomNumber.contains(currentQuery, ignoreCase = true) ||
                        dormer.firstName.contains(currentQuery, ignoreCase = true) ||
                        dormer.lastName.contains(currentQuery, ignoreCase = true)
            }
        }

        // Apply sorting if active
        when (sortColumn) {
            SortColumn.ROOM -> {
                filteredList = when (roomSortState) {
                    SortState.ASCENDING -> filteredList.sortedBy { it.roomNumber }
                    SortState.DESCENDING -> filteredList.sortedByDescending { it.roomNumber }
                    SortState.NONE -> filteredList // No sorting
                }
            }
            SortColumn.NAME -> {
                filteredList = when (nameSortState) {
                    SortState.ASCENDING -> filteredList.sortedBy { it.firstName + " " + it.lastName }
                    SortState.DESCENDING -> filteredList.sortedByDescending { it.firstName + " " + it.lastName }
                    SortState.NONE -> filteredList // No sorting
                }
            }
            SortColumn.NONE -> {
                // No sorting needed
            }
        }

        dormers = filteredList
        notifyDataSetChanged()
    }

    // Method to update the full list when data changes
    fun updateDormers(newDormers: List<Dormer>) {
        allDormers = newDormers.toList()
        applyFiltersAndSort() // Reapply current filters and sort
    }
}