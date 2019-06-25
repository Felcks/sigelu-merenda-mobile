package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class EnvioRepository {

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

    fun getListaEnvioDePedidoBD(pedidoID: Int): Observable<List<Envio>> {

        return Observable.create { subscriber->

            val envioDAO = db.envioDAO()
            val envios = envioDAO.getTodosEnviosDePedido(pedidoID)

            if(envios.isNotEmpty()){

                val itemEnvioDAO = db.itemEnvioDAO()
                for(envio in envios){

                    val listaItemEnvio = itemEnvioDAO.getTodosItemEnvioDeEnvio(envio.envioID ?: 0)
                    for(itemEnvio in listaItemEnvio){

                        val itemEstoqueDAO = db.itemEstoqueDAO()
                        itemEnvio.itemEstoque = itemEstoqueDAO.getById(itemEnvio.itemEstoqueID ?: 0)
                    }

                    envio.itens = listaItemEnvio.toMutableList()
                }
                subscriber.onNext(envios)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable("Nenhum envio encontrado"))
            }
        }
    }


    companion object {
        var envioParaCadastro: Envio? = null
    }

    fun cadastraInformacoesIniciais(motorista: String,
                                    dataSaida: Date){

        envioParaCadastro = Envio(
            0,
            FlowSharedPreferences.getPedidoId(App.instance),
            null,
            null,
            isEntregue = false,
            responsavel = AppSharedPreferences.getUserName(App.instance),
            motorista = motorista,
            dataSaida = dataSaida)
    }
}