package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.ItemRecebimentoDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento.RecebimentoDataRequest
import com.lemobs_sigelu.gestao_estoques.db
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CadastraRecebimentoRepository {

    val api = RestApi()

    fun enviaRecebimento(): Observable<Boolean> {

        return Observable.create { subscriber->

            val pedidoID = FlowSharedPreferences.getPedidoId(App.instance)
            val envioID = FlowSharedPreferences.getPedidoId(App.instance)

            val pedidoDAO = db.pedidoDAO()
            val pedido = pedidoDAO.getById(pedidoID)

            if(pedido == null){
                subscriber.onError(Throwable("Pedido não existe, cadastre o recebimento de novo."))
            }

            val origemID = pedido?.origemID
            val destinoID = pedido?.destinoID

            if(origemID == null) {
                subscriber.onError(Throwable("Pedido sem origem"))
            }
            if(destinoID == null) {
                subscriber.onError(Throwable("Pedido sem destino"))
            }


            val itemRecebimentoDAO = db.itemRecebimentoDAO()
            val itemEnvioDAO = db.itemEnvioDAO()
            val itemEstoqueDAO = db.itemEstoqueDAO()

            val listaItemRecebimento = itemRecebimentoDAO.getAll()

            for(itemRecebimento in listaItemRecebimento){
                val itemEnvio = itemEnvioDAO.getById(itemRecebimento.itemEnvioID ?: 0)
                if(itemEnvio != null) {
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

            if(response.isSuccessful){
                subscriber.onNext(true)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }

//        if(pedidoCadastro == null)
//            return false
//
//        val destino = if (pedidoCadastro!!.destino == PedidoCadastro.Companion.PedidoDestino.NUCLEO){
//            pedidoCadastro!!.destino.nome
//        }
//        else{
//            "Obra ${pedidoCadastro!!.obra?.codigo}"
//        }
//
//        val pedido = Pedido(100,
//            "1800099",
//            "Centro",
//            destino,
//            Date(),
//            Date(),
//            Situacao(1, "Em análise"),
//            listOf<SituacaoHistorico>(SituacaoHistorico(10, "Em enálise", Date(), listOf())),
//            materiaisCadastrados.map {
//                ItemPedido(it.id,
//                    it.base,
//                    it.getQuantidadePedida(),
//                    0.0)
//            }
//        )
//
//
//        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
//        val pedidoDTO = pedido.getEquivalentDTOParaAdicao()
//        pedidoDAO.add(pedidoDTO)
//        val materiaisDTO = pedido.materiais.map {
//            it.getEquivalentDTOParaAdicao(pedidoDTO)
//        }
//        pedidoDTO.materiais = materiaisDTO
//        pedidoDTO.codigo = "33000${pedidoDTO.id}"
//        pedidoDAO.add(pedidoDTO)
//
//        val materialDePedidoDAO = MaterialDePedidoDAO(DatabaseHelper.connectionSource)
//        for(i in pedido.materiais) {
//            materialDePedidoDAO.add(i.getEquivalentDTO(pedidoDTO))
//        }
//
//        val materialDeCadastroDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
//        for (item in materiaisCadastrados) {
//            item.quantidade_disponivel -= item.getQuantidadePedida()
//            materialDeCadastroDAO.add(item.getEquivalentDTO())
//        }
//
//        val situacaoHistoricoDAO = SituacaoHistoricoDAO(DatabaseHelper.connectionSource)
//        val situacaoHistoricoDTO = SituacaoHistoricoDTO(null, "Em análise", Date(), pedidoDTO)
//        situacaoHistoricoDAO.add(situacaoHistoricoDTO)
//
//        materiaisCadastrados.removeAll { true }
//        return true
    }
}