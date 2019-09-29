package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_3_confirma

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoModel
import com.lemobs_sigelu.gestao_estoques.common.domain.model.ItemRecebimento
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.ItemEstoqueDTO
import com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.UnidadeMedidaDTO
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
            }
            catch (e: Exception){
                cadastroRecebimentoResponse.value = Response.error(Throwable(e.message))
            }
        }
    }

    fun getFluxo() = cadastraRecebimentoModel
}