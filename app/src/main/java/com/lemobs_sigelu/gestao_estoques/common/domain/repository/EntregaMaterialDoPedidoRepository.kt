package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDePedidoDAO
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.bd.SituacaoHistoricoDAO
import com.lemobs_sigelu.gestao_estoques.bd_model.PedidoDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.SituacaoDTO
import com.lemobs_sigelu.gestao_estoques.bd_model.SituacaoHistoricoDTO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_material_entrega.view.*
import java.util.*

class EntregaMaterialDoPedidoRepository {

    private fun checarCorretudeDosMateriais(list: List<MaterialDePedido>): Boolean{

        var existeItemModificado = false
        for(item in list){

            if(item.entregue > 0){
                existeItemModificado = true
            }

            if(item.recebido + item.entregue > item.contratado){
                return false
            }
        }

        return existeItemModificado
    }

    private fun concluirEntregaDeMateriais(context: Context, list: List<MaterialDePedido>){

        val materialPedidaoDAO = MaterialDePedidoDAO(DatabaseHelper.connectionSource)
        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        val pedidoID = FlowSharedPreferences.getPedidoId(context)
        val pedido = pedidoDAO.queryForId(pedidoID)

        if(pedido != null) {
            for (item in list) {
                item.recebido += item.entregue
                item.entregue = 0.0
                materialPedidaoDAO.add(item.getEquivalentDTO(pedido))
            }

            gerarEntrega(context, list, pedido)
        }

    }

    private fun conferirSeEntregaCompleta(context: Context, list: List<MaterialDePedido>): Boolean {

        for(i in list){
            if(i.recebido < i.contratado)
                return false
        }

        return true
    }

    private fun gerarEntrega(context: Context, list: List<MaterialDePedido>, pedido: PedidoDTO){

        val situacaoHistoricoDAO = SituacaoHistoricoDAO(DatabaseHelper.connectionSource)
        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        val quantidadeSituacoes = pedido.historico_situacoes?.size ?: 2

        if(conferirSeEntregaCompleta(context, list)){

            val situacao = SituacaoHistoricoDTO(null, "Entregue", Date(), pedido)
            situacaoHistoricoDAO.add(situacao)

            if(pedido.historico_situacoes != null) {
                val listaSituacoes = mutableListOf<SituacaoHistoricoDTO>()
                listaSituacoes.addAll(pedido.historico_situacoes!!)
                listaSituacoes.add(situacao)
                pedidoDAO.add(pedido)
            }

            pedido.situacao = SituacaoDTO(3, "Entregue")
            pedidoDAO.add(pedido)
        }
        else{
            val situacao = SituacaoHistoricoDTO(null, "Entrega Parcial ${quantidadeSituacoes-1}", Date(), pedido)
            situacaoHistoricoDAO.add(situacao)

            if(pedido.historico_situacoes != null) {
                val listaSituacoes = mutableListOf<SituacaoHistoricoDTO>()
                listaSituacoes.addAll(pedido.historico_situacoes!!)
                listaSituacoes.add(situacao)
                pedidoDAO.add(pedido)
            }

            pedido.situacao = SituacaoDTO(5, "Parcial")
            pedidoDAO.add(pedido)
        }
    }

    fun enviarEntregaDeMateriais(context: Context, list: List<MaterialDePedido>): Observable<Boolean> {

        if(!checarCorretudeDosMateriais(list)){
            return Observable.create { subscriber ->
                subscriber.onNext(false)
                subscriber.onComplete()
            }
        }

        return Observable.create { subscriber ->

            this.concluirEntregaDeMateriais(context, list)
            subscriber.onNext(true)
            subscriber.onComplete()
        }
    }


}