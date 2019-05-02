package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDePedidoDAO
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

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
            var i = 1
            for (item in list) {
                item.recebido += item.entregue
                item.entregue = 0.0
                materialPedidaoDAO.add(item.getEquivalentDTO(pedido))
                i += 1
            }
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