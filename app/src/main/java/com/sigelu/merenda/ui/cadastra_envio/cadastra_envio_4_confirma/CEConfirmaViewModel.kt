package com.sigelu.merenda.ui.cadastra_envio.cadastra_envio_4_confirma

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sigelu.merenda.common.domain.interactors.CadastraEnvioParaObraController
import com.sigelu.merenda.common.domain.model.Envio2
import com.sigelu.merenda.common.viewmodel.Response
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class CEConfirmaViewModel(val controller: CadastraEnvioParaObraController): ViewModel() {

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()
    var envioPedidoResponse = MutableLiveData<Response>()
    var rascunhoPedidoResponse = MutableLiveData<Response>()
    var loading = ObservableField<Boolean>()

    var quantidadeRecebida: ObservableField<String> = ObservableField("")
    var carregandoProximaTela = MutableLiveData<Response>()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun carregaListaItem(){
        response.value = Response.success(controller.getItensCadastrados())
    }

    fun cancelarPedido(){
        controller.cancelaEnvio()
    }

    fun enviaPedido(observacoes: List<String>){

        CoroutineScope(Dispatchers.IO).launch {
            envioPedidoResponse.postValue(Response.loading())

            try{
                controller.registraEnvio(observacoes)
                envioPedidoResponse.postValue(Response.success(""))
            }
            catch (e: HttpException){

                if(e.code() == 410)
                    envioPedidoResponse.postValue(Response.error(Throwable("Item indisponível na origem.")))
            }
            catch (e: Exception){
                envioPedidoResponse.postValue(Response.error(e))
            }
        }

    }

    fun salvaRascunho(){
        
    }

    fun getEnvio(): Envio2?{
        return controller.getEnvio()
    }

    fun getFluxo() = controller
}