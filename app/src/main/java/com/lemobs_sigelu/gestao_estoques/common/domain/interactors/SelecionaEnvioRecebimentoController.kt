package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.CarregaListaEnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.SelecionaEnvioRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class SelecionaEnvioRecebimentoController @Inject constructor(private val carregaListaEnvioRepository: CarregaListaEnvioRepository,
                                                              private val selecionaEnvioRecebimentoRepository: SelecionaEnvioRepository) {

    fun getListaEnvioBD(): Observable<List<Envio>> {
        return carregaListaEnvioRepository.getListaEnvioBD()
    }

    fun selecionaEnvio(envioID: Int): Int{
        return selecionaEnvioRecebimentoRepository.selecionaEnvio(envioID)
    }
}