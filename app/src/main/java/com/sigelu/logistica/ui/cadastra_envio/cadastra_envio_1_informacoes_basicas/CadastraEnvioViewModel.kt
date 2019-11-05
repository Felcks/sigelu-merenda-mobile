package com.sigelu.logistica.ui.cadastra_envio.cadastra_envio_1_informacoes_basicas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.sigelu.logistica.common.domain.interactors.CadastraEnvioController
import com.sigelu.logistica.common.viewmodel.Response
import com.sigelu.logistica.extensions_constants.toDiaMesAno
import com.sigelu.logistica.extensions_constants.toHoraMinuto
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