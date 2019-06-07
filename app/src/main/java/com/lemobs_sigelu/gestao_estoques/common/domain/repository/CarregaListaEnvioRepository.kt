package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import io.reactivex.Observable
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class CarregaListaEnvioRepository {

    val api = RestApi()

    fun getListaEnvio(pedidoID: Int): Observable<List<Envio>> {

        return Observable.create { subscriber ->

            val callResponse = api.getEnviosDePedido(pedidoID)
            val response = callResponse.execute()

            if(response.isSuccessful && response.body() != null){

                val envios = response.body()!!.map {

                    Envio(it.id,
                        pedidoID,
                        it.situacao ?: "",
                        it.codigo ?: "",
                        it.data_saida?.plus("/${it.hora_saida}")?.anoMesDiaHoraMinutoSegundoToDate() ?: Date(),
                        it.data_recebimento?.toDateCreatedAt() ?: Date(),
                        it.flag_entregue ?: false,
                        it.responsavel?.nome ?: "",
                        listOf())
                }

                subscriber.onNext(envios)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun getListaEnvioBD(pedido: Pedido): Observable<List<Envio>> {

        return Observable.create { subscriber->

            //val envioDAO = EnvioDAO(DatabaseHelper.connectionSource)
            //val listaEnvios = envioDAO.queryForTodosEnviosDePedido(pedido.id)

            //if(listaEnvios.isNotEmpty()){
                subscriber.onNext(listOf())
                subscriber.onComplete()
            //}
            //else{
                subscriber.onError(Throwable("Sem envios"))
            //}
        }
    }
}