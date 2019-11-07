package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.IObraRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.NOME_ALMOXARIFADO
import com.lemobs_sigelu.gestao_estoques.extensions_constants.TIPO_ESTOQUE_ALMOXARIFADO
import com.lemobs_sigelu.gestao_estoques.extensions_constants.TIPO_ESTOQUE_NUCLEO
import com.lemobs_sigelu.gestao_estoques.extensions_constants.TIPO_ESTOQUE_OBRA
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_pedido.FluxoInfo
import kotlinx.coroutines.*

class CadastraPedidoModelImpl(
    private val usuarioModel: UsuarioModel,
    private val nucleoModel: NucleoModel,
    private val itemEstoqueRepository: ItemEstoqueRepository,
    private val obraRepository: IObraRepository,
    private val pedidoRepository: PedidoRepository,
    private val estoqueRepository: EstoqueRepository): CadastraPedidoModel{

    private var listaTodosItemEstoque: List<ItemEstoque>? = null
    private var listaTodasObra: List<Obra>? = null
    private var passoCorrente = 0
    private var quantidadePasso = 0

    companion object{
        var pedido: Pedido2? = null
    }

    override fun iniciaRMParaEstoque() {

        val nucleo = nucleoModel.getNucleo()
        val usuario = usuarioModel.getUsuario(nucleo)

        if(!usuario.temPermissao(PermissaoModel.PERMISSAO_CADASTRA_PEDIDO)){
            throw UsuarioSemPermissaoException()
        }

        var nucleoEstoqueID: Int = 0
        var almoxarifadoEstoqueID: Int = 0

        runBlocking {
            try {
                nucleoEstoqueID = estoqueRepository.getEstoqueIDNucleo(nucleo.id)
                almoxarifadoEstoqueID = estoqueRepository.getEstoqueIDAlmoxarifado()
            }
            catch (e: java.lang.Exception) {
                throw java.lang.Exception("Conecte-se a internet para fazer um pedido.")
            }
        }

        val localOrigem = Local2(TIPO_ESTOQUE_ALMOXARIFADO, NOME_ALMOXARIFADO, almoxarifadoEstoqueID)
        val localDestino = Local2(TIPO_ESTOQUE_NUCLEO, nucleo.nome, nucleoEstoqueID)
        val movimento = Movimento(null, TipoMovimento.ALMOXARIFADO_PARA_NUCLEO, localOrigem, localDestino)

        if(!movimento.validaMovimento()){
            throw MovimentoInvalidoException()
        }

        pedido = Pedido2(null, usuario, movimento)
    }

    override fun iniciaRMParaObra(obraID: Int) {

        val nucleo = nucleoModel.getNucleo()
        val usuario = usuarioModel.getUsuario(nucleo)

        if(!usuario.temPermissao(PermissaoModel.PERMISSAO_CADASTRA_PEDIDO)){
            throw UsuarioSemPermissaoException()
        }

        var almoxarifadoEstoqueID: Int = 0

        runBlocking {
            try {
                almoxarifadoEstoqueID = estoqueRepository.getEstoqueIDAlmoxarifado()
            }
            catch (e: java.lang.Exception){
                throw java.lang.Exception("Conecte-se a internet para fazer um pedido.")
            }
        }


        if(this.listaTodasObra == null){
            throw Exception("Lista de obra não carregada.")
        }

        val obra = listaTodasObra?.first { it.id == obraID } ?: throw Exception("Ocorreu um erro, tente novamente.")

        val localOrigem = Local2(TIPO_ESTOQUE_ALMOXARIFADO, NOME_ALMOXARIFADO, almoxarifadoEstoqueID)
        val localDestino = Local2(TIPO_ESTOQUE_OBRA, obra.codigo, obra.estoqueID)
        val movimento = Movimento(null, TipoMovimento.ALMOXARIFADO_PARA_OBRA, localOrigem, localDestino)

        if(!movimento.validaMovimento()){
            throw MovimentoInvalidoException()
        }

        pedido = Pedido2(null, usuario, movimento)
    }

    override fun iniciaPedidoAPartirDeRascunho(pedido: Pedido2) {

        CadastraPedidoModelImpl.Companion.pedido = pedido
    }

    override fun selecionaListaMaterial(listaIDAdicao: List<Int>, listaIDRemocao: List<Int>) {

        if(pedido == null)
            throw PedidoNaoCriadoException()

        pedido!!.listaMaterial.removeAll { listaIDRemocao.contains(it.itemEstoque.id) }

        for(id in listaIDAdicao) {
            val item = listaTodosItemEstoque?.first { it.id == id }

            if(item != null)
                pedido!!.listaMaterial.add(Material(null, item, 0.0))
        }
    }

    override fun verificaSeItemJaAdicionado(id: Int): Boolean {

        if(pedido == null)
            throw PedidoNaoCriadoException()

        if(pedido!!.listaMaterial.isNotEmpty()){
            return pedido!!.listaMaterial.map { it.itemEstoque.id }.contains(id)
        }

        return false
    }

    override fun getIdItensAdicionados(): List<Int> {

        if(pedido?.listaMaterial == null)
            return listOf()

        return pedido?.listaMaterial?.map { it.itemEstoque.id }!!
    }

    override fun getListaItensAdicionados(): List<Material>{

        if(pedido?.listaMaterial == null)
            return listOf()

        return pedido?.listaMaterial as List<Material>
    }

    override fun removeItem(itemEstoqueID: Int) {

        if(pedido == null)
            throw PedidoNaoCriadoException()

        val item = pedido?.listaMaterial?.first { it.itemEstoque.id == itemEstoqueID }
        if(item != null){
            pedido?.listaMaterial?.remove(item)
        }
        else{
            throw java.lang.Exception("Erro")
        }
    }

    override fun cadastraQuantidadeMaterial(listaID: List<Int>,
                                            listaValor: List<Double>,
                                            listaObservacao: List<String>) {

        if(pedido == null)
            throw PedidoNaoCriadoException()

        if(listaID.isEmpty() || listaValor.isEmpty())
            throw NenhumItemSelecionadoException()

        if(listaID.size != listaValor.size || listaValor.size != pedido!!.listaMaterial.size)
            throw Exception()

        var count = 0
        for(id in listaID){

            val valor = listaValor[count]
            val observacao = listaObservacao[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }

            val item = pedido!!.listaMaterial.first { it.itemEstoque.id == id }
            item.quantidadeRecebida = valor
            item.observacao = observacao
            count += 1
        }
    }

    override fun confirmaPedido() {

        if(pedido == null)
            throw PedidoNaoCriadoException()
    }

    override suspend fun enviaPedido(observacoes: List<String>, isRascunho: Boolean) {

        for(i in 0 until observacoes.size){

            if(i < pedido?.listaMaterial!!.size)
                (pedido?.listaMaterial as List<Material>)[i].observacao = observacoes.get(i)
        }
        pedidoRepository.enviaPedido(pedido!!, isRascunho)
    }

    override fun cancelaPedido() {}

    override fun getPedido(): Pedido2 {
        return pedido ?: throw PedidoNaoCriadoException()
    }

    override suspend fun getListaItemEstoque(): List<ItemEstoque> {
        listaTodosItemEstoque = itemEstoqueRepository.carregaListaEstoque2() ?: listOf()
        return listaTodosItemEstoque!!
    }

    override suspend fun getListaObra(): List<Obra> {
        listaTodasObra = obraRepository.carregaListaObra()
        return listaTodasObra ?: listOf()
    }

    override fun getTextoProximoPasso(): String {

        return when(passoCorrente){
            1 -> if(quantidadePasso == FluxoInfo.NUCLEO.maximoPassos) "Materiais" else "Obras"
            2 -> if(quantidadePasso == FluxoInfo.NUCLEO.maximoPassos) "Quantidade" else "Materiais"
            3 -> if(quantidadePasso == FluxoInfo.NUCLEO.maximoPassos) "Confirmar" else "Quantidades"
            4 -> if(quantidadePasso == FluxoInfo.NUCLEO.maximoPassos) " " else "Confirmar"
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

    override suspend fun getEstoqueIDNucleo(nucleoID: Int): Int {
        return estoqueRepository.getEstoqueIDNucleo(nucleoID)
    }

    override suspend fun getEstoqueIDAlmoxarifado(): Int {
        return estoqueRepository.getEstoqueIDAlmoxarifado()
    }
}