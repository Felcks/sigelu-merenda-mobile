package com.sigelu.logistica.ui.cadastra_pedido

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sigelu.logistica.R
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoActivity
import com.sigelu.logistica.ui.cadastra_pedido.cadastra_pedido_1_seleciona_item.SelecionaItemPedidoParaNucleoViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class HomeScreenActivityTest : KoinTest {
    @Rule
    @JvmField
    val rule = ActivityTestRule(SelecionaItemPedidoParaNucleoActivity::class.java, true, false)

    lateinit var mockVm: SelecionaItemPedidoParaNucleoViewModel

    @Before
    fun setup() {
        mockVm = mock(SelecionaItemPedidoParaNucleoViewModel::class.java)

        loadKoinModules(module {
            viewModel { mockVm }
        })
    }

    @After
    fun cleanUp() {
        stopKoin()
    }

    @Test
    fun shouldHaveTextViewWithMessage() {
        // 1. declare mock method
        val message = "hello view-model"
        Mockito.`when`(mockVm.response())
            .thenReturn(MutableLiveData())

        // 2. start activity
        rule.launchActivity(null)


        // 3. test
        onView(withId(R.id.tv_passos))
            .check(matches(isDisplayed()))

    }
}