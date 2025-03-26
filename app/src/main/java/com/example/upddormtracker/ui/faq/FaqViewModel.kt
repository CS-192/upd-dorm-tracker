package com.example.upddormtracker.ui.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FaqViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Frequently Asked Questions"
    }
    val text: LiveData<String> = _text
}