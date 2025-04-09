package com.example.upddormtracker.ui.edit_dorm_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditDormInfoViewModel : ViewModel() {

    private val _curfew = MutableLiveData<String>()
    val curfew: LiveData<String> = _curfew

    private val _curfewError = MutableLiveData<String?>(null)
    val curfewError: LiveData<String?> = _curfewError

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _addressError = MutableLiveData<String?>(null)
    val addressError: LiveData<String?> = _addressError

    private val _contactDetails = MutableLiveData<String>()
    val contactDetails: LiveData<String> = _contactDetails

    private val _contactDetailsError = MutableLiveData<String?>(null)
    val contactDetailsError: LiveData<String?> = _contactDetailsError

    private val _history = MutableLiveData<String>()
    val history: LiveData<String> = _history

    private val _historyError = MutableLiveData<String?>(null)
    val historyError: LiveData<String?> = _historyError


    fun validateCurfew(input: String) {
        _curfew.value = input
        when {
            input.isEmpty() -> _curfewError.value = "This is a required field"
            else -> _curfewError.value = null
        }
    }

    fun validateAddress(input: String) {
        _address.value = input
        when {
            input.isEmpty() -> _addressError.value = "This is a required field"
            else -> _addressError.value = null
        }
    }
    fun validateContactDetails(input: String) {
        _contactDetails.value = input
        when {
            input.isEmpty() -> _contactDetailsError.value = "This is a required field"
            else -> _contactDetailsError.value = null
        }
    }

    fun validateHistory(input: String) {
        _history.value = input
        when {
            input.isEmpty() -> _historyError.value = "This is a required field"
            else -> _historyError.value = null

        }
    }

    fun isFormValid(): Boolean {
        return  !curfew.value.isNullOrEmpty() && curfewError.value == null && !address.value.isNullOrEmpty()
                && addressError.value == null && !contactDetails.value.isNullOrEmpty() && contactDetailsError.value == null
                && !history.value.isNullOrEmpty() && historyError.value == null

    }
}