package com.example.upddormtracker.ui.dashboard_dormer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardDormerViewModel: ViewModel()  {
    private val _text = MutableLiveData<String>().apply {
        value = "Dashboard"
    }
    val text: LiveData<String> = _text
}