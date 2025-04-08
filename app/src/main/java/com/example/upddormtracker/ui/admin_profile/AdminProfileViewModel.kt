package com.example.upddormtracker.ui.admin_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminProfileViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Profile"
    }

    val text: LiveData<String> = _text
}