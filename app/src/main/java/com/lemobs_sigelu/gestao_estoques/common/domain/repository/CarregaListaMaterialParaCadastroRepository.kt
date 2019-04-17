package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoDeCadastro
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class CarregaListaMaterialParaCadastroRepository {

    fun getMateriais(context: Context): Observable<List<MaterialParaCadastro>> {

        return Observable.create { subscribe ->
            val pedido = pedidoDeCadastro
            if(pedido != null){
                if(pedido.destino == PedidoDeCadastro.Companion.PedidoDestino.NUCLEO){
                    subscribe.onNext(MATERIAIS_PARA_CADASTRO_OBRA)
                }
                else if(pedido.destino == PedidoDeCadastro.Companion.PedidoDestino.OBRA){
                    subscribe.onNext(MATERIAIS_PARA_CADASTRO_NUCLEO)
                }
            }
            subscribe.onComplete()
        }
    }
}