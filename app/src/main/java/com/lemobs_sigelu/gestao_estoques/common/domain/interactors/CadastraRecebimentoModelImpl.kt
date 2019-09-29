package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEnvioRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException
import com.lemobs_sigelu.gestao_estoques.exceptions.RecebimentoNaoCriadoException
import com.lemobs_sigelu.gestao_estoques.exceptions.UsuarioSemPermissaoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.FluxoInfo

class CadastraRecebimentoModelImpl(val usuarioModel: UsuarioModel,
                                   val nucleoModel: NucleoModel,
                                   val envioRepository: EnvioRepository,
                                   val itemEnvioRepository: ItemEnvioRepository): CadastraRecebimentoModel{

    private var recebimento: Recebimento2? = null
    private var pedidoEstoqueID: Int? = null
    private var pedidoEstoqueEnvioID: Int? = null

    private var passoCorrente = 0
    private var quantidadePasso = 0

    override fun setPedidoEstoqueID(pedidoEstoqueID: Int) {
        this.pedidoEstoqueID = pedidoEstoqueID
    }

    override fun iniciaRecebimento(pedidoEstoqueID: Int, pedidoEstoqueEnvioID: Int) {

        val nucleo = nucleoModel.getNucleo()
        val usuario = usuarioModel.getUsuario(nucleo)

        if(!usuario.temPermissao(PermissaoModel.PERMISSAO_CADASTRA_RECEBIMENTO)){
            throw UsuarioSemPermissaoException()
        }

        recebimento = Recebimento2(
            null,
            pedidoEstoqueID,
            pedidoEstoqueEnvioID
        )
    }

    override fun cadastraQuantidadeEObservacaoMaterial(
        listaItemEstoqueID: List<Int>,
        listaValor: List<Double>,
        listaObservacao: List<String>
    ) {

        if(recebimento == null)
            throw RecebimentoNaoCriadoException()

        if(listaItemEstoqueID.isEmpty() || listaValor.isEmpty())
            throw NenhumItemSelecionadoException()

        if(listaItemEstoqueID.size != listaValor.size || listaValor.size != recebimento!!.listaItemRecebimento.size)
            throw Exception()

        var count = 0
        for(id in listaItemEstoqueID){

            val valor = listaValor[count]
            val observacao = listaObservacao[count]

            val item = recebimento!!.listaItemRecebimento.first { it.itemEstoque.id == id }
            item.quantidadeRecebida = valor
            item.observacao = observacao
            count += 1
        }
    }

    override fun cancelaRecebimento() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun confirmaRecebimento() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecebimento(): Recebimento2? {
        return this.recebimento
    }

    override suspend fun getListaEnvio(): List<Envio2> {
        return envioRepository.getListaEnvio2(pedidoEstoqueID ?: 0) ?: listOf()
    }

    override suspend fun getListaItemEnvio(): List<ItemEnvio> {
        return itemEnvioRepository.getListaItemEnvio2(
            recebimento?.pedidoEstoqueID ?: 0,
            recebimento?.pedidoEstoqueEnvioID ?: 0) ?: listOf()
    }

    override fun getTextoProximoPasso(): String {

        return when(passoCorrente){
            1 ->  "Itens"
            2 ->  "Confirmar"
            else -> " "
        }
    }

    override fun getPassoAtual(): Int {
        return passoCorrente
    }

    override fun setPassoAtual(value: Int) {
        this.passoCorrente  = value
    }

    override fun getMaximoPasso(): Int {
        return quantidadePasso
    }

    override fun setMaximoPasso(value: Int) {
        this.quantidadePasso = value
    }

    override fun incrementaPassoAtual() {
        this.passoCorrente += 1
    }

    override fun decrementaPassoAtual() {
        this.passoCorrente -= 1
    }
}