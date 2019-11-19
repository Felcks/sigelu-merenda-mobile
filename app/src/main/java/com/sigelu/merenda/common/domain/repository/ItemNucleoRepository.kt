package com.sigelu.merenda.common.domain.repository

import com.sigelu.merenda.api.RestApi
import com.sigelu.merenda.common.domain.model.ItemEstoque
import com.sigelu.merenda.common.domain.model.ItemNucleo
import com.sigelu.merenda.common.domain.model.UnidadeMedida
import io.reactivex.Observable

class ItemNucleoRepository {

    val api = RestApi()

    fun getListaItemNucleo(nucleoID: Int): Observable<List<ItemNucleo>> {

        return Observable.create { subscriber ->

            val callResponse = api.getListaItemNucleo(nucleoID)
            val response = callResponse.execute()

            if(response.isSuccessful){

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
            else{
                subscriber.onError(Throwable(""))
            }
        }
    }

    fun getListaItemEstoqueDeNucleo(nucleoID: Int): Observable<List<ItemEstoque>> {

        return Observable.create { subscriber ->

            val callResponse = api.getListaItemEstoqueDeNucleo(nucleoID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                if(response.body() == null)
                    subscriber.onNext(listOf())
                else if(response.body()!!.isEmpty())
                    subscriber.onNext(listOf())
                else {

                    val list = response.body()!!.map{
                        val item = ItemEstoque(
                            it.id,
                            it.codigo ?: "",
                            it.descricao ?: "",
                            it.nome_alternativo ?: "",
                            with(it.unidade_medida){
                                UnidadeMedida(
                                    this.id,
                                    this.nome ?: "",
                                    this.sigla ?: ""
                                )
                            }
                        )

                        item.apply {
                            saldoContrato = it.saldo_contrato
                            quantidadeDisponivel = it.quantidade_disponivel
                        }
                        item
                    }

                    subscriber.onNext(list)
                    subscriber.onComplete()
                }
            }
            else{
                subscriber.onError(Throwable(""))
            }
        }
    }
}