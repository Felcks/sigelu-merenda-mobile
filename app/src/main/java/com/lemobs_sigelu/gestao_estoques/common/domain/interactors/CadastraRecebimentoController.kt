package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.ItemRecebimentoDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.RecebimentoDataRequest
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import com.lemobs_sigelu.gestao_estoques.extensions_constants.isConnected
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jul, 2019
 */
class CadastraRecebimentoController @Inject constructor(private val envioRepository: EnvioRepository,
                                                        private val itemEnvioRepository: ItemEnvioRepository,
                                                        private val itemRecebimentoRepository: ItemRecebimentoRepository) {

    private var listaEnvio = listOf<Envio>()
    private val api = RestApi()

    fun getListaEnvioDePedido(pedidoID: Int): Observable<List<Envio>> {

        if(isConnected(App.instance)) {
            return envioRepository.getListaEnvio(pedidoID)
        }
        else{
            return envioRepository.getListaEnvioDePedidoBD(pedidoID)
        }
    }

    fun armazenaListaEnvio(list: List<Envio>){

        this.listaEnvio = list
    }

    fun apagaTodaListaItemRecebimentoAnterior(){
        itemRecebimentoRepository.apagaTodosItemRecebimento()
    }

    fun selecionaEnvio(envioID: Int?){

        if(envioID == null){
            throw NenhumItemSelecionadoException()
        }

        val envio = this.listaEnvio.getOrNull(envioID) ?: throw ItemNaoSelecionavelException()

        if(envio.isEntregue){
            throw ItemNaoSelecionavelException()
        }

        FlowSharedPreferences.setEnvioId(App.instance, envio.envioID)
    }

    fun getListaItemEnvio(): Observable<List<ItemEnvio>>{

        val envioID = FlowSharedPreferences.getEnvioId(App.instance)
        val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)

        return if(isConnected(App.instance)){
            itemEnvioRepository.getListaItemEnvio(envioID, pedidoID)
        }
        else{
            itemEnvioRepository.getListaItemEnvioBD(envioID)
        }
    }

    fun  filtrarParaItensNaoCadastrados(listaGeral: List<ItemEnvio>): List<ItemEnvio>{

        val listaItemRecebimentoJaCadastrado = itemRecebimentoRepository.getListaItemRecebimento()
        val listaIdsItemReceimentoJaCadastrado = listaItemRecebimentoJaCadastrado.map { it.itemEnvioID }

        val listaItemEnvio = listaGeral.filter { x -> !listaIdsItemReceimentoJaCadastrado.contains(x.id) }
        if(listaItemEnvio.isNotEmpty()) {

            val itemEstoqueDAO = db.itemEstoqueDAO()
            for (itemEnvio in listaItemEnvio) {
                itemEnvio.itemEstoque = itemEstoqueDAO.getById(itemEnvio.itemEstoqueID ?: 0)
            }

            return listaItemEnvio
        }
        else{
            throw NenhumItemDisponivelException()
        }
    }

    fun selecionaItem(itemEnvioID: Int){

        val itemEnvioDAO = db.itemEnvioDAO()
        val itemEnvio = itemEnvioDAO.getById(itemEnvioID)

        val itemEstoqueDAO = db.itemEstoqueDAO()
        itemEnvio?.itemEstoque = itemEstoqueDAO.getById(itemEnvio?.itemEstoqueID ?: 0)

        val quantidade = itemEnvio?.quantidadeUnidade ?: 0.0

        if(quantidade < 0)
            throw ItemSemQuantidadeDisponivelException()

        FlowSharedPreferences.setItemEnvioID(App.instance, itemEnvioID)
    }

    fun getItemSolicitado(): ItemEnvio?{

        val itemEnvioID = FlowSharedPreferences.getItemEnvioID(App.instance)
        val itemEnvioDAO = db.itemEnvioDAO()
        val itemEstoqueDAO = db.itemEstoqueDAO()

        val item = itemEnvioDAO.getById(itemEnvioID)
        item?.itemEstoque = itemEstoqueDAO.getById(item?.itemEstoqueID ?: 0)

        return item
    }

    fun confirmaCadastroItem(valor: Double){

        if(valor <= 0.0){
            throw ValorMenorQueZeroException()
        }

        /* Gerando o Item Recebimento */
        val itemEnvioID = FlowSharedPreferences.getItemEnvioID(App.instance)

        val itemRecebimento = ItemRecebimento(
            null,
            itemEnvioID,
            valor
        )

        /* Resgatando o Item Envio Equivalente */
        val itemEnvioDAO = db.itemEnvioDAO()
        val itemEnvio = itemEnvioDAO.getById(itemEnvioID)
        if(itemEnvio != null){
            itemEnvio.itemEstoque = db.itemEstoqueDAO().getById(itemEnvioID)
        }

        /* Conferindo se temos as quantidade no itemEnvio para inserção */
        if(valor > itemEnvio?.quantidadeUnidade ?: 999999999.0){
            throw ValorMaiorQuePermitidoException()
        }

        /* Inserindo ItemRecebimento */
        val itemRecebimentoDAO = db.itemRecebimentoDAO()
        itemRecebimentoDAO.insertAll(itemRecebimento)
    }

    fun getListaItemRecebimento(): Observable<List<ItemRecebimento>> {

        return Observable.create { subscriber ->
            subscriber.onNext(itemRecebimentoRepository.getListaItemRecebimento())
        }
    }

    fun enviaRecebimento(): Observable<Boolean> {

        return Observable.create { subscriber ->

            val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)
            val envioID = FlowSharedPreferences.getEnvioId(App.instance)

            val pedidoDAO = db.pedidoDAO()
            val pedido = pedidoDAO.getById(pedidoID)

            if (pedido == null) {
                subscriber.onError(Throwable("Pedido não existe, cadastre o recebimento de novo."))
            }

            val origemID = pedido?.origemID
            val destinoID = pedido?.destinoID

            if (origemID == null) {
                subscriber.onError(Throwable("Pedido sem origem"))
            }
            if (destinoID == null) {
                subscriber.onError(Throwable("Pedido sem destino"))
            }


            val itemRecebimentoDAO = db.itemRecebimentoDAO()
            val itemEnvioDAO = db.itemEnvioDAO()
            val itemEstoqueDAO = db.itemEstoqueDAO()

            val listaItemRecebimento = itemRecebimentoDAO.getAll()

            for (itemRecebimento in listaItemRecebimento) {
                val itemEnvio = itemEnvioDAO.getById(itemRecebimento.itemEnvioID ?: 0)
                if (itemEnvio != null) {
                    itemEnvio.itemEstoque = itemEstoqueDAO.getById(itemEnvio.itemEstoqueID ?: 0)
                }

                itemRecebimento.itemEnvio = itemEnvio
            }

            val recebimentoDataRequest = RecebimentoDataRequest(
                envioID,
                origemID!!,
                destinoID!!,
                listaItemRecebimento?.map {
                    ItemRecebimentoDataRequest(
                        it.itemEnvio?.categoria?.categoria_id ?: 0,
                        it.itemEnvio?.itemEstoqueID ?: 0,
                        it.itemEnvio?.precoUnidade ?: 0.0,
                        it.quantidadeRecebida ?: 0.0
                    )
                }
            )


            val callResponse = api.postRecebimentoEstoque(recebimentoDataRequest)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                subscriber.onNext(true)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}