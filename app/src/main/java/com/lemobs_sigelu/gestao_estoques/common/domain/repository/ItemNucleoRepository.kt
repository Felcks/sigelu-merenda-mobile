package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.item_nucleo.ItemNucleoDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemNucleo
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida
import io.reactivex.Observable

class ItemNucleoRepository {

    val api = RestApi()

    fun getListaItemNucleo(nucleoID: Int): Observable<List<ItemNucleo>> {

        return Observable.create { subscriber ->

            val callResponse = api.getListaItemNucleo(nucleoID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                when {
                    response.body() == null -> subscriber.onNext(listOf())
                    response.body()!!.isEmpty() -> subscriber.onNext(listOf())
                    else -> {

                        val lista = response.body()!!.map {
                            ItemNucleo(it.id,
                                it.codigo,
                                it.nome_alternativo,
                                it.descricao,
                                it.quantidade_disponivel,
                                UnidadeMedida(
                                    it.unidade_medida_id ?: 0,
                                    it.unidade_medida.nome ?: "",
                                    it.unidade_medida.sigla ?: ""
                                )
                            )
                        }
                        subscriber.onNext(lista)
                        subscriber.onComplete()
                    }
                }

            }
            else{
                subscriber.onError(Throwable(""))
            }
        }
    }
}