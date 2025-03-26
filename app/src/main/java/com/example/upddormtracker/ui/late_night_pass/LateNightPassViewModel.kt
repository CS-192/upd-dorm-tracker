package com.example.upddormtracker.ui.late_night_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LateNightPassViewModel : ViewModel() {

    // Data class for the pass details
    data class PassDetails(
        val dormer: String,
        val roomNumber: String,
        val studentNumber: String,
        val passType: String,
        val destination: String,
        val departureDate: String,
        val arrivalDate: String,
        val arrivalTime: String
    )

    private val _passDetails = MutableLiveData<PassDetails>()
    val passDetails: LiveData<PassDetails> get() = _passDetails

    // Function to load pass details by ID
    fun loadPassDetails(passId: String) {
        // In a real app, this would fetch data from a repository
        // For now, return sample data
        _passDetails.value = PassDetails(
            dormer = "YOR BRIAR",
            roomNumber = "E138A",
            studentNumber = "202012345",
            passType = "ON",
            destination = "OSTANIA",
            departureDate = "10/10/24",
            arrivalDate = "13/10/24",
            arrivalTime = "1:00PM"
        )
    }

    // Function to delete a pass
    fun deletePass(passId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        // In a real app, this would call a repository method to delete the pass from the database
        // For now, simulate a successful deletion
        onSuccess()
    }
}