package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.api_model.login.LoginDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PermissaoSistemaRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciadorCredenciaisRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on May, 2019
 */

class LoginController @Inject constructor(private val loginRepository: LoginRepository,
                                          private val permissaoSistemaRepository: PermissaoSistemaRepository,
                                          private val gerenciadorCredenciaisRepository: GerenciadorCredenciaisRepository){


    fun login(usuario: String, senha: String): Observable<LoginDataResponse> {
        return loginRepository.login(usuario, senha)
    }

    fun carregaPermissoesModulo(auth: String): Observable<List<String>>{
        return permissaoSistemaRepository.carregaPermissoesModulo(auth)
    }

    fun salvarCredenciaisUsuario(loginDataResponse: LoginDataResponse) {
        this.gerenciadorCredenciaisRepository.salvarCredenciais(loginDataResponse)
    }
}