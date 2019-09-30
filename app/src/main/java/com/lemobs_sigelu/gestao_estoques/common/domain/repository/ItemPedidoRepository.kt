package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.pedido_item.ItemPedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Categoria
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida
import com.lemobs_sigelu.gestao_estoques.extensions_constants.SITUACAO_EM_ANALISE_ID
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by felcks on Jul, 2019
 */
class ItemPedidoRepository {

    val api = RestApi()

    fun getListaItemPedido(pedidoID: Int, pedidoSituacaoID: Int = 0): Observable<List<ItemPedido>> {

        return Observable.create { subscribe ->

            val callResponse: Call<List<ItemPedidoDataResponse>> = api.getItensDePedido(pedidoID)
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

                    val item = ItemPedido(it.id,
                        pedidoID,
                        if(pedidoSituacaoID == SITUACAO_EM_ANALISE_ID) 0.0 else it.quantidade_unidade ?: 0.0,
                        0.0,
                        itemEstoque.id,
                        null,
                        itemEstoque)

                    item.quantidadeDisponivel = if(pedidoSituacaoID == SITUACAO_EM_ANALISE_ID) 0.0 else it.quantidade_disponivel ?: 0.0
                    item.quantidadeSolicitada =  it.quantidade_solicitada ?: 0.0
                    item.observacao = it.observacao ?: ""
                    item
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

    fun salvaListaItemPedido(lista: List<ItemPedido>){

        val itemEstoqueDAO = db.itemEstoqueDAO()
        for(item in lista){
            if(item.itemEstoque != null){
                itemEstoqueDAO.insertAll(item.itemEstoque!!)
            }
        }

        val dao = db.itemPedidoDAO()
        dao.insertAll(*lista.toTypedArray())
    }

    fun salvaItemPedido(item: ItemPedido){

        val dao = db.itemPedidoDAO()
        dao.insertAll(item)
    }

}