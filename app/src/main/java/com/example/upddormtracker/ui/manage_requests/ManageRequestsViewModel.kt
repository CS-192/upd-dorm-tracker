package com.example.upddormtracker.ui.manage_requests

import androidx.lifecycle.ViewModel

class ManageRequestsViewModel : ViewModel() {

    // Data class for requests
    data class RequestItem(
        val id: String,
        val date: String,
        val studentNumber: String
    )

    // Get overnight pass data
    fun getOvernightPassData(): List<RequestItem> {
        // In a real app, this would fetch from a database or network
        return List(15) {
            RequestItem(
                id = "op$it",
                date = "29/10/24",
                studentNumber = "202012345"
            )
        }
    }

    // Get monthly bill data
    fun getMonthlyBillData(): List<RequestItem> {
        // In a real app, this would fetch from a database or network
        return List(10) {
            RequestItem(
                id = "mb$it",
                date = "01/10/24",
                studentNumber = "202012345"
            )
        }
    }

    // Get reports data
    fun getReportsData(): List<RequestItem> {
        // In a real app, this would fetch from a database or network
        return List(8) {
            RequestItem(
                id = "rep$it",
                date = "15/10/24",
                studentNumber = "202012345"
            )
        }
    }
}