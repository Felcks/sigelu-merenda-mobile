package com.lemobs_sigelu.gestao_estoques.common.domain.repository

import android.util.Log
import com.lemobs_sigelu.gestao_estoques.*
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.cadastra_envio.EnvioDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.cadastra_envio.ItemEnvioDataRequest
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.*
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

                if(response.body()!!.isEmpty())
                    subscriber.onNext(listOf())
                else {
                    val envios = response.body()!!.map {

                        val dataSaida = if (it.data_saida != null && it.hora_saida != null) {
                            it.data_saida.plus("/${it.hora_saida}").anoMesDiaHoraMinutoSegundoToDate()
                        } else {
                            null
                        }

                        Envio(it.id,
                            pedidoID,
                            it.situacao ?: "",
                            it.codigo ?: "",
                            dataSaida,
                            it.data_recebimento?.createdAtToDate() ?: Date(),
                            it.flag_entregue ?: false,
                            it.responsavel?.nome ?: "",
                            listOf(),
                            it.recebimento_estoque?.id,
                            if (it.recebimento_estoque != null) Recebimento(it.recebimento_estoque.id, null) else null
                        )
                    }

                    subscriber.onNext(envios)
                    subscriber.onComplete()
                }
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

    fun postEnvio(envio: Envio): Observable<Unit>{

        return Observable.create { subscriber ->

            val envioDataRequest = EnvioDataRequest(
                envio.itens.map {
                    ItemEnvioDataRequest(
                        it.itemEstoqueID ?: 0,
                        it.quantidadeRecebida ?: 0.0
                    )
                }
            )

            val callResponse = api.postEnvio(envio.pedidoID, envioDataRequest)
            val response = callResponse.execute()

            if(response.isSuccessful){
                subscriber.onNext(response.body()!!)
                subscriber.onComplete()
            }
            else{
                subscriber.onError(Throwable(response.errorBody()?.string()))
            }
        }
    }

    suspend fun postEnvio2(pedidoEstoqueID: Int, envio: Envio2) {

        val envioDataRequest = EnvioDataRequest(
            envio.listaItemEstoque.map {
                ItemEnvioDataRequest(
                    it.id,
                    it.quantidadeRecebida ?: 0.0
                )
            }
        )

        return api.postEnvio2(pedidoEstoqueID, envioDataRequest)
    }

    fun salvaEnvio(envio: Envio){

        val dao = db.envioDAO()
        dao.insertAll(envio)
    }
}