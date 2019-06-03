package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDeCadastroDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoDeCadastro
import io.reactivex.Observable

class CarregaListaMaterialParaCadastroRepository {

    fun getMateriais(context: Context): Observable<List<MaterialParaCadastro>> {

        return Observable.create { subscribe ->
            val pedido = pedidoDeCadastro

            if(pedido != null){
                when(pedido.destino){
                    PedidoDeCadastro.Companion.PedidoDestino.NUCLEO -> {

                        val materialDeCadastroDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
//                        var list = materialDeCadastroDAO.queryForAll().map { it.getEquivalentDomain() }.toMutableList()
//                        list = removeMateriaisJaCadastrados(list)
//                        val list = removeMateriaisJaCadastrados(MATERIAIS_PARA_CADASTRO_OBRA.toMutableList())
                        //subscribe.onNext(list)
                    }
                    PedidoDeCadastro.Companion.PedidoDestino.OBRA -> {
//                        val list = removeMateriaisJaCadastrados(MATERIAIS_PARA_CADASTRO_NUCLEO.toMutableList())
                        val materialDeCadastroDAO = MaterialDeCadastroDAO(DatabaseHelper.connectionSource)
//                        var list = materialDeCadastroDAO.queryForAll().map { it.getEquivalentDomain() }.toMutableList()
//                        list = removeMateriaisJaCadastrados(list)
//                        subscribe.onNext(list)
                    }
                }
            }
            subscribe.onComplete()
        }
    }

    private fun removeMateriaisJaCadastrados(materiaisCandidatos: MutableList<MaterialParaCadastro>): MutableList<MaterialParaCadastro>{

        val trueMaterial = mutableListOf<MaterialParaCadastro>()
        for (m in materiaisCandidatos) {
            var jaCadastrado = false
            for (i in materiaisCadastrados){
                if (i.id == m.id) {
                    jaCadastrado = true
                    break
                }
            }
            if(!jaCadastrado)
                trueMaterial.add(m)
        }

        return trueMaterial
    }
}