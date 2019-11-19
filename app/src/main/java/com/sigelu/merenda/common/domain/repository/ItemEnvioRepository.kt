package com.sigelu.merenda.common.domain.repository

import com.sigelu.merenda.api.RestApi
import com.sigelu.merenda.api_model.pedido_item.ItemPedidoDataResponse
import com.sigelu.merenda.common.domain.model.Envio
import com.sigelu.merenda.common.domain.model.ItemEnvio
import com.sigelu.merenda.exceptions.ListaVaziaException
import com.sigelu.merenda.extensions_constants.db
import io.reactivex.Observable
import retrofit2.Call

/**
 * Created by felcks on Jul, 2019
 */
class ItemEnvioRepository {

    val api = RestApi()

    fun getListaItemEnvio(envio: Envio): Observable<List<ItemEnvio>> {
        return getListaItemEnvio(envio.envioID, envio.pedidoID)
    }

    fun getListaItemEnvio(envioID: Int, pedidoID: Int): Observable<List<ItemEnvio>> {

        return Observable.create { subscriber ->

            val callResponse: Call<List<ItemPedidoDataResponse>> = api.getItensEnvioDePedido(pedidoID, envioID)
            val response = callResponse.execute()

            if (response.isSuccessful && response.body() != null) {

                val itens = response.body()!!.map {

                    val unidadeMedida = with(it.item_estoque.unidade_medida) {
                        com.sigelu.merenda.common.domain.model.UnidadeMedida(
                            this.id,
                            this.nome ?: "",
                            this.sigla ?: ""
                        )
                    }

                    val itemEstoque = with(it.item_estoque) {
                        com.sigelu.merenda.common.domain.model.ItemEstoque(
                            this.id,
                            this.codigo ?: "",
                            this.descricao ?: "",
                            this.nome_alternativo ?: "",
                            unidadeMedida
                        )
                    }

                    val item = ItemEnvio(
                        it.id,
                        envioID,
                        it.quantidade_unidade ?: 0.0,
                        0.0,
                        null,
                        itemEstoque.id,
                        itemEstoque
                    )

                    item.quantidadeDisponivel = it.quantidade_disponivel
                    item.observacao = it.observacao ?: ""
                    item
                }

                subscriber.onNext(itens)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    suspend fun getListaItemEnvio2(pedidoEstoqueID: Int, pedidoEstoqueEnvioID: Int): List<ItemEnvio>?{

        val response = api.getListaItemEnvio(pedidoEstoqueID, pedidoEstoqueEnvioID)

        return response.body()?.map {

            val unidadeMedida = with(it.item_estoque.unidade_medida) {
                com.sigelu.merenda.common.domain.model.UnidadeMedida(
                    this.id,
                    this.nome ?: "",
                    this.sigla ?: ""
                )
            }

            val itemEstoque = with(it.item_estoque) {
                com.sigelu.merenda.common.domain.model.ItemEstoque(
                    this.id,
                    this.codigo ?: "",
                    this.descricao ?: "",
                    this.nome_alternativo ?: "",
                    unidadeMedida
                )
            }

            val item = ItemEnvio(
                it.id,
                pedidoEstoqueEnvioID,
                it.quantidade_unidade ?: 0.0,
                0.0,
                null,
                itemEstoque.id,
                itemEstoque
            ).apply {
                this.observacao = it.observacao ?: ""
                this.pedidoEstoqueID = pedidoEstoqueID
            }

            item.quantidadeDisponivel = it.quantidade_disponivel
            item
        }
    }

    fun getListaItemEnvioBD(envioID: Int): Observable<List<ItemEnvio>> {

        return Observable.create { subscribe ->

            val dao = db.itemEnvioDAO()
            var listaItemEnvio = dao.getTodosItemEnvioDeEnvio(envioID)

            if(listaItemEnvio.isNotEmpty()){
                subscribe.onNext(listaItemEnvio)
                subscribe.onComplete()
            }
            else{
                subscribe.onError(ListaVaziaException())
            }
//            val listaItemRecebimentoJaCadastrado = getListaItemRecebimento()
//            val listaIdsItemReceimentoJaCadastrado = listaItemRecebimentoJaCadastrado.map { it.itemEnvioID }
//
//            listaItemEnvio = listaItemEnvio.filter { x -> !listaIdsItemReceimentoJaCadastrado.contains(x.id) }
//            if(listaItemEnvio.isNotEmpty()) {
//
//                val itemEstoqueDAO = db.itemEstoqueDAO()
//                for (itemEnvio in listaItemEnvio) {
//                    itemEnvio.itemEstoque = itemEstoqueDAO.getById(itemEnvio.itemEstoqueID ?: 0)
//                }
//
//                subscribe.onNext(listaItemEnvio)
//                subscribe.onComplete()
//            }
//            else{
//                subscribe.onError(Throwable("Lista de materiais vazia"))
//            }
        }
    }

    fun salvaListaItemEnvio(lista: List<ItemEnvio>){

        val itemEstoqueDAO = db.itemEstoqueDAO()
        for(item in lista){
            if(item.itemEstoque != null)
                itemEstoqueDAO.insertAll(item.itemEstoque!!)
        }

        val dao = db.itemEnvioDAO()
        dao.insertAll(*lista.toTypedArray())
    }

    fun salvaItemEnvio(itemEnvio: ItemEnvio){

        val dao = db.itemEnvioDAO()
        dao.insertAll(itemEnvio)
    }

}