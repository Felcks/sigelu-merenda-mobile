package com.sigelu.logistica.ui.cadastra_recebimento_novo.cadastra_recebimento_1_seleciona_envio

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.logistica.App
import com.sigelu.logistica.common.domain.interactors.CadastraRecebimentoModel
import com.sigelu.logistica.common.domain.interactors.Fluxo
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.utils.FlowSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CRSelecionaEnvioViewModel(val cadastraRecebimentoModel: CadastraRecebimentoModel): ViewModel() {

    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var carregandoProximaTela = MutableLiveData<Response>().apply { value = Response.empty() }

    private var pedidoEstoqueEnvioIDSelecionado: Int? = null

    private val listaEnvioResponse = MutableLiveData<Response>()
    fun listaEnvioResponse() = listaEnvioResponse

    init {
        cadastraRecebimentoModel.setPedidoEstoqueID(FlowSharedPreferences.getPedidoId(App.instance))
    }

    fun carregaListaEnvio(){

        loading.set(true)
        isError.set(false)

        CoroutineScope(Dispatchers.IO).launch {

            try{

                val retrived = cadastraRecebimentoModel.getListaEnvio()

                loading.set(false)

                if(retrived.isNotEmpty()) {

                    isError.set(false)
                    val mapped = retrived.map { EnvioDTO(
                        it.id ?: 0,
                        it.pedidoID ?: 0,
                        it.codigo ?: "",
                        it.recebimentoID) }
                    listaEnvioResponse.postValue(Response.success(mapped))
                }
                else{

                    isError.set(true)
                    listaEnvioResponse.postValue(Response.empty())
                }
            }
            catch (e: Exception){
                loading.set(false)
                isError.set(true)
                listaEnvioResponse.postValue(Response.error(Throwable("")))
            }
            finally {
                pedidoEstoqueEnvioIDSelecionado = null
            }
        }
    }

    fun iniciaRecebimento(){

        if(pedidoEstoqueEnvioIDSelecionado == null)
            throw Exception("Selecione um envio.")

        val envio = (this.listaEnvioResponse.value?.data as? List<EnvioDTO>)?.first{
            it.pedidoEstoqueEnvioID == this.pedidoEstoqueEnvioIDSelecionado }
            ?: throw Exception("Selecione um envio.")

        cadastraRecebimentoModel.iniciaRecebimento(
            envio.pedidoEstoqueID,
            envio.pedidoEstoqueEnvioID
        )
    }

    fun selecionaPedidoEstoqueEnvioID(id: Int){
        this.pedidoEstoqueEnvioIDSelecionado = id
    }

    fun getFluxo(): Fluxo = cadastraRecebimentoModel
}