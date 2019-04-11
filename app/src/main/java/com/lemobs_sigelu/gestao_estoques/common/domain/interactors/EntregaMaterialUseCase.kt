package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaMaterialPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.FluxoEntregaMaterialPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.VisualizarPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class EntregaMaterialUseCase @Inject constructor(val carregaListaMaterialPedidoRepository: CarregaListaMaterialPedidoRepository,
                                                 val visualizarPedidoRepository: VisualizarPedidoRepository,
                                                 val fluxoEntregaMaterialPedidoRepository: FluxoEntregaMaterialPedidoRepository) {

    fun getListaMaterialPedido(context: Context): Observable<List<MaterialDePedido>> {
        return carregaListaMaterialPedidoRepository.getMateriaisDePedido(context)
    }

    fun getTituloPedido(context: Context) = visualizarPedidoRepository.getTituloDePedido(context)

    fun enviarEntregaDeMateriais(context: Context, list: List<MaterialDePedido>): Observable<Boolean>{
        return fluxoEntregaMaterialPedidoRepository.enviarEntregaDeMateriais(context, list)
    }

}