package com.example.upddormtracker.ui.dorm_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DormDetailsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Manage Dorm Details"
    }
    val text: LiveData<String> = _text
}