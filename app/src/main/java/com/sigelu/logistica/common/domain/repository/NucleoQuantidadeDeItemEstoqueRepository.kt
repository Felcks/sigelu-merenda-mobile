package com.sigelu.logistica.common.domain.repository

import com.sigelu.logistica.api.RestApi
import com.sigelu.logistica.common.domain.model.NucleoQuantidadeDeItemEstoque
import io.reactivex.Observable

class NucleoQuantidadeDeItemEstoqueRepository {

    val api = RestApi()

    fun carregaListaNucleoQuantidade(itemEstoqueID: Int): Observable<List<NucleoQuantidadeDeItemEstoque>>{

        return Observable.create { subscriber ->

            val callResponse = api.getListaNucleoQuantidadeDeItemEstoque(itemEstoqueID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                val nucleos = response.body()!!.map {
                    NucleoQuantidadeDeItemEstoque(it.id,
                        it.nome,
                        it.sigla,
                        it.quantidade_disponivel)
                }

                subscriber.onNext(nucleos)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}