package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import io.reactivex.Observable

class CarregaListaItemEnvioParaRecebimentoRepository {

    fun getMateriais(envioID: Int): Observable<List<ItemEnvio>> {

        return Observable.create { subscribe ->

            val dao = db.itemEnvioDAO()
            var listaItemEnvio = dao.getTodosItemEnvioDeEnvio(envioID)

            val listaItemRecebimentoJaCadastrado = getListaItemRecebimento()
            val listaIdsItemReceimentoJaCadastrado = listaItemRecebimentoJaCadastrado.map { it.itemEnvioID }

            listaItemEnvio = listaItemEnvio.filter { x -> !listaIdsItemReceimentoJaCadastrado.contains(x.id) }
            if(listaItemEnvio.isNotEmpty()) {

                val itemEstoqueDAO = db.itemEstoqueDAO()
                for (itemEnvio in listaItemEnvio) {
                    itemEnvio.itemEstoque = itemEstoqueDAO.getById(itemEnvio.itemEstoqueID ?: 0)
                }

                subscribe.onNext(listaItemEnvio)
                subscribe.onComplete()
            }
            else{
                subscribe.onError(Throwable("Lista de materiais vazia"))
            }
        }
    }

    fun getListaItemRecebimento(): List<ItemRecebimento> {

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

        return listaItemRecebimento
    }
}