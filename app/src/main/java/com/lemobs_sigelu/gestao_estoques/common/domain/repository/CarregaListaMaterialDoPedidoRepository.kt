package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.LISTA_MATERIAIS_DE_PEDIDOS_MOCKADOS
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.PedidoDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CarregaListaMaterialDoPedidoRepository {

    fun getMateriaisDePedido(context: Context): Observable<List<MaterialDePedido>> {

        return Observable.create { subscribe ->

            val b = getMateriaisPedidoBD(context) ?: listOf()
            subscribe.onNext(getMateriaisPedidoBD(context) ?: listOf())
            subscribe.onComplete()
        }
    }

    fun getMateriaisPedidoBD(context: Context): List<MaterialDePedido>?{

        val pedidoID = FlowSharedPreferences.getPedidoId(context)
        val pedidoDAO = PedidoDAO(DatabaseHelper.connectionSource)
        return pedidoDAO.queryForId(pedidoID)?.getEquivalentDomain()?.materiais
    }
}