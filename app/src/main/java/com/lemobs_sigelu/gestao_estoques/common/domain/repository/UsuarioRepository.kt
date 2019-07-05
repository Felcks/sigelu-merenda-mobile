package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Nucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Usuario
import com.lemobs_sigelu.gestao_estoques.exceptions.UsuarioSemNucleoException
import io.reactivex.Observable

/**
 * Created by felcks on Jul, 2019
 */
class UsuarioRepository {

    val api = RestApi()

    fun getUsuario(authorization: String, usuarioID: Int): Observable<Usuario> {


        return Observable.create { subscriber ->
            val callResponse = api.getUsuario(authorization, usuarioID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                if(response.body() == null)
                    subscriber.onError(Throwable(""))


                if(response.body()!!.nucleo_id != null && response.body()!!.nucleo != null){

                    val usuario = Usuario(response.body()!!.id, Nucleo(response.body()!!.nucleo_id ?: 0,
                        response.body()!!.nucleo!!.nome ?: "",
                        response.body()!!.nucleo!!.sigla ?: "")
                    )
                    subscriber.onNext(usuario)
                }
                else{
                    subscriber.onError(UsuarioSemNucleoException())
                }
            }
            else{
                subscriber.onError(Throwable(""))
            }
        }

    }
}