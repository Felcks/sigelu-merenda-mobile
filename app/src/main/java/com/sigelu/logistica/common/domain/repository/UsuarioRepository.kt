package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.api.RestApi
import com.sigelu.logistica.common.domain.model.Nucleo
import com.sigelu.logistica.common.domain.model.Usuario
import com.sigelu.logistica.exceptions.UsuarioSemNucleoException
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
                else if(response.body()!!.nucleo_id != null && response.body()!!.nucleo != null){

                    val usuario = Usuario(response.body()!!.id,
                        Nucleo(response.body()!!.nucleo_id ?: 0,
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