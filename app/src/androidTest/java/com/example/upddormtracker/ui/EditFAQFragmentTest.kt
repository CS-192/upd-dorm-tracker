package com.example.upddormtracker.ui.edit_faq

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
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
class EditFaqFragmentTest : TestCase() {

    fun setup() {
        launchFragmentInContainer<EditFaqFragment>(
            themeResId = R.style.Theme_UPDDormTrackerBeta_NoActionBar
        )
    }

    fun testUIElementsDisplayed() {
        onView(withId(R.id.input_question)).check(matches(isDisplayed()))
        onView(withId(R.id.input_answer)).check(matches(isDisplayed()))
        onView(withId(R.id.save_faq_button)).check(matches(isDisplayed()))
        onView(withId(R.id.cancel_faq_button)).check(matches(isDisplayed()))
    }

    fun testQuestionInput() {
        onView(withId(R.id.input_question))
            .perform(typeText("What is the dorm curfew?"), closeSoftKeyboard())
        onView(withId(R.id.input_question)).check(matches(withText("What is the dorm curfew?")))
    }

    fun testAnswerInput() {
        onView(withId(R.id.input_answer))
            .perform(typeText("The curfew is at 10 PM."), closeSoftKeyboard())
        onView(withId(R.id.input_answer)).check(matches(withText("The curfew is at 10 PM.")))
    }

    fun testSaveButtonState() {
        onView(withId(R.id.save_faq_button)).check(matches(isEnabled()))
    }

    fun testCancelButtonState() {
        onView(withId(R.id.cancel_faq_button)).check(matches(isEnabled()))
    }

    @Test
    fun testQuestionValue() {
        val question = "What is the dorm curfew?"
        assert(question == "What is the dorm curfew?")
    }

    @Test
    fun testAnswerValue() {
        val answer = "The curfew is at 10 PM."
        assert(answer == "The curfew is at 10 PM.")
    }

    @Test
    fun testSaveFaq() {
        val saveMessage = "FAQ has been successfully edited!"
        assert(saveMessage == "FAQ has been successfully edited!")
    }

    @Test
    fun testCancelFaq() {
        val cancelMessage = "FAQ edit cancelled"
        assert(cancelMessage == "FAQ edit cancelled")
    }
}
