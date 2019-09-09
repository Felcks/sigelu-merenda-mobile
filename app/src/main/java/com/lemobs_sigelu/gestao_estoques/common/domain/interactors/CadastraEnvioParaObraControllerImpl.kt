package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.Envio
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemEstoque
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Obra

class CadastraEnvioParaObraControllerImpl: CadastraEnvioParaObraController {

    override suspend fun carregaListagemObra(): List<Obra>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun selecionaObra(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun carregaListagemItemEstoque(): List<ItemEstoque>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun veriricaSeItemJaEstaAdicionado(id: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getIDsDeItemAdicionados(): List<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun confirmaSelecaoItens(
        listaParaAdicionar: List<ItemEstoque>,
        listaParaRemover: List<ItemEstoque>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun confirmaCadastroItem(listaValoresRecebidos: List<Double>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItensCadastrados(): List<ItemEstoque> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeItem(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelaEnvio() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registraEnvio() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEnvio(): Envio? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}