package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_2_cadastra_item

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoModel
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CRCadastraItemViewModel(val cadastraRecebimentoModel: CadastraRecebimentoModel): ViewModel() {

    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var carregandoProximaTela = MutableLiveData<Response>().apply { value = Response.empty() }

    var listaItemResponse = MutableLiveData<Response>()

    fun carregaListaItemEnvio(){

        this.loading.set(true)
        this.isError.set(false)

        CoroutineScope(Dispatchers.IO).launch {

            try{

                val retrieved = cadastraRecebimentoModel.getListaItemEnvio()

                loading.set(false)

                if(retrieved.isNotEmpty()) {

                    isError.set(false)
                    val mapped = retrieved.map { ItemRecebimentoDTO(
                        it.id,
                        it.pedidoEstoqueID ?: 0,
                        it.envio_id ?: 0,
                        it.itemEstoqueID ?: 0,
                        ItemEstoqueDTO(
                            it.itemEstoqueID ?: 0,
                            it.itemEstoque?.codigo ?: "",
                            it.itemEstoque?.nomeAlternativo ?: "",
                            it.itemEstoque?.descricao ?: "",
                            UnidadeMedidaDTO(
                                it.itemEstoque?.unidadeMedida?.id ?: 0,
                                it.itemEstoque?.unidadeMedida?.nome ?: "",
                                it.itemEstoque?.unidadeMedida?.sigla ?: ""
                            )
                        ),
                        it.observacao,
                        it.quantidadeUnidade
                    ) }
                    listaItemResponse.postValue(Response.success(mapped))
                }
                else{

                    isError.set(true)
                    listaItemResponse.postValue(Response.empty())
                }
            }
            catch (e: Exception){
                loading.set(false)
                isError.set(true)
                listaItemResponse.postValue(Response.error(Throwable("")))
            }
        }
    }

    fun getFluxo() = cadastraRecebimentoModel
}