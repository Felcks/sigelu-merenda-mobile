package com.lemobs_sigelu.gestao_estoques.common.domain.interactors

import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.api.RestApi
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento_sem_pedido.ItemRecebimentoDataRequest
import com.lemobs_sigelu.gestao_estoques.api_model.recebimento_sem_pedido.RecebimentoSemPedidoDataRequest
import com.lemobs_sigelu.gestao_estoques.common.domain.model.*
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.FornecedorRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemContratoRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.ItemEstoqueRepository
import com.lemobs_sigelu.gestao_estoques.common.domain.repository.NucleoRepository
import com.lemobs_sigelu.gestao_estoques.exceptions.*
import com.lemobs_sigelu.gestao_estoques.extensions_constants.db
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by felcks on Jul, 2019
 */
class CadastraRecebimentoSemPedidoController @Inject constructor(private val itemContratoRepository: ItemContratoRepository,
                                                                 private val fornecedorRepository: FornecedorRepository,
                                                                 private val nucleoRepository: NucleoRepository,
                                                                 private val itemEstoqueRepository: ItemEstoqueRepository) {

    private val api = RestApi()

    companion object{
        private var recebimentoSemPedido: RecebimentoSemPedido? = null
        private var listaItemContrato : MutableList<ItemContrato>? = mutableListOf()
        private var itemRecebimento: ItemRecebimento? = null
        private var listaFornecedorComContratoVigente: List<Fornecedor>? = null
        private var listaItemEstoque: MutableList<ItemEstoque>? = mutableListOf()
    }

    fun carregaListaFornecedor(): Observable<List<Fornecedor>>{
        return fornecedorRepository.carregaListaFornecedor()
    }

    fun filtraListaFornecedorParaFornecedorComPeloMenosUmContratoVigente(listaFornecedor: List<Fornecedor>): List<Fornecedor>{
        val listaFiltrata = mutableListOf<Fornecedor>()
        for(fornecedor in listaFornecedor){

            if(fornecedor.listaContratoEstoque?.none { it.situacao == "Vigente" } == true){
                //Não tem nenhum contrato com a situação "Vigente"
            }
            else{
                listaFiltrata.add(fornecedor)
            }
        }

        if(listaFiltrata.isEmpty())
            throw NenhumItemDisponivelException()

        return listaFiltrata
    }

    fun salvaLista(listaFornecedor: List<Fornecedor>){
        listaFornecedorComContratoVigente = listaFornecedor
    }

    fun getNucleoDestino(): Nucleo{

        val meuNucleo =  nucleoRepository.getMeuNucleo()
        if(meuNucleo.id == -1)
            throw UsuarioSemNucleoException()

        return meuNucleo
    }

    fun confirmarInformacoesBasicasRecebimento(fornecedorSelecionadoPos: Int){

        val nucleo = getNucleoDestino()
        val fornecedor = listaFornecedorComContratoVigente?.get(fornecedorSelecionadoPos) ?: throw RecebimentoSemPedido.FornecedorNaoSelecionadoException()

        recebimentoSemPedido = RecebimentoSemPedido(null, fornecedor, nucleo)
    }

    fun getListaContratoVigenteIDs(): List<Int>{

        val fornecedor = recebimentoSemPedido?.fornecedorOrigem ?: throw Exception("Sem Fornecedor")
        val listaContratoVigente = fornecedor.listaContratoEstoque?.filter { it.situacao == "Vigente" }
        return listaContratoVigente?.map { it.id } ?: throw FornecedorSemContratoVigenteException()
    }

    fun getListaItem(contratoID: Int): Observable<List<ItemContrato>> {

        return itemContratoRepository.carregaListaItemContrato(contratoID)
    }

    fun salvaListaItemContrato(lista: List<ItemContrato>){

        listaItemContrato?.addAll(lista)
    }

    fun getItemContrato(): ItemContrato{
        return recebimentoSemPedido?.listaItemContrato?.first() ?: throw Exception("Sem nenhum item contrato")
    }

    fun confirmaCadastroItem(valor: Double){

        if(valor <= 0.0){
            throw ValorMenorQueZeroException()
        }

        val item =  recebimentoSemPedido?.listaItemContrato?.first()

        val itemRecebimento = ItemRecebimento(
            null,
            item?.id,
            valor
        )

        itemRecebimento.quantidadeUnidade = item?.quantidadeUnidade

        itemRecebimento.itemEstoque = item?.itemEstoque

        if(valor > item?.quantidadeUnidade ?: 999999999.0){
            throw ValorMaiorQuePermitidoException()
        }

        CadastraRecebimentoSemPedidoController.itemRecebimento = itemRecebimento
    }

    fun getItemRecebimento(): Observable<ItemRecebimento>{
        return Observable.create {subscriber ->
            if(itemRecebimento != null)
                subscriber.onNext(itemRecebimento!!)
            else
                subscriber.onError(Exception("Sem item recebimento."))
        }
    }

    fun cancelaRecebimento(){
        recebimentoSemPedido = null
        itemRecebimento = null
        listaItemContrato = mutableListOf()
        listaFornecedorComContratoVigente = null
    }

    fun getItensJaAdicionados(): List<Int>{

        if(listaItemEstoque == null)
            return listOf<Int>()

        return listaItemEstoque?.map { it.id }!!
    }

    fun selecionaItem(itemID: Int) : Boolean{
        return listaItemEstoque?.map { it.id }?.contains(itemID) != true
    }

    fun removeItemAdicionado() {
       listaItemEstoque?.clear()
    }

    fun confirmaSelecaoItens(listaParaAdicionar: List<ItemEstoque>, listaParaRemover: List<ItemEstoque>){

        val idItensParaRemover = listaParaRemover.map { it.id }
        listaItemEstoque?.removeAll { idItensParaRemover.contains(it.id) }
        listaItemEstoque?.addAll(listaParaAdicionar)
    }

    fun getListaItemEstoque(): Observable<List<ItemEstoque>>{
        return itemEstoqueRepository.carregaListaEstoque()
    }

    fun getItensEstoqueSolicitado(): List<ItemEstoque>?{
        return listaItemEstoque
    }

    fun getEnvio(): Envio?{
        return db.envioDAO().getByID(FlowSharedPreferences.getEnvioId(App.instance))
    }

    fun confirmaCadastroMaterial(listaValoresRecebidos: List<Double>){
        var count = 0
        if(listaItemEstoque == null)
            throw java.lang.Exception("")

        for(item in listaItemEstoque!!){

            val valor = listaValoresRecebidos[count]
            item.quantidadeRecebida = valor
            count += 1
        }

    }

    fun enviaRecebimento(): Observable<Boolean> {

        return Observable.create { subscriber ->

            val origemID = recebimentoSemPedido?.fornecedorOrigem?.id
            val destinoID = recebimentoSemPedido?.nucleoDestino?.id

            if (origemID == null) {
                subscriber.onError(Throwable("Pedido sem origem"))
            }
            if (destinoID == null) {
                subscriber.onError(Throwable("Pedido sem destino"))
            }

            val recebimentoDataRequest = RecebimentoSemPedidoDataRequest(
                origemID!!,
                destinoID!!,
                listaItemContrato!!.map {
                    ItemRecebimentoDataRequest(
                        it.categoria?.categoria_id ?: 0,
                        it.itemEstoqueID ?: 0,
                        it.precoUnidade ?: 0.0,
                        itemRecebimento?.quantidadeRecebida ?: 0.0
                    )
                }
            )


            val callResponse = api.postRecebimentoSemPedidoEstoque(recebimentoDataRequest)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                subscriber.onNext(true)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun getRecebimentoSemPedido(): RecebimentoSemPedido? = recebimentoSemPedido


}