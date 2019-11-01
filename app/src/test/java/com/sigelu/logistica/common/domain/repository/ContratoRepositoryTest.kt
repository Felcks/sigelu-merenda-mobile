package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.common.domain.model.ContratoEstoque
import io.reactivex.observers.TestObserver
import org.junit.Test

import org.junit.Assert.*

class ContratoRepositoryTest {

    @Test
    fun carregaListaContratosVigentes() {

        val repository = ContratoRepository()

        val testSubscriber = TestObserver<List<ContratoEstoque>>()
        repository.carregaListaContratosVigentes().subscribe(testSubscriber)
        testSubscriber.assertNoErrors()

        val awaitTerminalEvent = testSubscriber.awaitTerminalEvent()
        for (item in (testSubscriber.events[0][0] as List<ContratoEstoque>)) {
            assertNotNull(item.id)
            assertNotNull(item.situacao)
            assertNotNull(item.objetoContrato)
            assertNotNull(item.numeroContrato)
            assertNotNull(item.valorContratual)
        }
    }
}