package com.sigelu.merenda.common.domain.interactors

import com.sigelu.merenda.common.domain.model.*

interface CadastraRecebimentoModel: Fluxo {

    fun setPedidoEstoqueID(pedidoEstoqueID: Int)

    fun iniciaRecebimento(pedidoEstoqueID: Int, pedidoEstoqueEnvioID: Int)
    fun cadastraQuantidadeEObservacaoMaterial(listaItemRecebimento: List<ItemRecebimento2>)

    fun cancelaRecebimento()
    suspend fun confirmaRecebimento()

    fun getRecebimento(): Recebimento2?

    suspend fun getListaEnvio(): List<Envio2>
    suspend fun getListaItemEnvio(): List<ItemEnvio>
}