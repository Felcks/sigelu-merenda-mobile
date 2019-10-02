package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida
import io.reactivex.Observable

/**
 * Created by felcks on Jul, 2019
 */
open class ItemEstoqueRepository: BaseRepository() {

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

    suspend fun carregaListaEstoque2(): List<ItemEstoque>? {

        val response = api.getListagemItemEstoque()

        return response.body()?.map {
            val item = ItemEstoque(
                it.id,
                it.codigo ?: "",
                it.descricao ?: "",
                it.nome_alternativo ?: "",
                with(it.unidade_medida){
                    UnidadeMedida(
                        id,
                        nome ?: "",
                        sigla ?: ""
                    )
                }
            )

            item.apply {
                saldoContrato = it.saldo_contrato
                quantidadeDisponivel = it.quantidade_disponivel
            }
            item
        }
    }

    suspend fun carregaListaItemEstoque3(estoqueID: Int): List<ItemEstoque>? {

        val response = api.getListagemItemDeEstoque(estoqueID)

        return response?.body()?.map {
            val item = ItemEstoque(
                it.id,
                it.codigo ?: "",
                it.descricao ?: "",
                it.nome_alternativo ?: "",
                with(it.unidade_medida){
                    com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida(
                        id,
                        nome ?: "",
                        sigla ?: ""
                    )
                }
            )

            item.apply {
                saldoContrato = it.saldo_contrato
                quantidadeDisponivel = it.quantidade_disponivel
            }
            item
        }
    }
}