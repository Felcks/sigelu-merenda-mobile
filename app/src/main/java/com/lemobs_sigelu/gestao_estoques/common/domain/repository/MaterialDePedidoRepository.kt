package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.MATERIAL_DE_PEDIDO_MOCKADOS
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import io.reactivex.Observable

class MaterialDePedidoRepository {

    fun getMateriais(pedido_id: Int): Observable<List<MaterialDePedido>> {

        return Observable.create { subscriber ->

            subscriber.onNext(MATERIAL_DE_PEDIDO_MOCKADOS)
            subscriber.onComplete()
        }
    }
}