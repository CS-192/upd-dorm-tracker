package com.example.upddormtracker.ui.latenight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LateNightViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Create a Request"
    }
    val text: LiveData<String> = _text
}