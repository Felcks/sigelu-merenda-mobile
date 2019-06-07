package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

/**
 * Created by felcks on Jun, 2019
 */
class CarregaListaItensDeEnvioRepository {

    val api = RestApi()

    fun getItensEnvio(envio: Envio): Observable<List<ItemEnvio>>{

        return Observable.create { subscriber->

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val callResponse = api.getItensEnvioDePedido(pedidoEstoqueID, envio.envioID)
            val response = callResponse.execute()

            if(response.isSuccessful && response.body() != null){

                val itens = response.body()!!.map{

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

                    ItemEnvio(it.id,
                        envio,
                        it.quantidade_unidade ?: 0.0,
                        it.preco_unidade ?: 0.0,
                        itemEstoque,
                        categoria)
                }

                subscriber.onNext(itens)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}