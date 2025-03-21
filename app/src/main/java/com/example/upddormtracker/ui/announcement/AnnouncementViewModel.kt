package com.example.upddormtracker.ui.announcement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnnouncementViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Announcements"
    }
    val text: LiveData<String> = _text
}