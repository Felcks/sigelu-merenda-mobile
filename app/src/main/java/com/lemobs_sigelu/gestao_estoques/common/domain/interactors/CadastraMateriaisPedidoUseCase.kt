package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialParaCadastro
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialCadastradoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CadastraPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class CadastraMateriaisPedidoUseCase @Inject constructor(val carregaListaMaterialCadastradoRepository: CarregaListaMaterialCadastradoRepository,
                                                         val cadastraPedidoRepository: CadastraPedidoRepository) {

    fun carregaMateriais(context: Context): Observable<List<MaterialParaCadastro>> {
        return carregaListaMaterialCadastradoRepository.carregaMateriais(context)
    }

    fun confirmaPedido(context: Context): Boolean{
        return cadastraPedidoRepository.cadastraPedido(context)
    }

    fun cancelaPedido(context: Context){
        cadastraPedidoRepository.cancelaPedido(context)
    }
}