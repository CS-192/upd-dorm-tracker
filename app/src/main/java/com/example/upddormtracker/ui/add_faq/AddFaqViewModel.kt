package com.example.upddormtracker.ui.add_faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddFaqViewModel : ViewModel() {
    // LiveData for form fields
    private val _question = MutableLiveData<String>()
    val question: LiveData<String> = _question

    private val _questionError = MutableLiveData<String?>(null)
    val questionError: LiveData<String?> = _questionError

    private val _answer = MutableLiveData<String>()
    val answer: LiveData<String> = _answer

    private val _answerError = MutableLiveData<String?>(null)
    val answerError: LiveData<String?> = _answerError


    // Validation logic
    fun validateQuestion(input: String) {
        _question.value = input
        when {
            input.isEmpty() -> _questionError.value = "*This is a required field"
            else -> _questionError.value = null
        }
    }

    fun validateAnswer(input: String){
        _answer.value = input
        when {
            input.isEmpty() -> _answerError.value = "*This is a required field"
            else -> _answerError.value = null
        }
    }

    // Check if form is valid overall
    fun isFormValid(): Boolean {
        return !question.value.isNullOrEmpty() && questionError.value == null && !answer.value.isNullOrEmpty() && answerError.value == null
    }


}


