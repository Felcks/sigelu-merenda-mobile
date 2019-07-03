package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.ItemNaoSelecionavelException
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException
import com.lemobs_sigelu.gestao_estoques.extensions_constants.isConnected
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jul, 2019
 */
class CadastraRecebimentoController @Inject constructor(private val envioRepository: EnvioRepository) {

    private var listaEnvio = listOf<Envio>()

    fun getListaEnvioDePedido(pedidoID: Int): Observable<List<Envio>> {

        if(isConnected(App.instance)) {
            return envioRepository.getListaEnvio(pedidoID)
        }
        else{
            return envioRepository.getListaEnvioDePedidoBD(pedidoID)
        }
    }

    fun armazenaListaEnvio(list: List<Envio>){

        this.listaEnvio = list
    }

    fun selecionaEnvio(envioID: Int?){

        if(envioID == null){
            throw NenhumItemSelecionadoException()
        }

        val envio = this.listaEnvio.getOrNull(envioID) ?: throw ItemNaoSelecionavelException()

        if(envio.isEntregue){
            throw ItemNaoSelecionavelException()
        }

        FlowSharedPreferences.setEnvioId(App.instance, envio.envioID)
    }
}