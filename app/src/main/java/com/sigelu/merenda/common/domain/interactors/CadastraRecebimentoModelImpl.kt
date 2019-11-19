package com.sigelu.merenda.common.domain.interactors

import com.sigelu.merenda.common.domain.model.*
import com.sigelu.merenda.common.domain.repository.EnvioRepository
import com.sigelu.merenda.common.domain.repository.ItemEnvioRepository
import com.sigelu.merenda.common.domain.repository.RecebimentoRepository
import com.sigelu.merenda.exceptions.*

class CadastraRecebimentoModelImpl(val usuarioModel: UsuarioModel,
                                   val nucleoModel: NucleoModel,
                                   val envioRepository: EnvioRepository,
                                   val itemEnvioRepository: ItemEnvioRepository,
                                   val recebimentoRepository: RecebimentoRepository): CadastraRecebimentoModel{

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
        listaItemRecebimento: List<ItemRecebimento2>
    ) {

        if(recebimento == null)
            throw RecebimentoNaoCriadoException()

        if(listaItemRecebimento.isEmpty() )
            throw NenhumItemSelecionadoException()

        for(item in listaItemRecebimento){

            if(item.quantidadeRecebida > item.quantidadeEnviada)
                throw ValorMaiorQuePermitidoException()
        }

        this.recebimento?.listaItemRecebimento = listaItemRecebimento
    }

    override fun cancelaRecebimento() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun confirmaRecebimento() {

        if(recebimento == null)
            throw RecebimentoNaoCriadoException()

        recebimentoRepository.cadastraRecebimento(recebimento!!)
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