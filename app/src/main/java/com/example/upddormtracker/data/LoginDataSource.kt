package com.example.upddormtracker.data

import com.example.upddormtracker.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private val auth: FirebaseAuth = Firebase.auth

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            // Attempt Firebase Email/Password Authentication
            val authResult = auth.signInWithEmailAndPassword(username, password).await()

            // Get the currently signed-in user
            val firebaseUser = authResult.user ?: throw IOException("Authentication failed")

            // Create LoggedInUser from Firebase user
            val loggedInUser = LoggedInUser(
                userId = firebaseUser.uid,
                displayName = firebaseUser.displayName ?: username
            )

            Result.Success(loggedInUser)
        } catch (e: Exception) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // Sign out the current user from Firebase
        auth.signOut()
    }

    // Optional: Method to register a new user
    suspend fun register(username: String, password: String): Result<LoggedInUser> {
        return try {
            // Create user with email and password
            val authResult = auth.createUserWithEmailAndPassword(username, password).await()

            val firebaseUser = authResult.user ?: throw IOException("Registration failed")

            val loggedInUser = LoggedInUser(
                userId = firebaseUser.uid,
                displayName = firebaseUser.displayName ?: username
            )

            Result.Success(loggedInUser)
        } catch (e: Exception) {
            Result.Error(IOException("Error registering user", e))
        }
    }
}