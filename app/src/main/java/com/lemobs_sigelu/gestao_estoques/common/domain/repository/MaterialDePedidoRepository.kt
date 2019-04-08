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

    private fun checarCorretudeDosMateriais(list: List<MaterialDePedido>): Boolean{

        for(item in list){

            if(item.recebido + item.entregue > item.contratado ){
                return false
            }
        }
        return true
    }

    fun enviarEntregaDeMateriais(list: List<MaterialDePedido>): Observable<Boolean> {

        if(!checarCorretudeDosMateriais(list)){
            return Observable.create { subscriber ->
                subscriber.onNext(false)
                subscriber.onComplete()
            }
        }

        return Observable.create { subscriber ->
            subscriber.onNext(true)
            subscriber.onComplete()
        }
    }
}