package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio2
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Recebimento2

interface CadastraRecebimentoModel: Fluxo {

    fun setPedidoEstoqueID(pedidoEstoqueID: Int)

    fun iniciaRecebimento(pedidoEstoqueID: Int, pedidoEstoqueEnvioID: Int)
    fun cadastraQuantidadeEObservacaoMaterial(listaItemEstoqueID: List<Int>, listaValor: List<Double>, listaObservacao: List<String>)

    fun cancelaRecebimento()
    fun confirmaRecebimento()

    fun getRecebimento(): Recebimento2?

    suspend fun getListaEnvio(): List<Envio2>
    suspend fun getListaItemEnvio(): List<ItemEnvio>
}