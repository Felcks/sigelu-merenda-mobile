package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialParaCadastroRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaMaterialParaPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class SelecionaMaterialPedidoController @Inject constructor(private val carregaListaMaterialPedidoRepository: CarregaListaMaterialParaCadastroRepository,
                                                            private val selecionaMaterialParaCadastroRepository: SelecionaMaterialParaPedidoRepository) {

    fun carregaMateriais(context: Context): Observable<List<MaterialParaCadastro>> {
        return carregaListaMaterialPedidoRepository.getMateriais(context)
    }

    fun selecionaMaterial(context: Context, materialId: Int): Boolean{
        return selecionaMaterialParaCadastroRepository.selecionaMaterial(context, materialId)
    }
}