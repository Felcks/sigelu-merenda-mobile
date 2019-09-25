package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.pedido.PedidoListagemDataResponse
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.*
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import org.threeten.bp.ZonedDateTime
import retrofit2.Call
import java.util.*

open class PedidoRepository {

    val api = RestApi()

    fun getPedidoBD(pedidoID: Int): Pedido?{

        val dao = db.pedidoDAO()
        val pedido = dao.getById(pedidoID)

        return pedido
    }

    fun getPedido(pedidoEstoqueID: Int): Observable<Pedido> {

        return Observable.create { subscribe ->

            val callResponse: Call<PedidoDataResponse> = api.getPedido(pedidoEstoqueID)
            val response = callResponse.execute()

            if(response.isSuccessful){

                val pedido = with(response.body()!!) {

                    data class Tupla(val id: Int?, val nome: String?)

                    val (origemID, origemNome) = when(this.tipo_origem_id){

                        TIPO_ESTOQUE_ALMOXARIFADO -> Tupla(this.origem_estoque_id, this.origem_estoque?.almoxarifado?.nome)
                        TIPO_ESTOQUE_NUCLEO -> Tupla(this.origem_estoque_id,   this.origem_estoque?.nucleo?.nome)
                        TIPO_ESTOQUE_OBRA -> Tupla(this.origem_estoque_id,  this.origem_estoque?.obra_direta?.ordem_servico?.codigo)
                        TIPO_ESTOQUE_FORNECEDOR -> Tupla(this.origem_fornecedor_id,  this.origem_fornecedor?.nome)
                        else -> Tupla(null, null)
                    }

                    val (destinoID, destinoNome) = when(this.tipo_destino_id){

                        TIPO_ESTOQUE_ALMOXARIFADO -> Tupla(this.destino_estoque_id, this.destino_estoque?.almoxarifado?.nome)
                        TIPO_ESTOQUE_NUCLEO -> Tupla(this.destino_estoque_id,   this.destino_estoque?.nucleo?.nome)
                        TIPO_ESTOQUE_OBRA -> Tupla(this.destino_estoque_id,  this.destino_estoque?.obra_direta?.ordem_servico?.codigo)
                        else -> Tupla(null, null)
                    }

                    val dataUltimoEnvio = if(this.data_ultimo_envio != null && this.hora_ultimo_envio != null){
                        "${this.data_ultimo_envio}/${this.hora_ultimo_envio}".anoMesDiaHoraMinutoSegundoToDate()
                    }
                    else{
                        null
                    }

                    Pedido(
                        this.id,
                        this.codigo ?: "",
                        origemNome ?: "",
                        destinoNome ?: "",
                        origemID,
                        destinoID,
                        origemNome,
                        destinoNome,
                        this.created_at?.createdAtToDate(),
                        dataUltimoEnvio,
                        this.data_ultimo_recebimento?.createdAtToDate(),
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
        dao.updateItem(pedido.id,
            pedido.codigo,
            pedido.origem,
            pedido.destino)
    }

    fun salvaListaPedidoBD(lista: List<Pedido>){
        db.pedidoDAO().insertAll(*lista.toTypedArray())
    }

    fun getListaPedido(): Observable<List<Pedido2>> {

        return Observable.create { subscriber ->

            val callResponse: Call<List<PedidoListagemDataResponse>> = api.getListaPedido()
            val response = callResponse.execute()

            if(response.isSuccessful){

                data class Tupla(val id: Int?, val nome: String?)

                val list = response.body()?.map {

                    val (origemID, origemNome) = when(it.tipo_origem_id){

                        TIPO_ESTOQUE_ALMOXARIFADO -> Tupla(it.origem_estoque_id, it.origem_estoque?.almoxarifado?.nome)
                        TIPO_ESTOQUE_NUCLEO -> Tupla(it.origem_estoque_id,   it.origem_estoque?.nucleo?.nome)
                        TIPO_ESTOQUE_OBRA -> Tupla(it.origem_estoque_id,  it.origem_estoque?.obra_direta?.ordem_servico?.codigo)
                        TIPO_ESTOQUE_FORNECEDOR -> Tupla(it.origem_fornecedor_id,  it.origem_fornecedor?.nome)
                        else -> Tupla(null, null)
                    }

                    val (destinoID, destinoNome) = when(it.tipo_destino_id){

                        TIPO_ESTOQUE_ALMOXARIFADO -> Tupla(it.destino_estoque_id, it.destino_estoque?.almoxarifado?.nome)
                        TIPO_ESTOQUE_NUCLEO -> Tupla(it.destino_estoque_id,   it.destino_estoque?.nucleo?.nome)
                        TIPO_ESTOQUE_OBRA -> Tupla(it.destino_estoque_id,  it.destino_estoque?.obra_direta?.ordem_servico?.codigo)
                        else -> Tupla(null, null)
                    }

                    val dataUltimoEnvio = if(it.data_ultimo_envio != null && it.hora_ultimo_envio != null){
                        "${it.data_ultimo_envio}/${it.hora_ultimo_envio}".anoMesDiaHoraMinutoSegundoToDate()
                    }
                    else{
                        null
                    }

                    val usuario = Usuario(0, Nucleo(0, "Nucleo", "Nucleo"))
                    val movimento = Movimento(
                        0,
                        TipoMovimento.ALMOXARIFADO_PARA_OBRA,
                        Local2(it.tipo_origem_id, origemNome ?: "", origemID ?: 0),
                        Local2(it.tipo_destino_id, destinoNome ?: "", destinoID ?: 0)
                    )

                    Pedido2(
                        it.id,
                        usuario,
                        movimento,
                        it.codigo ?: "",
                        ZonedDateTime.parse(it.created_at),
                        ZonedDateTime.parse(it.created_at),
                        ZonedDateTime.parse(it.created_at),
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

            val itens = when(pedidoCadastro.origemTipo){

                "Fornecedor" -> pedidoCadastro.listaItemContrato.map {
                    ItemPedidoCadastroDataRequest(
                        it.itemEstoqueID ?: 0,
                        it.quantidadeRecebida ?: 0.0,
                        ""
                    )
                }
                else -> pedidoCadastro.listaItemNucleo.map {
                    ItemPedidoCadastroDataRequest(
                        it.id,
                        it.quantidadeRecebida ?: 0.0,
                        ""
                    )
                }
            }


            val callResponse = if(pedidoCadastro.origemTipo == "Fornecedor") {

                val isRascunho = pedidoCadastro.situacao?.situacao_id == 1

                val pedidoDataRequest = PedidoDataRequestFornecedorNucleo(
                    pedidoCadastro.origemTipo ?: "",
                    origemFornecedorID,
                    pedidoCadastro.destinoTipo ?: "",
                    destinoNucleoID,
                    pedidoCadastro.contratoEstoque?.id,
                    isRascunho,
                    itens
                )

                if(pedidoCadastro.isEdicao)
                    api.putPedidoFornecedorNucleo(pedidoCadastro.id ?: 0, pedidoDataRequest)
                else
                    api.postPedidoFornecedorNucleo(pedidoDataRequest)
            }
            else if(pedidoCadastro.destinoTipo == "Núcleo") {

                val isRascunho = pedidoCadastro.situacao?.situacao_id == 1

                val pedidoDataRequest = PedidoDataRequestNucleoNucleo(
                    pedidoCadastro.origemTipo ?: "",
                    origemNucleoID,
                    pedidoCadastro.destinoTipo ?: "",
                    destinoNucleoID,
                    isRascunho,
                    itens
                )
                if(pedidoCadastro.isEdicao)
                    api.putPedidoNucleoNucleo(pedidoCadastro.id ?: 0, pedidoDataRequest)
                else
                    api.postPedidoNucleoNucleo(pedidoDataRequest)
            }
            else{

                val isRascunho = pedidoCadastro.situacao?.situacao_id == 1

                val pedidoDataRequest = PedidoDataRequestNucleoObra(
                    pedidoCadastro.origemTipo ?: "",
                    origemNucleoID,
                    pedidoCadastro.destinoTipo ?: "",
                    destinoObraID,
                    isRascunho,
                    itens
                )

                if(pedidoCadastro.isEdicao)
                    api.putPedidoNucleoObra(pedidoCadastro.id ?: 0, pedidoDataRequest)
                else
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

    suspend fun enviaPedido(pedido: Pedido2, isRascunho: Boolean = false): PedidoResponseOfRequest{

        val pedidoDataRequest = PedidoDataRequest(
            pedido.movimento.origem.tipo_id,
            pedido.movimento.destino.tipo_id,
            pedido.movimento.origem.estoque_id,
            pedido.movimento.destino.estoque_id,
            null,
            null,
            isRascunho,
            pedido.listaMaterial.map {
                ItemPedidoCadastroDataRequest(
                    it.itemEstoque.id,
                    it.quantidadeRecebida,
                    it.observacao
                )
            }
        )

        val a = api.postPedido(pedidoDataRequest)

        return a
    }

    suspend fun cancelaPedido(pedidoEstoqueID: Int){
        return api.cancelaPedido(pedidoEstoqueID)
    }
}