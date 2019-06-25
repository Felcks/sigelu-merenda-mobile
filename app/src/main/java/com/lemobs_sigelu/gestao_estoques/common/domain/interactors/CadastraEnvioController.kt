package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import java.util.*
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioController @Inject constructor(val envioRepository: EnvioRepository){

    fun cadastraInformacoesIniciaisPedido(motorista: String,
                                          dataSaida: Date){

        envioRepository.cadastraInformacoesIniciais(motorista, dataSaida)
    }
}