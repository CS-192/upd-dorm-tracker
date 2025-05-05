package com.example.upddormtracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _dorm = MutableLiveData<String>()
    val dorm: LiveData<String> get() = _dorm

    private val _isAdmin = MutableLiveData<Boolean>()
    val isAdmin: LiveData<Boolean> get() = _isAdmin

    private val _isDormer = MutableLiveData<Boolean>()
    val isDormer: LiveData<Boolean> get() = _isDormer

    private val _studentNumber = MutableLiveData<String>()
    val studentNumber: LiveData<String> get() = _studentNumber

    fun setDorm(dorm: String) {
        _dorm.value = dorm
    }

    fun setIsAdmin(isAdmin: Boolean) {
        _isAdmin.value = isAdmin
    }

    fun setIsDormer(isDormer: Boolean) {
        _isDormer.value = isDormer
    }

    fun setStudentNumber(studentNumber: String) {
        _studentNumber.value = studentNumber
    }
}