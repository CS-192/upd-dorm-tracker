package com.example.upddormtracker.ui.dorm_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DormInfoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Dorm Information"
    }
    val text: LiveData<String> = _text
}