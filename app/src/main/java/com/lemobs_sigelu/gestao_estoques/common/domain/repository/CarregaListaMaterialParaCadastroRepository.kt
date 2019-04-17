package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Material
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
                when(pedido.destino){
                    PedidoDeCadastro.Companion.PedidoDestino.NUCLEO -> {
                        val list = removeMateriaisJaCadastrados(MATERIAIS_PARA_CADASTRO_OBRA.toMutableList())
                        subscribe.onNext(list)
                    }
                    PedidoDeCadastro.Companion.PedidoDestino.OBRA -> {
                        val list = removeMateriaisJaCadastrados(MATERIAIS_PARA_CADASTRO_NUCLEO.toMutableList())
                        subscribe.onNext(list)
                    }
                }
            }
            subscribe.onComplete()
        }
    }

    private fun removeMateriaisJaCadastrados(materiaisCandidatos: MutableList<MaterialParaCadastro>): MutableList<MaterialParaCadastro>{

        for (m in materiaisCandidatos) {
            if (materiaisCadastrados.contains(m))
                materiaisCandidatos.remove(m)
        }

        return materiaisCandidatos
    }
}