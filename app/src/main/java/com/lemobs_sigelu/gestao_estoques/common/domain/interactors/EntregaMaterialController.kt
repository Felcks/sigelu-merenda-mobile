package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemPedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaItemDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EntregaMaterialDoPedidoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaPedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class EntregaMaterialController @Inject constructor(val carregaListaItemPedidoRepository: CarregaListaItemDoPedidoRepository,
                                                    val visualizarPedidoRepository: CarregaPedidoRepository,
                                                    val fluxoEntregaMaterialPedidoRepository: EntregaMaterialDoPedidoRepository) {

    fun getListaMaterialPedido(context: Context): Observable<List<ItemPedido>> {
        return carregaListaItemPedidoRepository.getListaItemPedido(0)
    }

    fun enviarEntregaDeMateriais(context: Context, list: List<ItemPedido>): Observable<Boolean>{
        return fluxoEntregaMaterialPedidoRepository.enviarEntregaDeMateriais(context, list)
    }

}