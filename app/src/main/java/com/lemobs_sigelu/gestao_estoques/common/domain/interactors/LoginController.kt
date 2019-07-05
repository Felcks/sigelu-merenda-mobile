package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.login.LoginDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Usuario
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PermissaoSistemaRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.LoginRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.UsuarioRepository
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on May, 2019
 */

class LoginController @Inject constructor(private val loginRepository: LoginRepository,
                                          private val permissaoSistemaRepository: PermissaoSistemaRepository,
                                          private val usuarioRepository: UsuarioRepository){


    fun login(usuario: String, senha: String): Observable<LoginDataResponse> {
        return loginRepository.login(usuario, senha)
    }

    fun carregaPermissoesModulo(auth: String): Observable<List<String>>{
        return permissaoSistemaRepository.carregaPermissoesModulo(auth)
    }

    fun carregaNucleoUsuario(auth: String, usuarioID: Int): Observable<Usuario>{
        return usuarioRepository.getUsuario(auth, usuarioID)
    }

    fun salvarCredenciaisUsuario(loginDataResponse: LoginDataResponse, usuario: Usuario) {

        val context = App.instance

        val tokenNoBarrier = loginDataResponse.token_usuario?.substring(7)
        AppSharedPreferences.setUserToken(context, tokenNoBarrier ?: "")
        AppSharedPreferences.setUserName(context, loginDataResponse.nome ?: "")
        AppSharedPreferences.setNucleoID(context, usuario.nucleo.id)
        AppSharedPreferences.setNucleoNome(context, usuario.nucleo.nome)

        RestApi.auth = tokenNoBarrier ?: ""

        if(loginDataResponse.permissoes != null){
            if(loginDataResponse.permissoes.isNotEmpty()){
                AppSharedPreferences.setUserId(context, loginDataResponse.permissoes[0].id ?: 0)
                AppSharedPreferences.setUserPerfil(context, loginDataResponse.permissoes[0].perfil ?: "")
            }
        }
    }
}