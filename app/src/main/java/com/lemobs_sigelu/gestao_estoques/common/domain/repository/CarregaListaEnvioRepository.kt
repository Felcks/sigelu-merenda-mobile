package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.bd.DatabaseHelper
import com.lemobs_sigelu.gestao_estoques.bd.EnvioDAO
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class CarregaListaEnvioRepository {

    val api = RestApi()

    fun getListaEnvio(pedido: Pedido): Observable<List<Envio>> {

        return Observable.create { subscriber ->

            val callResponse = api.getEnviosDePedido(pedido.id)
            val response = callResponse.execute()

            if(response.isSuccessful && response.body() != null){

                val envios = response.body()!!.map {

                    Envio(it.id,
                        pedido,
                        it.situacao ?: "",
                        it.codigo ?: "",
                        it.data_saida?.plus("/${it.hora_saida}")?.anoMesDiaHoraMinutoSegundoToDate() ?: Date(),
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

    fun getListaEnvioBD(): Observable<List<Envio>> {

        return Observable.create { subscriber->

            val pedidoEstoqueID = FlowSharedPreferences.getPedidoId(App.instance)
            val envioDAO = EnvioDAO(DatabaseHelper.connectionSource)
            val listaEnvios = envioDAO.queryForTodosEnviosDePedido(pedidoEstoqueID)

            if(listaEnvios.isNotEmpty()){
                subscriber.onNext(listaEnvios.map { it.getEquivalentDomain() })
            }
            else{
                subscriber.onError(Throwable("Sem envios"))
            }
        }
    }
}