package com.example.upddormtracker.ui.dormer_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DormerProfileViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Profile"
    }

    val text: LiveData<String> = _text
}