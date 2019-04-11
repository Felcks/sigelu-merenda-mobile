package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.content.Context
import com.lemobs_sigelu.gestao_estoques.LISTA_MATERIAIS_DE_PEDIDOS_MOCKADOS
import com.lemobs_sigelu.gestao_estoques.common.domain.model.MaterialDePedido
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable

class FluxoEntregaMaterialPedidoRepository {

    private fun checarCorretudeDosMateriais(list: List<MaterialDePedido>): Boolean{

        var existeItemModificado = false
        for(item in list){

            if(item.entregue > 0){
                existeItemModificado = true
            }

            if(item.recebido + item.entregue > item.contratado){
                return false
            }
        }

        return existeItemModificado
    }

    private fun concluirEntregaDeMateriais(list: List<MaterialDePedido>){

        for (item in list){
            item.recebido += item.entregue
            item.entregue = 0.0
        }
    }

    fun enviarEntregaDeMateriais(context: Context, list: List<MaterialDePedido>): Observable<Boolean> {

        if(!checarCorretudeDosMateriais(list)){
            return Observable.create { subscriber ->
                subscriber.onNext(false)
                subscriber.onComplete()
            }
        }

        return Observable.create { subscriber ->

            this.concluirEntregaDeMateriais(list)
            subscriber.onNext(true)
            subscriber.onComplete()
        }
    }


}