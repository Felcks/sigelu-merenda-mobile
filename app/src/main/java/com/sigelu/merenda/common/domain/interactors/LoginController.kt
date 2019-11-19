package com.sigelu.merenda.common.domain.interactors

import com.sigelu.merenda.App
import com.sigelu.merenda.api.RestApi
import com.sigelu.merenda.api_model.login.LoginDataResponse
import com.sigelu.merenda.common.domain.model.Usuario
import com.sigelu.merenda.common.domain.repository.PermissaoSistemaRepository
import com.sigelu.merenda.common.domain.repository.LoginRepository
import com.sigelu.merenda.common.domain.repository.UsuarioRepository
import com.sigelu.merenda.utils.AppSharedPreferences
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