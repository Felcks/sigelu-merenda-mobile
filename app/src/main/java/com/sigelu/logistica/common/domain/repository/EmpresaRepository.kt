package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.api.RestApi
import com.sigelu.logistica.common.domain.model.Empresa
import com.sigelu.logistica.common.domain.model.TipoEmpresa
import io.reactivex.Observable

/**
 * Created by felcks on Jun, 2019
 */
class EmpresaRepository {

    val restApi = RestApi()

    fun carregaListaEmpresa(): Observable<List<Empresa>> {

        return Observable.create { subscriber ->

            val callResponse = restApi.getEmpresas()
            val response = callResponse.execute()

            if(response.isSuccessful){

                val empresas = response.body()!!.map {

                    Empresa(
                        it.id,
                        it.nome ?: "",
                        TipoEmpresa(
                            it.id,
                            it.nome ?: ""
                        )
                    )
                }

                subscriber.onNext(empresas)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}