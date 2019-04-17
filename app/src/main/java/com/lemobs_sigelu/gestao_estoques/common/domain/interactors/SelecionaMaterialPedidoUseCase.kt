package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialParaCadastroRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaMaterialParaPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class SelecionaMaterialPedidoUseCase @Inject constructor(val carregaListaMaterialPedidoRepository: CarregaListaMaterialParaCadastroRepository,
                                                         val selecionaMaterialParaCadastroRepository: SelecionaMaterialParaPedidoRepository) {

    fun carregaMateriais(context: Context): Observable<List<MaterialParaCadastro>> {
        return carregaListaMaterialPedidoRepository.getMateriais(context)
    }

    fun selecionaMaterial(context: Context, materialId: Int): Boolean{
        return selecionaMaterialParaCadastroRepository.selecionaMaterial(context, materialId)
    }
}