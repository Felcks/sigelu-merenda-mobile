package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import java.util.*
import javax.inject.Inject

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioController @Inject constructor(val envioRepository: EnvioRepository,
                                                  val pedidoRepository: PedidoRepository){

    fun cadastraInformacoesIniciaisPedido(motorista: String,
                                          dataSaida: Date){

        val pedido = pedidoRepository.getPedidoNoBancoPeloID(FlowSharedPreferences.getPedidoId(App.instance))
        envioRepository.cadastraInformacoesIniciais(motorista, dataSaida, pedido)
    }

    fun getItemEnvioSolicitado(): ItemEnvio?{
        return EnvioRepository.envioParaCadastro?.itens?.last()
    }

    fun getItensEnvio(): List<ItemEnvio>?{
        return EnvioRepository.envioParaCadastro?.itens
    }

    fun confirmaCadastroMaterial(valor: Double): Double{

        if(valor <= 0.0){
            return -2.0
        }

        if(valor > EnvioRepository.envioParaCadastro?.itens?.last()?.quantidadeUnidade ?: 999999999.0){
            return -1.0
        }

        EnvioRepository.envioParaCadastro?.itens?.last()?.quantidadeRecebida = valor
        return valor
    }

    fun cancelaEnvio(){
        EnvioRepository.envioParaCadastro = null
    }

//    fun cadastraEnvio(): Observable<Unit>{
//
//
//    }

    fun getEnvio(): Envio? {
        return EnvioRepository.envioParaCadastro
    }
}