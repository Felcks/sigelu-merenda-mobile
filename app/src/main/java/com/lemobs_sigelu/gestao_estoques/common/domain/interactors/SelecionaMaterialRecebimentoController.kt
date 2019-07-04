package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemEnvioParaRecebimentoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaMaterialParaPedidoRepository
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

class SelecionaMaterialRecebimentoController @Inject constructor(private val carregaListaItemEnvioPedidoRepository: CarregaListaItemEnvioParaRecebimentoRepository,
                                                                 private val selecionaMaterialParaCadastroRepository: SelecionaMaterialParaPedidoRepository) {

    fun carregaMateriais(): Observable<List<ItemEnvio>> {

        val envioID = FlowSharedPreferences.getEnvioId(App.instance)
        return carregaListaItemEnvioPedidoRepository.getMateriais(envioID)
    }

    fun selecionaMaterial(materialId: Int): Boolean{
        return selecionaMaterialParaCadastroRepository.selecionaMaterial(materialId)
    }
}