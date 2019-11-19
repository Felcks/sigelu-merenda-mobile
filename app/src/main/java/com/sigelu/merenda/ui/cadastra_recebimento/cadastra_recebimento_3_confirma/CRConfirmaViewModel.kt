package com.sigelu.merenda.ui.cadastra_recebimento.cadastra_recebimento_3_confirma

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.merenda.common.domain.interactors.CadastraRecebimentoModel
import com.sigelu.merenda.common.viewmodel.Response
import com.sigelu.merenda.common.viewmodel.Status
import com.sigelu.merenda.ui.cadastra_recebimento.ItemEstoqueDTO
import com.sigelu.merenda.ui.cadastra_recebimento.UnidadeMedidaDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CRConfirmaViewModel(val cadastraRecebimentoModel: CadastraRecebimentoModel): ViewModel() {

    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)

    var cadastroRecebimentoResponse = MutableLiveData<Response>().apply { value = Response.empty() }
    var listaItemRecebimentoResponse = MutableLiveData<Response>().apply { Response.empty() }

    fun carregaListaItemRecebimento(){

        val retrieved = cadastraRecebimentoModel?.getRecebimento()?.listaItemRecebimento
        val list = retrieved?.map {
            ItemRecebimentoDTO(
                it.quantidadeEnviada,
                ItemEstoqueDTO(
                    it.itemEstoque.id,
                    it.itemEstoque.codigo,
                    it.itemEstoque.nomeAlternativo,
                    it.itemEstoque.descricao,
                    UnidadeMedidaDTO(
                        it.itemEstoque.unidadeMedida?.id ?: 0,
                        it.itemEstoque.unidadeMedida?.nome ?: "",
                        it.itemEstoque.unidadeMedida?.sigla ?: ""
                    )
                ),
                it.quantidadeRecebida,
                it.observacao
            )
        }

        listaItemRecebimentoResponse.value = Response.success(list ?: listOf())
    }

    fun cadastraRecebimento(){

        if(cadastroRecebimentoResponse.value?.status != Status.EMPTY_RESPONSE)
            return

        cadastroRecebimentoResponse.value = Response.loading()

        CoroutineScope(Dispatchers.IO).launch {

            try {
                cadastraRecebimentoModel.confirmaRecebimento()
                cadastroRecebimentoResponse.postValue(Response.success(""))
            }
            catch (e: Exception){
                cadastroRecebimentoResponse.postValue(Response.error(Throwable(e.message)))
            }
        }
    }

    fun getFluxo() = cadastraRecebimentoModel
}