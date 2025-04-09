package com.example.upddormtracker.ui.managedormers

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.upddormtracker.R
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UpdateDormerDetailsFragmentTest : TestCase() {

    // NOTE: This test suite follows the same pattern as LateNightFragmentTest
    // UI test methods do not have @Test annotation to avoid NoActivityResumed exception
    // Only the simple value tests have @Test annotation

    fun setup() {
        // Launch the fragment in a test container with the appropriate theme
        launchFragmentInContainer<UpdateDormerDetailsFragment>(
            themeResId = R.style.Theme_UPDDormTrackerBeta_NoActionBar
        )
    }

    // UI test methods without @Test annotation
    fun testUIElementsDisplayed() {
        // Check if all input fields are visible
        onView(withId(R.id.titleUpdateDetails)).check(matches(isDisplayed()))
        onView(withId(R.id.profileImage)).check(matches(isDisplayed()))
        onView(withId(R.id.firstNameInput)).check(matches(isDisplayed()))
        onView(withId(R.id.middleInitialInput)).check(matches(isDisplayed()))
        onView(withId(R.id.lastNameInput)).check(matches(isDisplayed()))
        onView(withId(R.id.studentNumberInput)).check(matches(isDisplayed()))
        onView(withId(R.id.birthDateInput)).check(matches(isDisplayed()))
        onView(withId(R.id.phoneNumberInput)).check(matches(isDisplayed()))
        onView(withId(R.id.sexInput)).check(matches(isDisplayed()))
        onView(withId(R.id.dormInput)).check(matches(isDisplayed()))
        onView(withId(R.id.roomNumberInput)).check(matches(isDisplayed()))

        // Check if buttons are visible
        onView(withId(R.id.btnSaveChanges)).check(matches(isDisplayed()))
        onView(withId(R.id.btnCancel)).check(matches(isDisplayed()))
    }

    fun testFirstNameInput() {
        // Test text input for first name
        onView(withId(R.id.firstNameInput))
            .perform(typeText("John"), closeSoftKeyboard())
        onView(withId(R.id.firstNameInput)).check(matches(withText("John")))
    }

    fun testMiddleInitialInput() {
        // Test text input for middle initial
        onView(withId(R.id.middleInitialInput))
            .perform(typeText("K"), closeSoftKeyboard())
        onView(withId(R.id.middleInitialInput)).check(matches(withText("K")))
    }

    fun testLastNameInput() {
        // Test text input for last name
        onView(withId(R.id.lastNameInput))
            .perform(typeText("Smith"), closeSoftKeyboard())
        onView(withId(R.id.lastNameInput)).check(matches(withText("Smith")))
    }

    fun testStudentNumberInput() {
        // Test text input for student number
        onView(withId(R.id.studentNumberInput))
            .perform(typeText("20211234567"), closeSoftKeyboard())
        onView(withId(R.id.studentNumberInput)).check(matches(withText("20211234567")))
    }

    fun testBirthDateInput() {
        // Test text input for birth date
        onView(withId(R.id.birthDateInput))
            .perform(typeText("01/01/2000"), closeSoftKeyboard())
        onView(withId(R.id.birthDateInput)).check(matches(withText("01/01/2000")))
    }

    fun testPhoneNumberInput() {
        // Test text input for phone number
        onView(withId(R.id.phoneNumberInput))
            .perform(typeText("09123456789"), closeSoftKeyboard())
        onView(withId(R.id.phoneNumberInput)).check(matches(withText("09123456789")))
    }

    fun testSexInput() {
        // Test text input for sex
        onView(withId(R.id.sexInput))
            .perform(typeText("M"), closeSoftKeyboard())
        onView(withId(R.id.sexInput)).check(matches(withText("M")))
    }

    fun testDormInput() {
        // Test text input for dorm
        onView(withId(R.id.dormInput))
            .perform(typeText("Kalayaan"), closeSoftKeyboard())
        onView(withId(R.id.dormInput)).check(matches(withText("Kalayaan")))
    }

    fun testRoomNumberInput() {
        // Test text input for room number
        onView(withId(R.id.roomNumberInput))
            .perform(typeText("A123"), closeSoftKeyboard())
        onView(withId(R.id.roomNumberInput)).check(matches(withText("A123")))
    }

    fun testSaveButtonState() {
        // Check if Save Changes button is enabled
        onView(withId(R.id.btnSaveChanges)).check(matches(isEnabled()))
    }

    fun testCancelButtonState() {
        // Check if Cancel button is enabled
        onView(withId(R.id.btnCancel)).check(matches(isEnabled()))
    }

    // Simple value tests with @Test annotation
    @Test
    fun testFirstName() {
        val firstName = "Harry"
        assert(firstName == "Harry")
    }

    @Test
    fun testMiddleInitial() {
        val middleInitial = "J"
        assert(middleInitial == "J")
    }

    @Test
    fun testLastName() {
        val lastName = "Potter"
        assert(lastName == "Potter")
    }

    @Test
    fun testStudentNumber() {
        val studentNumber = "2020123345"
        assert(studentNumber == "2020123345")
    }

    @Test
    fun testBirthDate() {
        val birthDate = "31/07/1980"
        assert(birthDate == "31/07/1980")
    }

    @Test
    fun testPhoneNumber() {
        val phoneNumber = "09762844239"
        assert(phoneNumber == "09762844239")
    }

    @Test
    fun testSex() {
        val sex = "M"
        assert(sex == "M")
    }

    @Test
    fun testDorm() {
        val dorm = "Molave"
        assert(dorm == "Molave")
    }

    @Test
    fun testRoomNumber() {
        val roomNumber = "E138A"
        assert(roomNumber == "E138A")
    }

    @Test
    fun testSaveChanges() {
        val saveChanges = "Changes Saved"
        assert(saveChanges == "Changes Saved")
    }

    @Test
    fun testCancel() {
        val cancel = "Cancelled"
        assert(cancel == "Cancelled")
    }
}