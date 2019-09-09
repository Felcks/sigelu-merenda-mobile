package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import io.reactivex.Observable

interface CadastraEnvioParaObraController {

    suspend fun carregaListagemObra(): List<Obra>?
    fun selecionaObra(id: Int)

    suspend fun carregaListagemItemEstoque(): List<ItemEstoque>?
    fun veriricaSeItemJaEstaAdicionado(id: Int): Boolean
    fun getIDsDeItemAdicionados(): List<Int>
    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemEstoque>, listaParaRemover: List<ItemEstoque>)

    fun confirmaCadastroItem(listaValoresRecebidos: List<Double>)
    fun getItensCadastrados(): List<ItemEstoque>
    fun removeItem(id: Int)

    fun cancelaEnvio()
    fun registraEnvio()
    fun getEnvio(): Envio?
}