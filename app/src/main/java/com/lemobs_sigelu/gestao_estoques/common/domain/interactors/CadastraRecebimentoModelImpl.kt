package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.EnvioRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEnvioRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.NenhumItemSelecionadoException
import com.lemobs_sigelu.gestao_estoques.exceptions.RecebimentoNaoCriadoException
import com.lemobs_sigelu.gestao_estoques.exceptions.UsuarioSemPermissaoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException

class CadastraRecebimentoModelImpl(val usuarioModel: UsuarioModel,
                                   val nucleoModel: NucleoModel,
                                   val envioRepository: EnvioRepository,
                                   val itemEnvioRepository: ItemEnvioRepository): CadastraRecebimentoModel{

    private var recebimento: Recebimento2? = null

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

    override fun getRecebimento(): Recebimento2 {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getListaEnvio(): List<Envio2> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getListaItemEnvio(): List<ItemEstoque> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}