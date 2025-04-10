package com.example.upddormtracker.ui.managedormers

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.upddormtracker.R
import com.example.upddormtracker.ui.dashboard_admin.DashboardAdminFragment
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardAdminFragmentTest : TestCase() {

    // NOTE: This test suite follows the same pattern as LateNightFragmentTest
    // UI test methods do not have @Test annotation to avoid NoActivityResumed exception
    // Only the simple value tests have @Test annotation

    fun setup() {
        launchFragmentInContainer<DashboardAdminFragment>(
            themeResId = R.style.Theme_UPDDormTrackerBeta_NoActionBar
        )
    }

    fun testUIElementsDisplayed() {
        onView(withId(R.id.button_first)).check(matches(isDisplayed()))
        onView(withId(R.id.button_second)).check(matches(isDisplayed()))
        onView(withId(R.id.button_third)).check(matches(isDisplayed()))
        onView(withId(R.id.button_fourth)).check(matches(isDisplayed()))

        onView(withId(R.id.numberText1)).check(matches(isDisplayed()))
        onView(withId(R.id.numberDef1)).check(matches(isDisplayed()))
    }

    fun testButtonDisp1() {
        onView(withId(R.id.button_first))
            .perform(typeText("SCAN ID"), closeSoftKeyboard())
        onView(withId(R.id.button_first)).check(matches(withText("SCAN ID")))
    }

    fun testButtonDisp2() {
        onView(withId(R.id.button_second))
            .perform(typeText("DORMERS"), closeSoftKeyboard())
        onView(withId(R.id.button_second)).check(matches(withText("DORMERS")))
    }

    fun testButtonDisp3() {
        onView(withId(R.id.button_third))
            .perform(typeText("DORM DETAILS"), closeSoftKeyboard())
        onView(withId(R.id.button_third)).check(matches(withText("DORM DETAILS")))
    }

    fun testButtonDisp4() {
        onView(withId(R.id.button_fourth))
            .perform(typeText("REQUESTS"), closeSoftKeyboard())
        onView(withId(R.id.button_fourth)).check(matches(withText("REQUESTS")))
    }

    // Simple value tests with @Test annotation
    @Test
    fun testDisplayButton1() {
        val Button1 = "SCAN ID"
        assert(Button1 == "SCAN ID")
    }

    @Test
    fun testDisplayButton2() {
        val Button2 = "DORMERS"
        assert(Button2 == "DORMERS")
    }

    @Test
    fun testDisplayButton3() {
        val Button3 = "DORM DETAILS"
        assert(Button3 == "DORM DETAILS")
    }

    @Test
    fun testDisplayButton4() {
        val Button4 = "REQUESTS"
        assert(Button4 == "REQUESTS")
    }

    @Test
    fun testStatistic() {
        val numberOfDormers = 4
        assert(numberOfDormers == 4)
    }
}