package com.lemobs_sigelu.gestao_estoques.ui.envio.cadastra_envio_informacoes_basicas

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioViewModel (private val controller: CadastraEnvioController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    var nomeMotorista: ObservableField<String> = ObservableField("")


    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun cadastraInformacoesIniciais(){
        controller.cadastraInformacoesIniciaisPedido(nomeMotorista.get() ?: "", Date())
    }
}