package com.example.upddormtracker.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Patterns
import com.example.upddormtracker.data.LoginRepository
import com.example.upddormtracker.data.Result

import com.example.upddormtracker.R
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // Launch a coroutine to handle asynchronous login
        viewModelScope.launch {
            try {
                val result = loginRepository.login(username, password)

                if (result is Result.Success) {
                    _loginResult.value = LoginResult(
                        success = LoggedInUserView(displayName = result.data.displayName)
                    )
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun register(username: String, password: String) {
        // Launch a coroutine to handle asynchronous registration
        viewModelScope.launch {
            try {
                val result = loginRepository.register(username, password)

                if (result is Result.Success) {
                    _loginResult.value = LoginResult(
                        success = LoggedInUserView(displayName = result.data.displayName)
                    )
                } else {
                    _loginResult.value = LoginResult(error = R.string.registration_failed)
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult(error = R.string.registration_failed)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        when {
            !isUserNameValid(username) -> {
                _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
            }
            !isPasswordValid(password) -> {
                _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
            }
            else -> {
                _loginForm.value = LoginFormState(isDataValid = true)
            }
        }
    }

    // Email validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            false
        }
    }

    // Password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6 // Firebase requires at least 6 characters
    }

    fun logout() {
        loginRepository.logout()
    }

    // Check if user is currently logged in
    fun isUserLoggedIn(): Boolean = loginRepository.isLoggedIn
}