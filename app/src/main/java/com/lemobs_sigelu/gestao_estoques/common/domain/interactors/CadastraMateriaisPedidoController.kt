package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.GerenciaRecebimentoRepository
import io.reactivex.Observable
import javax.inject.Inject

class CadastraMateriaisPedidoController @Inject constructor(val carregaListaItemRecebimentoRepository: CarregaListaItemRecebimentoRepository,
                                                            val cadastraPedidoRepository: CadastraPedidoRepository,
                                                            val gerenciaRecebimentoRepository: GerenciaRecebimentoRepository) {

    fun carregaListaItemRecebimento(): Observable<List<ItemRecebimento>> {
        return carregaListaItemRecebimentoRepository.getListaItemRecebimento()
    }

    fun confirmaPedido(context: Context): Boolean{
        return cadastraPedidoRepository.cadastraPedido(context)
    }

    fun cancelaPedido(){
        gerenciaRecebimentoRepository.apagarListaItemRecebimentoAnteriores()
    }
}