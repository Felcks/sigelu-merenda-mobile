package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.api_model.login.LoginDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaPermissoesSistemasRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciadorCredenciaisRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on May, 2019
 */

class LoginController @Inject constructor(val loginRepository: LoginRepository,
                                          val carregaPermissoesSistemasRepository: CarregaPermissoesSistemasRepository,
                                          val gerenciadorCredenciaisRepository: GerenciadorCredenciaisRepository){


    fun login(usuario: String, senha: String): Observable<LoginDataResponse> {
        return loginRepository.login(usuario, senha)
    }

    fun carregaPermissoesModulo(auth: String): Observable<List<String>>{
        return carregaPermissoesSistemasRepository.carregaPermissoesModulo(auth)
    }

    fun salvarCredenciaisUsuario(loginDataResponse: LoginDataResponse) {
        this.gerenciadorCredenciaisRepository.salvarCredenciais(loginDataResponse)
    }
}