package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio2
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEnvio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Recebimento2

interface CadastraRecebimentoModel {

    fun iniciaRecebimento(pedidoEstoqueID: Int, pedidoEstoqueEnvioID: Int)
    fun cadastraQuantidadeEObservacaoMaterial(listaItemEstoqueID: List<Int>, listaValor: List<Double>, listaObservacao: List<String>)

    fun cancelaRecebimento()
    fun confirmaRecebimento()

    fun getRecebimento(): Recebimento2

    fun getListaEnvio(): List<Envio2>
    fun getListaItemEnvio(): List<ItemEstoque>
}