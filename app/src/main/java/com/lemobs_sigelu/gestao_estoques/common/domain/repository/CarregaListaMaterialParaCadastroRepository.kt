package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.ItemEnvioDAO
import com.lemobs_sigelu.gestao_estoques.bd.MaterialDeCadastroDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.model.PedidoDeCadastro
import io.reactivex.Observable

class CarregaListaMaterialParaCadastroRepository {

    fun getMateriais(envioID: Int): Observable<List<ItemEnvio>> {

        return Observable.create { subscribe ->

            val dao = db.itemEnvioDAO()
            val envios = dao.getTodosItemEnvioDeEnvio(envioID)

            if(envios.isNotEmpty()) {
                val itemEstoqueDAO = db.itemEstoqueDAO()
                for (envio in envios) {
                    envio.itemEstoque = itemEstoqueDAO.getById(envio.itemEstoqueID ?: 0)
                }

                subscribe.onNext(envios)
                subscribe.onComplete()
            }
            else{
                subscribe.onError(Throwable("Lista de materiais vazia"))
            }
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