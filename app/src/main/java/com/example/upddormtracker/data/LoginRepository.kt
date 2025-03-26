package com.example.upddormtracker.data

import com.example.upddormtracker.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class LoginRepository(private val dataSource: LoginDataSource) {

    private val auth: FirebaseAuth = Firebase.auth

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = auth.currentUser != null

    init {
        // Check if a user is already signed in when the repository is created
        val currentUser = auth.currentUser
        if (currentUser != null) {
            user = LoggedInUser(
                userId = currentUser.uid,
                displayName = currentUser.displayName ?: currentUser.email ?: "User"
            )
        }
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // Delegate login to the data source
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    fun logout() {
        // Clear the in-memory user
        user = null

        // Sign out using Firebase
        dataSource.logout()
    }

    suspend fun register(username: String, password: String): Result<LoggedInUser> {
        // Delegate registration to the data source
        val result = dataSource.register(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // You can add additional logic here for persisting user info if needed
        // For example, you might want to save some user details to local storage
    }

    // Optional: Method to check if a user is currently signed in
    fun getCurrentUser(): LoggedInUser? {
        val currentUser = auth.currentUser
        return currentUser?.let {
            LoggedInUser(
                userId = it.uid,
                displayName = it.displayName ?: it.email ?: "User"
            )
        }
    }
}