package com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sigelu.merenda.R
import com.sigelu.merenda.ui.cadastra_pedido.cadastra_pedido_2_cadastra_item.CadastraItemPedidoActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class SelecionaItemPedidoParaNucleoActivityTest: KoinTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<SelecionaItemPedidoParaNucleoActivity>()


    @Before
    fun setup() {
    }

    @After
    fun cleanUp() {
    }

    @Test
    fun testProximo_SemMudar(){
        Intents.init()
        val matcher: Matcher<Intent> = IntentMatchers.hasComponent(CadastraItemPedidoActivity::class.java.name)

        Espresso.onView(ViewMatchers.withId(R.id.ll_layout_proximo)).perform(ViewActions.click())

        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        Intents.intending(matcher).respondWith(result)

        Intents.intended(matcher)
        Intents.release()
    }
}