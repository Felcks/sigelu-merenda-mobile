package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Fornecedor
import com.lemobs_sigelu.gestao_estoques.common.domain.model.TipoFornecedor
import io.reactivex.Observable

/**
 * Created by felcks on Jul, 2019
 */
class FornecedorRepository {

    val api = RestApi()

    fun carregaListaFornecedor(): Observable<List<Fornecedor>> {

        return Observable.create { subscriber ->

            val callResponse = api.getEmpresas()
            val response = callResponse.execute()

            if(response.isSuccessful){

                val fornecedores = response.body()!!.map {

                    Fornecedor(
                        it.id,
                        it.nome ?: "",
                        TipoFornecedor(
                            it.id,
                            it.nome ?: ""
                        )
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