package com.lemobs_sigelu.gestao_estoques.ui.cadastra_recebimento_novo.cadastra_recebimento_2_cadastra_item

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraRecebimentoModel
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response

class CRCadastraItemViewModel(val cadastraRecebimentoModel: CadastraRecebimentoModel): ViewModel() {

    var loading = ObservableField<Boolean>(false)
    var isError = ObservableField<Boolean>(false)
    var carregandoProximaTela = MutableLiveData<Response>().apply { value = Response.empty() }


    fun getFluxo() = cadastraRecebimentoModel
}