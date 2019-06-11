package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.toDateCreatedAt
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CarregaPedidoRepository {

    val api = RestApi()

    fun getPedido(): Observable<Pedido> {

        return Observable.create { subscribe ->

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val callResponse = api.getPedido(pedidoEstoqueID)
            val response = callResponse.execute()

            if(response.isSuccessful){



                val pedido = with(response.body()!!) {

                    val origemID = when(this.tipo_origem){
                        "Fornecedor" -> this.origem_fornecedor_id
                        "Núcleo" -> this.origem_nucleo_id
                        else -> null
                    }

                    val destinoID = when(this.tipo_destino){
                        "Núcleo" -> this.destino_nucleo_id
                        "Obra" -> this.destino_obra_direta_id
                        else -> null
                    }

                    Pedido(
                        this.id,
                        this.codigo ?: "",
                        this.tipo_origem ?: "",
                        this.tipo_destino ?: "",
                        origemID,
                        destinoID,
                        this.data_aprovacao?.toDateCreatedAt(),
                        null,
                        Situacao(this.situacao.id,
                            this.situacao.nome))
                }

                this.salvaPedido(pedido)
                subscribe.onNext(pedido)
                subscribe.onComplete()
            }
            else{


                subscribe.onError(Throwable(response.message()))
            }
        }
    }

    fun getPedidoBD(): Pedido? {


        return null
            //return pedidoDAO.queryForId(pedidoEstoqueID)?.getEquivalentDomain()
    }

    private fun salvaPedido(pedido: Pedido) {


        //pedidoDAO.add(pedido.getEquivalentDTO())
    }
}