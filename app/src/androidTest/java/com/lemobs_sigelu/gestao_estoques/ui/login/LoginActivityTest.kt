package com.lemobs_sigelu.gestao_estoques.ui.login

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.lemobs_sigelu.gestao_estoques.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {

    @get:Rule var activityScenarioRule = activityScenarioRule<LoginActivity>()

    @Test
    fun changeText_sameActivity() {

        // Type text and then press the button.
        onView(withId(R.id.edt_matricula))
            .perform(typeText("encarregadocentro1"), closeSoftKeyboard())

        // Check that the text was changed.
        //onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.edt_matricula)).check(matches(withText("encarregadocentro1")))
    }
}