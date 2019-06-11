package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemEnvioParaRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaMaterialParaPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class SelecionaMaterialPedidoController @Inject constructor(private val carregaListaItemEnvioPedidoRepository: CarregaListaItemEnvioParaRecebimentoRepository,
                                                            private val selecionaMaterialParaCadastroRepository: SelecionaMaterialParaPedidoRepository) {

    fun carregaMateriais(envioID: Int): Observable<List<ItemEnvio>> {
        return carregaListaItemEnvioPedidoRepository.getMateriais(envioID)
    }

    fun selecionaMaterial(materialId: Int): Boolean{
        return selecionaMaterialParaCadastroRepository.selecionaMaterial(materialId)
    }
}