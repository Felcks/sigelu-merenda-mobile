package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Categoria
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida
import com.lemobs_sigelu.gestao_estoques.db
import io.reactivex.Observable

class CarregaListaItemDoPedidoRepository {

    val api = RestApi()

    fun getListaItemPedido(pedidoID: Int): Observable<List<ItemPedido>> {

        return Observable.create { subscribe ->

            val callResponse = api.getItensDePedido(pedidoID)
            val response = callResponse.execute()

            if(response.isSuccessful && response.body() != null){

                val itens: List<ItemPedido> = response.body()!!.map {

                    val unidadeMedida = with(it.item_estoque.unidade_medida){
                        UnidadeMedida(this.id,
                            this.nome ?: "",
                            this.sigla ?: "")
                    }

                    val itemEstoque = with(it.item_estoque){
                        ItemEstoque(this.id,
                            this.codigo ?: "",
                            this.descricao ?: "",
                            this.nome_alternativo ?: "",
                            unidadeMedida)
                    }

                    val categoria = Categoria(it.categoria.id,
                        it.categoria.nome ?: "")

                    ItemPedido(it.id,
                        pedidoID,
                        it.quantidade_unidade ?: 0.0,
                        it.preco_unidade ?: 0.0,
                        itemEstoque.id,
                        categoria,
                        itemEstoque)
                }

                subscribe.onNext(itens)
                subscribe.onComplete()
            }
            else{
                subscribe.onError(Throwable(response.message()))
            }
        }
    }

    fun getListaItemPedidoBD(pedidoID: Int): List<ItemPedido> {

        val dao = db.itemPedidoDAO()
        return dao.getTodosItemPedidoDePedido(pedidoID)
    }
}