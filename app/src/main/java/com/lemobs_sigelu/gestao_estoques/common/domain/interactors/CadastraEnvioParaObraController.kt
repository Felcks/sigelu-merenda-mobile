package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio2
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra
import io.reactivex.Observable

interface CadastraEnvioParaObraController: Fluxo {

    suspend fun carregaListagemObra(): List<Obra>
    fun selecionaObra(obraID: Int)

    suspend fun carregaListagemItemEstoque(): List<ItemEstoque>
    fun veriricaSeItemJaEstaAdicionado(id: Int): Boolean
    fun getIDsDeItemAdicionados(): List<Int>
    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemEstoque>, listaParaRemover: List<ItemEstoque>)

    fun confirmaCadastroItem(listaValoresRecebidos: List<Double>, listaObservacao: List<String>)
    fun getItensCadastrados(): List<ItemEstoque>
    fun removeItem(id: Int)

    fun cancelaEnvio()
    suspend fun registraEnvio(observacoes: List<String>)
    fun getEnvio(): Envio2?
}