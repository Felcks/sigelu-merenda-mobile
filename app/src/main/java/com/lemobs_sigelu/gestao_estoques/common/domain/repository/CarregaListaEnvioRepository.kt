package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class CarregaListaEnvioRepository {

    val api = RestApi()

    fun getListaEnvio(): Observable<List<Envio>> {

        return Observable.create { subscriber ->

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val callResponse = api.getEnviosDePedido(pedidoEstoqueID)
            val response = callResponse.execute()

            if(response.isSuccessful && response.body() != null){

                val envios = response.body()!!.map {

                    Envio(it.id,
                        it.situacao ?: "",
                        it.codigo ?: "",
                        it.data_saida?.anoMesDiaToDate() ?: Date(),
                        it.hora_saida?.horaMinutoSegundoToDate() ?: Date(),
                        it.data_recebimento?.toDateCreatedAt() ?: Date(),
                        it.flag_entregue ?: false,
                        it.responsavel?.nome ?: "")
                }

                subscriber.onNext(envios)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}