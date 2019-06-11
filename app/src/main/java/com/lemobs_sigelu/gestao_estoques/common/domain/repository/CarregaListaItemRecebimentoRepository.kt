package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.db
import com.lemobs_sigelu.gestao_estoques.materiaisCadastrados
import io.reactivex.Observable

class CarregaListaItemRecebimentoRepository {

    fun getListaItemRecebimento(): Observable<List<ItemRecebimento>> {

        return Observable.create { subscribe ->

            val dao = db.itemRecebimentoDAO()
            val listaItemRecebimento = dao.getAll()

            val itemEnvioDAO = db.itemEnvioDAO()
            val itemEstoqueDAO = db.itemEstoqueDAO()
            for(itemRecebimento in listaItemRecebimento){

                val itemEnvio = itemEnvioDAO.getById(itemRecebimento.itemEnvioID ?: 0)
                val itemEstoque = itemEstoqueDAO.getById(itemEnvio?.itemEstoqueID ?: 0)
                itemEnvio?.itemEstoque = itemEstoque
                itemRecebimento.itemEnvio = itemEnvio
            }

            subscribe.onNext(listaItemRecebimento)
            subscribe.onComplete()
        }
    }
}