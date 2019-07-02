package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Categoria
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

/**
 * Created by felcks on Jul, 2019
 */
class ItemEnvioRepository {

    val api = RestApi()

    fun getListaItemEnvio(envio: Envio): Observable<List<ItemEnvio>> {

        return Observable.create { subscriber->

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val callResponse = api.getItensEnvioDePedido(pedidoEstoqueID, envio.envioID)
            val response = callResponse.execute()

            if(response.isSuccessful && response.body() != null){

                val itens = response.body()!!.map{

                    val unidadeMedida = with(it.item_estoque.unidade_medida){
                        com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida(
                            this.id,
                            this.nome ?: "",
                            this.sigla ?: ""
                        )
                    }

                    val itemEstoque = with(it.item_estoque){
                        com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque(
                            this.id,
                            this.codigo ?: "",
                            this.descricao ?: "",
                            this.nome_alternativo ?: "",
                            unidadeMedida
                        )
                    }

                    val categoria = Categoria(it.categoria.id,
                        it.categoria.nome ?: "")

                    ItemEnvio(it.id,
                        envio.envioID,
                        it.quantidade_unidade ?: 0.0,
                        it.preco_unidade ?: 0.0,
                        categoria,
                        itemEstoque.id,
                        itemEstoque)
                }

                subscriber.onNext(itens)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
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