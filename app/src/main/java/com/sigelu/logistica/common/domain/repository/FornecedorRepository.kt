package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.api.RestApi
import com.sigelu.logistica.common.domain.model.ContratoEstoque
import com.sigelu.logistica.common.domain.model.Fornecedor
import com.sigelu.logistica.common.domain.model.TipoFornecedor
import com.sigelu.logistica.extensions_constants.anoMesDiaToDate
import io.reactivex.Observable
import java.util.*

/**
 * Created by felcks on Jul, 2019
 */
class FornecedorRepository {

    val api = RestApi()

    fun carregaListaFornecedor(): Observable<List<Fornecedor>> {

        return Observable.create { subscriber ->

            val callResponse = api.getFornecedores()
            val response = callResponse.execute()

            if(response.isSuccessful){

                val fornecedores = response.body()!!.map {

                    Fornecedor(
                        it.id,
                        it.nome ?: "",
                        TipoFornecedor(
                            it.id,
                            it.nome ?: ""
                        ),
                        it.contratoEstoque?.map { ce ->
                            ContratoEstoque(ce.id,
                                ce.situacao ?: "",
                                ce.objeto_contrato ?: "",
                                ce.numero_contrato ?: "",
                                ce.valor_contratual ?: 0.0,
                                ce.data_inicio?.anoMesDiaToDate() ?: Date(),
                                ce.data_conclusao?.anoMesDiaToDate() ?: Date()
                            )
                        }
                    )
                }

                subscriber.onNext(fornecedores)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}