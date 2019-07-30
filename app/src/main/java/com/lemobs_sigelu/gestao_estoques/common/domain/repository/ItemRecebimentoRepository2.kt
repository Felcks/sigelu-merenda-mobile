package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import io.reactivex.Observable

class ItemRecebimentoRepository2{

    val api = RestApi()

    fun getListaItemRecebimento(recebimentoID: Int): Observable<List<ItemRecebimento>> {

        return Observable.create {subscriber ->

            val callResponse = api.getListaItemRecebimento(recebimentoID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                if(response.body() == null)
                    subscriber.onNext(listOf())
                if(response.body()!!.isEmpty())
                    subscriber.onNext(listOf())
                else {
                    val listaItemRecebimento = response.body()!!.map {
                        ItemRecebimento(
                            it.id,
                            null,
                            it.quantidade_unidade ?: 0.0
                        )
                    }

                    subscriber.onNext(listaItemRecebimento)
                    subscriber.onComplete()
                }
            }
            else{
                subscriber.onError(Throwable(""))
            }
        }
    }
}