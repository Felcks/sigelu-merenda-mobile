package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.item_recebimento.ItemRecebimentoDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import io.reactivex.Observable
import retrofit2.Call

class ItemRecebimentoRepository2{

    val api = RestApi()

    fun getListaItemRecebimento(recebimentoID: Int): Observable<List<ItemRecebimento>> {

        return Observable.create {subscriber ->

            val callResponse: Call<List<ItemRecebimentoDataResponse>> = api.getListaItemRecebimento(recebimentoID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                val listaItemRecebimento = response.body()!!.map {
                    ItemRecebimento(
                        it.id,
                        null,
                        it.quantidade_unidade ?: 0.0
                    ).apply {
                        this.observacao = it.observacao ?: ""
                    }
                }

                subscriber.onNext(listaItemRecebimento)
                subscriber.onComplete()

            }
            else{
                subscriber.onError(Throwable(""))
            }
        }
    }
}