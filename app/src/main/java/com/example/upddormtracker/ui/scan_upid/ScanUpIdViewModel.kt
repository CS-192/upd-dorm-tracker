package com.example.upddormtracker.ui.scan_upid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanUpIdViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Scan UP ID"
    }
    val text: LiveData<String> = _text
}