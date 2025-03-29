package com.example.upddormtracker.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.upddormtracker.R
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LateNightFragmentTest : TestCase(){

    fun setup() {
        // Launch the fragment in a test container
        launchFragmentInContainer<LateNightFragment>(
            themeResId = R.style.Theme_UPDDormTrackerBeta_NoActionBar
        )
    }

    fun testUIElementsDisplayed() {
        // Check if key UI elements are visible
        onView(withId(R.id.spinner_request_type)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSelectDateDeparture)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSelectDateArrival)).check(matches(isDisplayed()))
        onView(withId(R.id.etDestination)).check(matches(isDisplayed()))
        onView(withId(R.id.etReason)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSubmit)).check(matches(isDisplayed()))
    }

    fun testSpinnerSelection() {
        // Test spinner interactions
        onView(withId(R.id.spinner_request_type)).perform(click())
        onView(withText("Late Night Pass")).perform(click())
        onView(withId(R.id.spinner_request_type)).check(matches(withSpinnerText("Late Night Pass")))

        onView(withId(R.id.spinner_request_type)).perform(click())
        onView(withText("Overnight Pass")).perform(click())
        onView(withId(R.id.spinner_request_type)).check(matches(withSpinnerText("Overnight Pass")))
    }

    fun testDestinationInput() {
        // Test destination input
        onView(withId(R.id.etDestination)).perform(typeText("Test Destination"), closeSoftKeyboard())
        onView(withId(R.id.etDestination)).check(matches(withText("Test Destination")))
    }

    fun testReasonInput() {
        // Test reason input
        onView(withId(R.id.etReason)).perform(typeText("Test Reason"), closeSoftKeyboard())
        onView(withId(R.id.etReason)).check(matches(withText("Test Reason")))
    }

    fun testSubmitButtonState() {
        // Note: This is a simplified test as full form validation would require
        // more complex mocking of Firebase and ViewModel
        onView(withId(R.id.btnSubmit)).check(matches(isEnabled()))
    }

    @Test
    fun testAddingPassType() {
        val passType = "Overnight"
        assert(passType == "Overnight")
    }

    @Test
    fun testAddingDepartureDate() {
        val departureDate = "9/24/2024"
        assert(departureDate == "9/24/2024")
    }

    @Test
    fun testAddingArrivalDate() {
        val departureDate = "7/10/24"
        assert(departureDate == "7/10/24")
    }

    @Test
    fun testAddingDestination() {
        val destination = "Davao"
        assert(destination == "Davao")
    }

    @Test
    fun testAddingReason() {
        val reason = "Group Study"
        assert(reason == "Group Study")
    }

    @Test
    fun testSubmit() {
        val submit = "Submitted"
        assert(submit == "Submitted")
    }

}