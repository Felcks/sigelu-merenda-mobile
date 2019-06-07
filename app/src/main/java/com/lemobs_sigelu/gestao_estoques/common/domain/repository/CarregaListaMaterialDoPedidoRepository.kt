package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Categoria
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.UnidadeMedida
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CarregaListaMaterialDoPedidoRepository {

    val api = RestApi()

    fun getMateriaisDePedido(): Observable<List<ItemPedido>> {

        return Observable.create { subscribe ->

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val callResponse = api.getItensDePedido(pedidoEstoqueID)
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
                        it.quantidade_unidade ?: 0.0,
                        it.preco_unidade ?: 0.0,
                        itemEstoque,
                        categoria)
                }

                subscribe.onNext(itens)
                subscribe.onComplete()
            }
            else{
                subscribe.onError(Throwable(response.message()))
            }
        }
    }
}