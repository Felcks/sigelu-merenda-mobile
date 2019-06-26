package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
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