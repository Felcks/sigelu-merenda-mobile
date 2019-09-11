package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.IObraRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.PedidoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.*

class CadastraPedidoModelImpl(
    private val usuarioModel: UsuarioModel,
    private val nucleoModel: NucleoModel,
    private val itemEstoqueRepository: ItemEstoqueRepository,
    private val obraRepository: IObraRepository,
    private val pedidoRepository: PedidoRepository): CadastraPedidoModel{

    private var pedido: Pedido2? = null
    private var listaTodosItemEstoque: List<ItemEstoque>? = null
    private var listaTodasObra: List<Obra>? = null
    private var passoAtual = 0
    private var quantidadePasso = 0

    override fun iniciaRMParaEstoque() {

        val nucleo = nucleoModel.getNucleo()
        val usuario = usuarioModel.getUsuario(nucleo)

        if(!usuario.temPermissao(PermissaoModel.PERMISSAO_CADASTRA_PEDIDO)){
            throw UsuarioSemPermissaoException()
        }

        val localOrigem = Local2(null, TipoLocal.ALMOXARIFADO.name, TipoLocal.ALMOXARIFADO)
        val localDestino = Local2(null, nucleo.nome, TipoLocal.NUCLEO)
        val movimento = Movimento(null, TipoMovimento.ALMOXARIFADO_PARA_NUCLEO, localOrigem, localDestino)

        if(!movimento.validaMovimento()){
            throw MovimentoInvalidoException()
        }

        this.pedido = Pedido2(null, usuario, movimento)
    }

    override fun iniciaRMParaObra(obraID: Int) {

        val nucleo = nucleoModel.getNucleo()
        val usuario = usuarioModel.getUsuario(nucleo)

        if(!usuario.temPermissao(PermissaoModel.PERMISSAO_CADASTRA_PEDIDO)){
            throw UsuarioSemPermissaoException()
        }

        if(this.listaTodasObra == null){
            throw Exception("Lista obra não carregada.")
        }

        val obra = listaTodasObra?.first { it.id == obraID } ?: throw Exception("Ocorreu um erro, tente novamente.")

        val localOrigem = Local2(null, TipoLocal.ALMOXARIFADO.name, TipoLocal.ALMOXARIFADO)
        val localDestino = Local2(null, obra.codigo, TipoLocal.OBRA)
        val movimento = Movimento(null, TipoMovimento.ALMOXARIFADO_PARA_OBRA, localOrigem, localDestino)

        if(!movimento.validaMovimento()){
            throw MovimentoInvalidoException()
        }

        this.pedido = Pedido2(null, usuario, movimento)
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
            return pedido!!.listaMaterial.map { it.id }.contains(id)
        }

        return false
    }

    override fun getIdItensAdicionados(): List<Int> {

        if(pedido?.listaMaterial == null)
            return listOf()

        return pedido?.listaMaterial?.map { it.itemEstoque.id }!!
    }

    override fun cadastraQuantidadeMaterial(listaID: List<Int>, listaValor: List<Double>) {

        if(pedido == null)
            throw PedidoNaoCriadoException()

        if(listaID.size != listaValor.size || listaValor.size != pedido!!.listaMaterial.size)
            throw Exception()

        var count = 0
        for(id in listaID){

            val valor = listaValor[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }

            val item = pedido!!.listaMaterial.first { it.id == id }
            item.quantidadeRecebida = valor
            count += 1
        }
    }

    override fun confirmaPedido(observacao: String) {

        if(pedido == null)
            throw PedidoNaoCriadoException()

        pedido!!.observacao = observacao
    }

    override suspend fun enviaPedido() {
        //TODO
    }

    override suspend fun getListaItemEstoque(): List<ItemEstoque>? {
        listaTodosItemEstoque = itemEstoqueRepository.carregaListaEstoque2()
        return listaTodosItemEstoque
    }

    override suspend fun getListaObra(): List<Obra>? {
        listaTodasObra = obraRepository.carregaListaObra()
        return listaTodasObra
    }

    override fun getTextoPassoAtual(): String {

        if(quantidadePasso == 0){
            return "Passo $passoAtual de ?"
        }

        return "Passo $passoAtual de $quantidadePasso"
    }

    override fun getTextoProximoPasso(): String {

        return when(passoAtual){
            0 -> "Próximo: ?"
            1 -> "Materais"
            2 -> "Quantidades"
            3 -> "Confirmar"
            else -> ""
        }
    }
}