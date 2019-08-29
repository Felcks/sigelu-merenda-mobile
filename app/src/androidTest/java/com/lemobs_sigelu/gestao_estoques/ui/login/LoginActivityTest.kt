package com.lemobs_sigelu.gestao_estoques.ui.login

import android.service.autofill.Validators.not
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.ToastMatcher
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
    fun invalidUser() {

        val expectedText = App.instance.getString(R.string.usuario_nao_encontrado)

        onView(withId(R.id.edt_matricula)).perform(typeText(loginErrado), closeSoftKeyboard())
        onView(withId(R.id.edt_senha)).perform(typeText(senhaCerta), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        onView(withText(expectedText)).inRoot(ToastMatcher()).check(matches(withText(expectedText)))
    }

    @Test
    fun invalidPassword(){

        val expectedText = App.instance.getString(R.string.senha_invalida)

        onView(withId(R.id.edt_matricula)).perform(typeText(loginCerto), closeSoftKeyboard())
        onView(withId(R.id.edt_senha)).perform(typeText(senhaErrada), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        onView(withText(expectedText)).inRoot(ToastMatcher()).check(matches(withText(expectedText)))
    }

    @Test
    fun correctLogin(){

        onView(withId(R.id.edt_matricula)).perform(typeText(loginCerto), closeSoftKeyboard())
        onView(withId(R.id.edt_senha)).perform(typeText(senhaCerta), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        Thread.sleep(5000)
        onView(withId(R.id.btn_login)).check(doesNotExist())
    }

    @Test
    fun emptyUserAndPassword(){

        val expectedText = App.instance.getString(R.string.campos_nao_preenchidos)

        onView(withId(R.id.btn_login)).perform(click())
        onView(withText(expectedText)).inRoot(ToastMatcher()).check(matches(withText(expectedText)))
    }

    @Test
    fun emptyPassword(){

        val expectedText = App.instance.getString(R.string.campos_nao_preenchidos)

        onView(withId(R.id.edt_matricula)).perform(typeText(loginErrado), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())
        onView(withText(expectedText)).inRoot(ToastMatcher()).check(matches(withText(expectedText)))
    }

    @Test
    fun emptyUser(){

        val expectedText = App.instance.getString(R.string.campos_nao_preenchidos)

        onView(withId(R.id.edt_senha)).perform(typeText(senhaErrada), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())
        onView(withText(expectedText)).inRoot(ToastMatcher()).check(matches(withText(expectedText)))
    }

    companion object{
        const val loginCerto = "encarregadocentro1"
        const val senhaCerta = "123456"

        const val loginErrado = "aaaa"
        const val senhaErrada = "aaaa"
    }
}