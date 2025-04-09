package com.example.upddormtracker.ui.edit_announcement


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditAnnouncementViewModel : ViewModel() {
    // LiveData for form fields
    private val _subject = MutableLiveData<String>()
    val subject: LiveData<String> = _subject

    private val _subjectError = MutableLiveData<String?>(null)
    val subjectError: LiveData<String?> = _subjectError

    private val _details = MutableLiveData<String>()
    val details: LiveData<String> = _details

    private val _detailsError = MutableLiveData<String?>(null)
    val detailsError: LiveData<String?> = _detailsError



    // Validation logic
    fun validateSubject(input: String) {
        _subject.value = input
        when {
            input.isEmpty() -> _subjectError.value = "This is a required field"
            else -> _subjectError.value = null
        }
    }

    fun validateDetails(input: String){
        _details.value = input
        when {
            input.isEmpty() -> _detailsError.value = "This is a required field"
            else -> _detailsError.value = null
        }
    }




    // Check if form is valid overall
    fun isFormValid(): Boolean {
        return !subject.value.isNullOrEmpty() && subjectError.value == null && !details.value.isNullOrEmpty() && detailsError.value == null
    }


}


