package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.MaterialDePedidoRepository
import io.reactivex.Observable
import javax.inject.Inject

class CarregaListaMaterialPedidoUseCase @Inject constructor(val repository: MaterialDePedidoRepository) {

    fun executa(pedido_id: Int): Observable<List<MaterialDePedido>> {
        return repository.getMateriais(pedido_id)
    }


}