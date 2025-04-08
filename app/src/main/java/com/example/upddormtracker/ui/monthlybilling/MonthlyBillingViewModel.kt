package com.example.upddormtracker.ui.monthlybilling

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MonthlyBillingViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Create a Request"
    }
    val text: LiveData<String> = _text
}