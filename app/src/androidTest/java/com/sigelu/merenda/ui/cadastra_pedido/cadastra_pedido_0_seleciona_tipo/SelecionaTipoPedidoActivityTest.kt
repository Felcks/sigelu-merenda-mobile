package com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_0_seleciona_tipo

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sigelu.merenda.R
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents.intending
import android.app.Activity
import android.app.Instrumentation.ActivityResult
import androidx.test.espresso.intent.Intents.intended
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_0_seleciona_obra.SelecionaObraActivity
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoActivity


@RunWith(AndroidJUnit4::class)
@LargeTest
class SelecionaTipoPedidoActivityTest{

    @get:Rule
    var activityScenarioRule = activityScenarioRule<SelecionaTipoPedidoActivity>()

    @Test
    fun testProximo_SemMudar(){
        Intents.init()
        val matcher: Matcher<Intent> = hasComponent(SelecionaItemPedidoParaNucleoActivity::class.java.name)

        onView(withId(R.id.ll_layout_proximo)).perform(click())

        val result = ActivityResult(Activity.RESULT_OK, null)
        intending(matcher).respondWith(result)

        intended(matcher)
        Intents.release()
    }

    @Test
    fun testProximo_ClicandoNaPrimeiraOpcao(){
        Intents.init()
        val matcher: Matcher<Intent> = hasComponent(SelecionaItemPedidoParaNucleoActivity::class.java.name)

        onView(withId(R.id.rb_fornecedor)).perform(click())
        onView(withId(R.id.ll_layout_proximo)).perform(click())

        val result = ActivityResult(Activity.RESULT_OK, null)
        intending(matcher).respondWith(result)

        intended(matcher)
        Intents.release()
    }

    @Test
    fun testProximo_ClicandoNaSegundaOpcao(){
        Intents.init()
        val matcher: Matcher<Intent> = hasComponent(SelecionaObraActivity::class.java.name)

        onView(withId(R.id.rb_nucleo)).perform(click())
        onView(withId(R.id.ll_layout_proximo)).perform(click())

        val result = ActivityResult(Activity.RESULT_OK, null)
        intending(matcher).respondWith(result)

        intended(matcher)
        Intents.release()
    }

}