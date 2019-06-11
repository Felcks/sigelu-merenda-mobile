package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialParaCadastroRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaMaterialParaPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class SelecionaMaterialPedidoController @Inject constructor(private val carregaListaMaterialPedidoRepository: CarregaListaMaterialParaCadastroRepository,
                                                            private val selecionaMaterialParaCadastroRepository: SelecionaMaterialParaPedidoRepository) {

    fun carregaMateriais(envioID: Int): Observable<List<ItemEnvio>> {
        return carregaListaMaterialPedidoRepository.getMateriais(envioID)
    }

    fun selecionaMaterial(materialId: Int): Boolean{
        return selecionaMaterialParaCadastroRepository.selecionaMaterial(materialId)
    }
}