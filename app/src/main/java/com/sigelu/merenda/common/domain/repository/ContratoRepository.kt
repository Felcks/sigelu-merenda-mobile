package com.sigelu.merenda.common.domain.repository

import com.sigelu.merenda.api.RestApi
import com.sigelu.merenda.common.domain.model.ContratoEstoque
import com.sigelu.merenda.extensions_constants.toDate
import io.reactivex.Observable
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class ContratoRepository{

    val api = RestApi()

    fun carregaListaContratosVigentes(): Observable<List<ContratoEstoque>>{

        return Observable.create { subscriber ->

            val callResponse = api.getContratos()
            val response = callResponse.execute()

            if(response.isSuccessful){

                val list = response.body()!!.filter { it.situacao == "Vigente" }.map {

                    ContratoEstoque(
                        it.id,
                        it.situacao ?: "",
                        it.objeto_contrato ?: "",
                        it.numero_contrato ?: "",
                        it.valor_contratual ?: 0.0,
                        it.data_conclusao?.toDate() ?: Date(),
                        it.data_inicio?.toDate() ?: Date(),
                        it.empresa_id
                    )
                }

                subscriber.onNext(list)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}