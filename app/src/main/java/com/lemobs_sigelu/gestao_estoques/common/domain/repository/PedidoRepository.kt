package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Situacao
import com.lemobs_sigelu.gestao_estoques.common.domain.model.SituacaoPedido
import com.lemobs_sigelu.gestao_estoques.extensions_constants.createdAtToDate
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class PedidoRepository {

    val api = RestApi()

    fun getPedidoBD(pedidoID: Int): Pedido?{

        val dao = db.pedidoDAO()
        val pedido = dao.getById(pedidoID)

        return pedido
    }

    fun getPedido(pedidoEstoqueID: Int): Observable<Pedido> {

        return Observable.create { subscribe ->

            val callResponse = api.getPedido(pedidoEstoqueID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                val pedido = with(response.body()!!) {

                    data class Tupla(val id: Int?, val nome: String?)

                    val (origemID, origemNome) = when(tipo_origem){
                        "Fornecedor" -> Tupla(origem_fornecedor_id, origem_fornecedor?.nome)
                        "Núcleo" -> Tupla(origem_nucleo_id, origem_nucleo?.nome)
                        else -> Tupla(null, null)
                    }

                    val (destinoID, destinoNome) = when(tipo_destino){
                        "Núcleo" -> Tupla(destino_nucleo_id, destino_nucleo?.nome)
                        "Obra" -> Tupla(destino_obra_direta_id, destino_obra_direta?.ordem_servico?.codigo)
                        else -> Tupla(null, null)
                    }

                    Pedido(
                        this.id,
                        this.codigo ?: "",
                        this.tipo_origem ?: "",
                        this.tipo_destino ?: "",
                        origemID,
                        destinoID,
                        origemNome,
                        destinoNome,
                        this.data_aprovacao?.createdAtToDate(),
                        null,
                        Situacao(
                            this.situacao.id,
                            this.situacao.nome
                        )
                    )
                }

                this.salvaPedidoBD(pedido)
                subscribe.onNext(pedido)
                subscribe.onComplete()
            }
            else{


                subscribe.onError(Throwable(response.message()))
            }
        }
    }

    private fun salvaPedidoBD(pedido: Pedido) {

        val dao  = db.pedidoDAO()
        dao.insertAll(pedido)
    }

    fun salvaListaPedidoBD(lista: List<Pedido>){
        db.pedidoDAO().insertAll(*lista.toTypedArray())
    }

    fun getListaPedido(): Observable<List<Pedido>> {

        return Observable.create { subscriber ->

            val callResponse = api.getListaPedido()
            val response = callResponse.execute()

            if(response.isSuccessful){


                val list = response.body()?.map {

                    data class Tupla(val id: Int?, val nome: String?)

                    val (origemID, origemNome) = when(it.tipo_origem){
                        "Fornecedor" -> Tupla(it.origem_fornecedor_id, it.origem_fornecedor?.nome)
                        "Núcleo" -> Tupla(it.origem_nucleo_id, it.origem_nucleo.nome)
                        else -> Tupla(null, null)
                    }

                    val (destinoID, destinoNome) = when(it.tipo_destino){
                        "Núcleo" -> Tupla(it.destino_nucleo_id, it.destino_nucleo?.nome)
                        "Obra" -> Tupla(it.destino_obra_direta_id, it.destino_obra_direta?.ordem_servico?.codigo)
                        else -> Tupla(null, null)
                    }

                    Pedido(it.id,
                        it.codigo ?: "",
                        it.tipo_origem ?: "",
                        it.tipo_destino ?: "",
                        origemID,
                        destinoID,
                        origemNome,
                        destinoNome,
                        null,
                        null,
                        Situacao(it.situacao.id, it.situacao.nome)
                    )
                }
                subscriber.onNext(list ?: listOf())
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.errorBody().toString()))
            }

        }
    }

    fun getListaPedidoBD(): List<Pedido> {

        return db.pedidoDAO().getAll()
    }

    fun getSituacoesDePedido(): Observable<List<SituacaoPedido>> {

        return Observable.create { subscribe ->

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val callResponse = api.getSituacoesDePedido(pedidoEstoqueID)
            val response = callResponse.execute()

            if(response.isSuccessful && response.body() != null){

                val situacoes = response.body()!!.map {
                    SituacaoPedido(0,
                        Situacao(it.situacao_id, it.situacao.nome),
                        it.data.createdAtToDate(),
                        it.justificativa_situacao ?: "")
                }

                subscribe.onNext(situacoes)
                subscribe.onComplete()
            }
            else{

                subscribe.onError(Throwable(""))
            }
        }
    }


}