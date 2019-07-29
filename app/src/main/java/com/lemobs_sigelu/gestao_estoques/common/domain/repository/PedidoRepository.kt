package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.ItemPedidoCadastroDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoDataRequestFornecedorNucleo
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoDataRequestNucleoNucleo
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoDataRequestNucleoObra
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.*
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import java.util.*

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
                        null,
                        null,
                        null,
                        Situacao(
                            this.situacao.id,
                            this.situacao.nome
                        )
                    )
                }

                val contrato = if(response.body()!!.contrato_estoque != null) {
                    with(response.body()!!.contrato_estoque!!) {

                        ContratoEstoque(
                            id ?: 0,
                            situacao ?: "",
                            objeto_contrato ?: "",
                            numero_contrato ?: "",
                            valor_contratual ?: 0.0,
                            data_inicio?.anoMesDiaToDate() ?: Date(),
                            data_conclusao?.anoMesDiaToDate() ?: Date(),
                            empresa_id
                        )
                    }
                }
                else{
                    null
                }
                pedido.contrato = contrato

                this.salvaPedidoBD(pedido)
                val pedidoBD = this.getPedidoBD(pedidoEstoqueID)!!
                pedidoBD.contrato = contrato
                subscribe.onNext(pedidoBD)
                subscribe.onComplete()
            }
            else{


                subscribe.onError(Throwable(response.message()))
            }
        }
    }

    private fun salvaPedidoBD(pedido: Pedido) {

        val dao  = db.pedidoDAO()
        dao.updateItem(pedido.id,
            pedido.codigo,
            pedido.origem,
            pedido.destino)
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

                    val dataUltimoEnvio = if(it.data_ultimo_envio != null && it.hora_ultimo_envio != null){
                        "${it.data_ultimo_envio}/${it.hora_ultimo_envio}".anoMesDiaHoraMinutoSegundoToDate()
                    }
                    else{
                        null
                    }

                    Pedido(
                        it.id,
                        it.codigo ?: "",
                        it.tipo_origem ?: "",
                        it.tipo_destino ?: "",
                        origemID,
                        destinoID,
                        origemNome,
                        destinoNome,
                        it.created_at?.createdAtToDate(),
                        dataUltimoEnvio,
                        it.data_ultimo_recebimento?.createdAtToDate(),
                        Situacao(it.situacao.id, it.situacao.nome)
                    )
                }

                this.salvaListaPedidoBD(list ?: listOf())
                subscriber.onNext(getListaPedidoBD())
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

    fun salvaPedido(pedido: Pedido){
        db.pedidoDAO().insertAll(pedido)
    }

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

                val isRascunho = pedidoCadastro.situacao?.situacao_id == 1

                val pedidoDataRequest = PedidoDataRequestFornecedorNucleo(
                    pedidoCadastro.origemTipo ?: "",
                    origemFornecedorID,
                    pedidoCadastro.destinoTipo ?: "",
                    destinoNucleoID,
                    situacaoID,
                    isRascunho,
                    pedidoCadastro.listaItemContrato.map {
                        ItemPedidoCadastroDataRequest(
                            it.categoria?.categoria_id ?: 0,
                            it.itemEstoqueID ?: 0,
                            it.precoUnidade ?: 0.0,
                            it.quantidadeRecebida ?: 0.0
                        )
                    }
                )

                api.postPedidoFornecedorNucleo(pedidoDataRequest)
            }
            else if(pedidoCadastro.destinoTipo == "Núcleo") {

                val isRascunho = pedidoCadastro.situacao?.situacao_id == 1

                val pedidoDataRequest = PedidoDataRequestNucleoNucleo(
                    pedidoCadastro.origemTipo ?: "",
                    origemNucleoID,
                    pedidoCadastro.destinoTipo ?: "",
                    destinoNucleoID,
                    1,
                    isRascunho,
                    pedidoCadastro.listaItemContrato.map {
                        ItemPedidoCadastroDataRequest(
                            it.categoria?.categoria_id ?: 0,
                            it.itemEstoqueID ?: 0,
                            it.precoUnidade ?: 0.0,
                            it.quantidadeRecebida ?: 0.0
                        )
                    }
                )

                api.postPedidoNucleoNucleo(pedidoDataRequest)
            }
            else{

                val isRascunho = pedidoCadastro.situacao?.situacao_id == 1

                val pedidoDataRequest = PedidoDataRequestNucleoObra(
                    pedidoCadastro.origemTipo ?: "",
                    origemNucleoID,
                    pedidoCadastro.destinoTipo ?: "",
                    destinoObraID,
                    situacaoID,
                    isRascunho,
                    pedidoCadastro.listaItemContrato.map {
                        ItemPedidoCadastroDataRequest(
                            it.categoria?.categoria_id ?: 0,
                            it.itemEstoqueID ?: 0,
                            it.precoUnidade ?: 0.0,
                            it.quantidadeRecebida ?: 0.0
                        )
                    }
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