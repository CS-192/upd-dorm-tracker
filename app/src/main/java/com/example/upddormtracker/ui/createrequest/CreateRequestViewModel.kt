package com.example.upddormtracker.ui.createrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateRequestViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Create a Request"
    }
    val text: LiveData<String> = _text
}