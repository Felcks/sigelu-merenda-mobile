package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Categoria
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemContrato
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida
import io.reactivex.Observable

class ItemContratoRepository {

    val api = RestApi()

    fun carregaListaItemContrato(contratoID: Int): Observable<List<ItemContrato>> {

        return Observable.create { subscriber ->

            val callResponse = api.getItensContrato(contratoID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                val itensOrcamento = with(response.body()!!){

                    this.itens.map {

                        ItemContrato(it.id,
                            it.contrato_estoque_orcamento_id,
                            it.numeracao ?: "",
                            it.quantidade_unidade ?: 0.0,
                            it.preco_unidade ?: 0.0,
                            it.memoria_calculo ?: "",
                            it.valor_total ?: 0.0,
                            Categoria(it.categoria.id, it.categoria.nome ?: ""),
                            it.item_estoque.id,
                            with(it.item_estoque){
                                ItemEstoque(this.id,
                                    this.codigo ?: "",
                                    this.descricao ?: "",
                                    this.nome_alternativo ?: "",
                                    with(unidade_medida){
                                        UnidadeMedida(this.id,
                                            this.nome ?: "",
                                            this.sigla ?: "")
                                    }
                                )
                            })
                    }
                }

                subscriber.onNext(itensOrcamento)
                subscriber.onComplete()
            }
            else{

                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}