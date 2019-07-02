package com.lemobs_sigelu.gestao_estoques.ui.cadastra_envio.cadastra_envio_informacoes_basicas

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.CadastraEnvioController
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.extensions_constants.toDiaMesAno
import com.lemobs_sigelu.gestao_estoques.extensions_constants.toHoraMinuto
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception
import java.util.*

/**
 * Created by felcks on Jun, 2019
 */
class CadastraEnvioViewModel (private val controller: CadastraEnvioController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    var nomeMotorista: ObservableField<String> = ObservableField("")
    var dataSaida: ObservableField<String> = ObservableField(Date().toDiaMesAno())
    var horaSaida: ObservableField<String> = ObservableField(Date().toHoraMinuto())


    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun cadastraInformacoesIniciais(){

        val nomeMotorista = nomeMotorista.get() ?: ""
        if(nomeMotorista.isBlank()){
            throw Exception("Preencha o nome do motorista.")
        }

        controller.cadastraInformacoesIniciaisPedido(nomeMotorista, Date())
    }
}