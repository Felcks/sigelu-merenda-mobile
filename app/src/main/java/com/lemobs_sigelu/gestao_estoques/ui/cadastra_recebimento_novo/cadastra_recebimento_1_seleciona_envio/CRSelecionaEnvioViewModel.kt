package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_1_seleciona_envio

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.App
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.Fluxo
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.utils.AppSharedPreferences
import com.lemobs_sigelu.gestao_estoques.utils.FlowSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CRSelecionaEnvioViewModel(val cadastraRecebimentoModel: CadastraRecebimentoModel): ViewModel() {

    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var carregandoProximaTela = MutableLiveData<Response>().apply { value = Response.empty() }

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
                        it.codigo ?: "") }
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
        }
    }


    fun getFluxo(): Fluxo = cadastraRecebimentoModel
}