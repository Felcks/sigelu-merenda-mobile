package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import android.util.Log
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api_model.post_pedido.PedidoResponseOfRequest
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.*
import com.lemobs_sigelu.gestao_estoques.exceptions.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.TIPO_ESTOQUE_NUCLEO
import com.lemobs_sigelu.gestao_estoques.extensions_constants.TIPO_ESTOQUE_OBRA
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CadastraEnvioParaObraControllerImpl(val obraRepository: IObraRepository,
                                          val itemEstoqueRepository: ItemEstoqueRepository,
                                          val nucleoModel: NucleoModel,
                                          val pedidoRepository: PedidoRepository,
                                          val usuarioModel: UsuarioModel,
                                          val envioRepository: EnvioRepository,
                                          val estoqueRepository: EstoqueRepository): CadastraEnvioParaObraController {

    private var listaObra: List<Obra>? = null
    private var listaItemEstoque: List<ItemEstoque>? = null
    private var envio: Envio2? = null

    private var passoCorrente = 0
    private var quantidadePasso = 0

    override suspend fun carregaListagemObra(): List<Obra> {
        listaObra = obraRepository.carregaListaObra()
        return listaObra ?: listOf()
    }

    override fun selecionaObra(obraID: Int) {

        val nucleo = nucleoModel.getNucleo()
        val usuario = usuarioModel.getUsuario(nucleo)

        if(!usuario.temPermissao(PermissaoModel.PERMISSAO_CADASTRA_PEDIDO)){
            throw UsuarioSemPermissaoException()
        }

        var nucleoEstoqueID: Int = 0

        val job = CoroutineScope(Dispatchers.IO).launch {
            val a = async { estoqueRepository.getEstoqueIDNucleo(nucleo.id) }

            nucleoEstoqueID = a.await()
        }

        while(!job.isCompleted){}

        if(this.listaObra == null){
            throw Exception("Lista obra não carregada.")
        }

        val obra = listaObra?.first { it.id == obraID } ?: throw Exception("Ocorreu um erro, tente novamente.")

        val localOrigem = Local2(TIPO_ESTOQUE_NUCLEO, nucleo.nome, nucleoEstoqueID)
        val localDestino = Local2(TIPO_ESTOQUE_OBRA, obra.codigo, obra.estoqueID)
        val movimento = Movimento(null, TipoMovimento.NUCLEO_PARA_OBRA, localOrigem, localDestino)

        if(!movimento.validaMovimento()){
            throw MovimentoInvalidoException()
        }

        this.envio = Envio2(null, usuario, movimento)
    }

    override suspend fun carregaListagemItemEstoque(): List<ItemEstoque> {

        val nucleoID = AppSharedPreferences.getNucleoID(App.instance)
        var nucleoEstoqueID: Int = 0

        val job = CoroutineScope(Dispatchers.IO).launch {
            val a = async { estoqueRepository.getEstoqueIDNucleo(nucleoID) }

            nucleoEstoqueID = a.await()
        }

        while(!job.isCompleted){}


        this.listaItemEstoque = itemEstoqueRepository.carregaListaItemEstoque3(nucleoEstoqueID)
        return listaItemEstoque ?: listOf()
    }

    override fun veriricaSeItemJaEstaAdicionado(id: Int): Boolean {

        if(envio == null)
            throw EnvioNaoCriadoException()

        return !envio!!.listaItemEstoque.map { it.id }.contains(id)
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

    override fun confirmaCadastroItem(listaValoresRecebidos: List<Double>,
                                      listaObservacao: List<String>) {

        if(envio == null)
            throw EnvioNaoCriadoException()

        if(envio?.listaItemEstoque == null)
            throw Exception()

        if(listaValoresRecebidos.size != envio?.listaItemEstoque?.size)
            throw java.lang.Exception()

        var count = 0
        for(item in envio!!.listaItemEstoque){

            val valor = listaValoresRecebidos[count]
            val observacao = listaObservacao[count]

            if(valor <= 0.0){
                throw ValorMenorQueZeroException()
            }

            if(valor >= (item.quantidadeDisponivel ?: 0.0)){
                throw ItemSemQuantidadeDisponivelException()
            }

            item.quantidadeRecebida = valor
            item.observacao = observacao
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

    override suspend fun registraEnvio(observacoes: List<String>) {

        if(envio == null)
            throw EnvioNaoCriadoException()

        for(i in 0 until observacoes.size){

            if(i < envio!!.listaItemEstoque.size)
                (envio!!.listaItemEstoque)[i].observacao = observacoes[i]
        }

        val pedidoResponse = enviaPedido(observacoes)
        envioRepository.postEnvio2(pedidoResponse.id, envio!!)
    }

    private suspend fun enviaPedido(observacoes: List<String>): PedidoResponseOfRequest {

        if(envio == null)
            throw EnvioNaoCriadoException()


        val pedido = Pedido2(
            null,
            envio!!.usuario,
            envio!!.movimento
        )
        pedido.listaMaterial = envio!!.listaItemEstoque.map {
            Material(
                null,
                it,
                it.quantidadeRecebida ?: 0.0)
        } as MutableList<Material>

        return pedidoRepository.enviaPedido(pedido)
    }

    override fun getEnvio(): Envio2? {
        return envio
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

    override fun getTextoProximoPasso(): String {

        return when(passoCorrente){
            1 -> "Materiais"
            2 -> "Quantidade"
            3 -> "Confirmar"
            else -> " "
        }
    }
}