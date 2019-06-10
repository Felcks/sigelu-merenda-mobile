package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Pedido
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaEnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaEnvioRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaEnvioRecebimentoController @Inject constructor(private val carregaListaEnvioRepository: CarregaListaEnvioRepository,
                                                              private val selecionaEnvioRecebimentoRepository: SelecionaEnvioRepository) {

    fun getListaEnvioDePedido(pedidoID: Int): Observable<List<Envio>> {
        return carregaListaEnvioRepository.getListaEnvioDePedidoBD(pedidoID)
    }

    fun selecionaEnvio(envioID: Int): Int{
        return selecionaEnvioRecebimentoRepository.selecionaEnvio(envioID)
    }
}