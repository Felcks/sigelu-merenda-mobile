package com.sigelu.merenda.common.domain.repository

import com.sigelu.merenda.api_model.login.LoginDataResponse
import io.reactivex.observers.TestObserver
import org.junit.Test

import org.junit.Assert.*

class LoginRepositoryTest {

    @Test
    fun login() {

        val repository = LoginRepository()

        val testSubscriber = TestObserver<LoginDataResponse>()
        repository.login("encarregadocentro1", "123456").subscribe(testSubscriber)
        testSubscriber.assertNoErrors()

        testSubscriber.awaitTerminalEvent()
        val item = testSubscriber.events[0][0] as LoginDataResponse
        assertNotNull(item.nome)
        assertNotNull(item.permissoes)
        assertNotNull(item.token_usuario)
        assertNotNull(item.usuario_id)
    }

    @Test
    fun loginError() {

        val repository = LoginRepository()

        val testSubscriber = TestObserver<LoginDataResponse>()
        repository.login("aaaa", "aaa").subscribe(testSubscriber)
        testSubscriber.assertNoErrors()

        testSubscriber.awaitTerminalEvent()
        val item = testSubscriber.events[0][0] as LoginDataResponse
        assertNotNull(item.message)
        assertNotNull(item.tipo)
    }
}