package com.example.upddormtracker.ui.add_faq

import com.example.upddormtracker.datamodel.FAQ
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class FAQRepositoryTest {
    private lateinit var firestore: FirebaseFirestore

    @Before
    fun setup() {
        // Initialize Mockito annotations


        // Initialize Firestore (use emulator for testing)
        firestore = Firebase.firestore
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setHost("localhost:8080")
            .setSslEnabled(false)
            .build()
    }

    @Test


    @Test(expected = IllegalArgumentException::class)
    fun testAddFAQWithEmptyQuestion(): Unit = runBlocking {
        // Arrange - Invalid FAQ data
        val invalidFaq = FAQ(
            question = "",
            answer = "Some answer",
            dorm = "Molave"
        )

        // Act - Attempt to add invalid FAQ
        firestore.collection("faq")
            .add(invalidFaq)
            .await()
    }
}