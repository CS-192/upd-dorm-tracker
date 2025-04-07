package com.example.upddormtracker.ui.managedormers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ManageDormersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Manage Dormer's Information"
    }
    val text: LiveData<String> = _text
}