package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.IObraRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.EnvioNaoCriadoException
import com.lemobs_sigelu.gestao_estoques.exceptions.ObraNaoPermitidaException
import com.lemobs_sigelu.gestao_estoques.exceptions.ValorMenorQueZeroException

class CadastraEnvioParaObraControllerImpl(val obraRepository: IObraRepository,
                                          val itemEstoqueRepository: ItemEstoqueRepository,
                                          val nucleoModel: NucleoModel,
                                          val usuarioModel: UsuarioModel): CadastraEnvioParaObraController {

    private var listaObra: List<Obra>? = null
    private var listaItemEstoque: List<ItemEstoque>? = null
    private var envio: Envio2? = null

    override suspend fun carregaListagemObra(): List<Obra>? {
        listaObra = obraRepository.carregaListaObra()
        return listaObra
    }

    override fun selecionaObra(id: Int) {

        if(listaObra == null)
            throw Exception("Obras não carregadas")

        if(!listaObra!!.map { it.id }.contains(id))
            throw ObraNaoPermitidaException()

        val obra = listaObra!!.first { it.id == id }
        val destino = Local(obra.id, "Obra", obra.codigo)

        val origem = Local(nucleoModel.getNucleoID(), "Núcleo", nucleoModel.getNucleoNome())

        this.envio = Envio2(
            origem,
            destino,
            usuarioModel.getUsuarioNome()
        )
    }

    override suspend fun carregaListagemItemEstoque(): List<ItemEstoque>? {
        this.listaItemEstoque = itemEstoqueRepository.carregaListaEstoque2()
        return listaItemEstoque
    }

    override fun veriricaSeItemJaEstaAdicionado(id: Int): Boolean {

        if(envio == null)
            throw EnvioNaoCriadoException()

        return envio!!.listaItemEstoque.map { it.id }.contains(id) != true
    }

    override fun getIDsDeItemAdicionados(): List<Int> {

        if(envio?.listaItemEstoque == null)
            return listOf()

        return envio?.listaItemEstoque?.map { it.id }!!
    }

    override fun confirmaSelecaoItens(
        listaParaAdicionar: List<ItemEstoque>,
        listaParaRemover: List<ItemEstoque>
    ) {

        val idItensParaRemover = listaParaRemover.map { it.id }
        envio?.listaItemEstoque?.removeAll { idItensParaRemover.contains(it.id) }
        envio?.listaItemEstoque?.addAll(listaParaAdicionar)
    }

    override fun confirmaCadastroItem(listaValoresRecebidos: List<Double>) {

        if(envio == null)
            throw EnvioNaoCriadoException()

        if(envio?.listaItemEstoque == null)
            throw Exception()

        if(listaValoresRecebidos.size != envio?.listaItemEstoque?.size)
            throw java.lang.Exception()

        var count = 0
        for(item in envio!!.listaItemEstoque){

            val valor = listaValoresRecebidos[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }

            item.quantidadeRecebida = valor
            count += 1
        }
    }

    override fun getItensCadastrados(): List<ItemEstoque> {
        return envio?.listaItemEstoque ?: listOf()
    }

    override fun removeItem(id: Int) {

        val item = envio?.listaItemEstoque?.find { it.id == id }
        if(item != null){
            envio?.listaItemEstoque?.remove(item)
        }
        else{
            throw java.lang.Exception("Erro")
        }
    }

    override fun cancelaEnvio() {
        envio = null
    }

    override fun registraEnvio() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEnvio(): Envio2? {
        return envio
    }
}