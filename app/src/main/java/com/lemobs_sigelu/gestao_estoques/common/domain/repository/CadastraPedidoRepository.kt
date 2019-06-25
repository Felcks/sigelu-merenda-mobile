package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.SITUACAO_APROVADO_ID
import com.lemobs_sigelu.gestao_estoques.SITUACAO_EM_ANALISE_ID
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.ItemPedidoCadastroDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoDataRequestFornecedorNucleo
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoDataRequestNucleoNucleo
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoDataRequestNucleoObra
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoCadastro
import io.reactivex.Observable

class CadastraPedidoRepository {

    val api = RestApi()

    fun cadastraPedido(pedidoCadastro: PedidoCadastro): Observable<Unit> {

        return Observable.create { subscriber->

            data class Tupla(val id: Int?, val id2: Int?)

            val (origemNucleoID, origemFornecedorID) = when(pedidoCadastro.origemTipo ?: ""){
                "Fornecedor" -> Tupla(null, pedidoCadastro.origemID ?: 0)
                "Núcleo" -> Tupla(pedidoCadastro.origemID, null)
                else -> Tupla(null, null)
            }

            val (destinoNucleoID, destinoObraID) = when(pedidoCadastro.destinoTipo ?: ""){
                "Núcleo" -> Tupla(pedidoCadastro.destinoID, null)
                "Obra" -> Tupla(null, pedidoCadastro.destinoID)
                else -> Tupla(null, null)
            }

            val situacaoID = when(pedidoCadastro.origemTipo){
                "Núcleo" -> SITUACAO_APROVADO_ID
                "Fornecedor" -> SITUACAO_EM_ANALISE_ID
                else -> 2
            }


            val callResponse = if(pedidoCadastro.origemTipo == "Fornecedor") {

                val pedidoDataRequest = PedidoDataRequestFornecedorNucleo(
                    pedidoCadastro.origemTipo ?: "",
                    origemFornecedorID,
                    pedidoCadastro.destinoTipo ?: "",
                    destinoNucleoID,
                    situacaoID,
                    1,
                    listOf(
                        ItemPedidoCadastroDataRequest(
                            pedidoCadastro.itemContrato?.categoria?.categoria_id ?: 0,
                            pedidoCadastro.itemContrato?.itemEstoqueID ?: 0,
                            pedidoCadastro.itemContrato?.precoUnidade ?: 0.0,
                            pedidoCadastro.itemContrato?.quantidadeRecebida ?: 0.0
                        )
                    )
                )

                api.postPedidoFornecedorNucleo(pedidoDataRequest)
            }
            else if(pedidoCadastro.destinoTipo == "Núcleo") {

                val pedidoDataRequest = PedidoDataRequestNucleoNucleo(
                    pedidoCadastro.origemTipo ?: "",
                    origemNucleoID,
                    pedidoCadastro.destinoTipo ?: "",
                    destinoNucleoID,
                    1,
                    situacaoID,
                    listOf(
                        ItemPedidoCadastroDataRequest(
                            pedidoCadastro.itemContrato?.categoria?.categoria_id ?: 0,
                            pedidoCadastro.itemContrato?.itemEstoqueID ?: 0,
                            pedidoCadastro.itemContrato?.precoUnidade ?: 0.0,
                            pedidoCadastro.itemContrato?.quantidadeRecebida ?: 0.0
                        )
                    )
                )

                api.postPedidoNucleoNucleo(pedidoDataRequest)
            }
            else{

                val pedidoDataRequest = PedidoDataRequestNucleoObra(
                    pedidoCadastro.origemTipo ?: "",
                    origemNucleoID,
                    pedidoCadastro.destinoTipo ?: "",
                    destinoObraID,
                    situacaoID,
                    1,
                    listOf(
                        ItemPedidoCadastroDataRequest(
                            pedidoCadastro.itemContrato?.categoria?.categoria_id ?: 0,
                            pedidoCadastro.itemContrato?.itemEstoqueID ?: 0,
                            pedidoCadastro.itemContrato?.precoUnidade ?: 0.0,
                            pedidoCadastro.itemContrato?.quantidadeRecebida ?: 0.0
                        )
                    )
                )

                api.postPedidoNucleoObra(pedidoDataRequest)
            }

            val response = callResponse.execute()

            if(response.isSuccessful){

                subscriber.onNext(response.body()!!)
                subscriber.onComplete()
            }
            else{

                subscriber.onError(Throwable(response.message()))
            }

        }
    }
}