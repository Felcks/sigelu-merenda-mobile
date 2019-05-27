package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.login.LoginDataResponse
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences

/**
 * Created by felcks on May, 2019
 */
class GerenciadorCredenciaisRepository {

    fun salvarCredenciais(loginResponse: LoginDataResponse){

        val context = App.instance

        val tokenNoBarrier = loginResponse.token_usuario.substring(7)
        AppSharedPreferences.setUserToken(context, tokenNoBarrier)
        AppSharedPreferences.setUserName(context, loginResponse.nome)
        RestApi.auth = tokenNoBarrier

        if(loginResponse.permissoes != null){
            if(loginResponse.permissoes.isNotEmpty()){
                AppSharedPreferences.setUserId(context, loginResponse.permissoes[0].id ?: 0)
                AppSharedPreferences.setUserPerfil(context, loginResponse.permissoes[0].perfil ?: "")
            }
        }
    }
}