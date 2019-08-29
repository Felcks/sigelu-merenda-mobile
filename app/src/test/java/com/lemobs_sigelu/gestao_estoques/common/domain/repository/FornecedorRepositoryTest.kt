package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ContratoEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Fornecedor
import io.reactivex.observers.TestObserver
import org.junit.Test

import org.junit.Assert.*

class FornecedorRepositoryTest {

    @Test
    fun carregaListaFornecedor() {

        val repository = FornecedorRepository()

        val testSubscriber = TestObserver<List<Fornecedor>>()
        repository.carregaListaFornecedor().subscribe(testSubscriber)
        testSubscriber.assertNoErrors()

        val awaitTerminalEvent = testSubscriber.awaitTerminalEvent()
        for (item in (testSubscriber.events[0][0] as List<Fornecedor>)) {
            assertNotNull(item.id)
        }
    }
}