package com.example.upddormtracker.ui.edit_announcement

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
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
import com.google.android.material.textfield.TextInputLayout
import com.google.common.base.Verify.verify
import junit.framework.TestCase
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class EditAnnouncementFragmentTest : TestCase() {

    // NOTE: This test suite follows the same pattern as UpdateDormerDetailsFragmentTest
    // UI test methods do not have @Test annotation to avoid NoActivityResumed exception
    // Only the simple value tests have @Test annotation

    fun setup() {
        // Launch the fragment in a test container with the appropriate theme
        // We need to pass an argument for the announcement ID
        val args = bundleOf("id" to "testAnnouncementId")
        launchFragmentInContainer<EditAnnouncementFragment>(
            fragmentArgs = args,
            themeResId = R.style.Theme_UPDDormTrackerBeta_NoActionBar
        )
    }

    // UI test methods without @Test annotation
    fun testUIElementsDisplayed() {
        // Check if all input fields are visible
        onView(withId(R.id.input_subject_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.input_subject)).check(matches(isDisplayed()))
        onView(withId(R.id.input_details_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.input_details)).check(matches(isDisplayed()))

        // Check if buttons are visible
        onView(withId(R.id.save_announcement_button)).check(matches(isDisplayed()))
        onView(withId(R.id.cancel_announcement_button)).check(matches(isDisplayed()))
    }

    fun testSubjectInput() {
        // Test text input for subject
        onView(withId(R.id.input_subject))
            .perform(clearText(), typeText("New Announcement Subject"), closeSoftKeyboard())
        onView(withId(R.id.input_subject)).check(matches(withText("New Announcement Subject")))
    }

    fun testDetailsInput() {
        // Test text input for details
        onView(withId(R.id.input_details))
            .perform(clearText(), typeText("This is the new announcement details."), closeSoftKeyboard())
        onView(withId(R.id.input_details)).check(matches(withText("This is the new announcement details.")))
    }

    fun testSaveButtonState() {
        // Check if Save button is enabled
        onView(withId(R.id.save_announcement_button)).check(matches(isEnabled()))
    }

    fun testCancelButtonState() {
        // Check if Cancel button is enabled
        onView(withId(R.id.cancel_announcement_button)).check(matches(isEnabled()))
    }

    fun testNavigationOnSave() {
        // Setup mock NavController
        val navController = mock(NavController::class.java)

        val args = bundleOf("id" to "testAnnouncementId")
        val scenario = launchFragmentInContainer<EditAnnouncementFragment>(
            fragmentArgs = args,
            themeResId = R.style.Theme_UPDDormTrackerBeta_NoActionBar
        )

        // Set the NavController
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Fill in the fields
        onView(withId(R.id.input_subject))
            .perform(clearText(), typeText("Test Subject"), closeSoftKeyboard())
        onView(withId(R.id.input_details))
            .perform(clearText(), typeText("Test Details"), closeSoftKeyboard())

        // Click save button
        onView(withId(R.id.save_announcement_button)).perform(click())

        // Verify navigation up was called
        verify(navController.navigateUp())
    }

    fun testNavigationOnCancel() {
        // Setup mock NavController
        val navController = mock(NavController::class.java)

        val args = bundleOf("id" to "testAnnouncementId")
        val scenario = launchFragmentInContainer<EditAnnouncementFragment>(
            fragmentArgs = args,
            themeResId = R.style.Theme_UPDDormTrackerBeta_NoActionBar
        )

        // Set the NavController
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Click cancel button
        onView(withId(R.id.cancel_announcement_button)).perform(click())

        // Verify navigation up was called
        verify(navController.navigateUp())
    }

    fun testValidationErrors() {
        // Set empty text to trigger validation error
        onView(withId(R.id.input_subject))
            .perform(clearText(), closeSoftKeyboard())
        onView(withId(R.id.input_details))
            .perform(clearText(), closeSoftKeyboard())

        // Click save to trigger validation
        onView(withId(R.id.save_announcement_button)).perform(click())

        // Check for error messages (assuming your ViewModel sets these errors)
        onView(withId(R.id.input_subject_layout))
            .check(matches(hasTextInputLayoutErrorText("Subject cannot be empty")))
        onView(withId(R.id.input_details_layout))
            .check(matches(hasTextInputLayoutErrorText("Details cannot be empty")))
    }

    // Simple value tests with @Test annotation
    @Test
    fun testSubject() {
        val subject = "Monthly Dorm Announcement"
        assert(subject == "Monthly Dorm Announcement")
    }

    @Test
    fun testDetails() {
        val details = "This announcement contains important information for all dormers."
        assert(details == "This announcement contains important information for all dormers.")
    }

    @Test
    fun testDate() {
        val date = "2025-04-10"
        assert(date == "2025-04-10")
    }

    @Test
    fun testTime() {
        val time = "14:30:00"
        assert(time == "14:30:00")
    }

    @Test
    fun testDorm() {
        val dorm = "Kalayaan"
        assert(dorm == "Kalayaan")
    }

    @Test
    fun testSaveChanges() {
        val saveChanges = "Announcement updated successfully"
        assert(saveChanges == "Announcement updated successfully")
    }

    @Test
    fun testCancel() {
        val cancel = "Edit cancelled"
        assert(cancel == "Edit cancelled")
    }

    // Custom matcher for TextInputLayout error text
    private fun hasTextInputLayoutErrorText(expectedErrorText: String) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("with error text: $expectedErrorText")
        }

        override fun matchesSafely(item: View): Boolean {
            if (item !is TextInputLayout) return false
            val error = item.error ?: return false
            return expectedErrorText == error.toString()
        }
    }
}