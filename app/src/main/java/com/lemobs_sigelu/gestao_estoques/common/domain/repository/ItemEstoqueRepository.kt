package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida
import io.reactivex.Observable

/**
 * Created by felcks on Jul, 2019
 */
class ItemEstoqueRepository {

    val api = RestApi()

    fun carregaListaEstoque(): Observable<List<ItemEstoque>> {

        return Observable.create { subscriber ->

            val callResponse = api.getItensEstoque()
            val response = callResponse.execute()

            if(response.isSuccessful){

                if(response.body() == null)
                    subscriber.onNext(listOf())
                else if(response.body()!!.isEmpty())
                    subscriber.onNext(listOf())
                else{

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
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}