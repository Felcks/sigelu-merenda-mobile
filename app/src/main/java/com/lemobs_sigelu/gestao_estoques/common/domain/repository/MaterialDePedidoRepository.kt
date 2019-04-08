package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.MATERIAL_DE_PEDIDO_MOCKADOS
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.pedido_1
import io.reactivex.Observable

class MaterialDePedidoRepository {

    fun getMateriaisDePedido(): Observable<List<MaterialDePedido>> {

        return Observable.create { subscriber ->

            subscriber.onNext(MATERIAL_DE_PEDIDO_MOCKADOS)
            subscriber.onComplete()
        }
    }

    fun getTituloDePedido(): String = pedido_1.getCodigoFormatado()

}