package com.lemobs_sigelu.gestao_estoques.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.lemobs_sigelu.gestao_estoques.api_model.login.LoginDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.LoginController
import com.lemobs_sigelu.gestao_estoques.common.domain.model.Permissao
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.squareup.moshi.JsonDataException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class LoginViewModel (private val loginController: LoginController): ViewModel(){

    private val disposables = CompositeDisposable()
    private var response = MutableLiveData<Response>()

    var username: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    private var loading: ObservableField<Boolean> = ObservableField(false)

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun loading(): ObservableField<Boolean>{
        return loading
    }

    fun login(){

        if(loading.get() == true)
            return

        response.value = Response.loading()

        val username = username.get() ?: ""
        val password = password.get() ?: ""

        if(username.isBlank() || password.isBlank()){
            response.value = Response.error(Throwable("Preencha os campos"))
            return
        }


        loading.set(true)
        disposables.add(loginController.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    if(result.token_usuario != null) {
                        val auth: String = result.token_usuario.substring(7)
                        checarPermissaoModulo(result, auth)
                    }
                    else{
                        this.loading.set(false)
                        response.value = Response.error(Throwable(result?.message ?: "Ocorreu um erro inesperado"))
                    }
                },
                { throwable ->
                    this.loading.set(false)
                    response.setValue(Response.error(throwable))
                }
            )
        )
    }

    private fun checarPermissaoModulo(loginResponse: LoginDataResponse, authorization: String) {

        disposables.add(loginController.carregaPermissoesModulo(authorization)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { serviceResponse ->

                    this.loading.set(false)
                    var permissaoConcedida = true
                    for(r in serviceResponse){
                        if(r == Permissao.LOGIN.nome){
                            permissaoConcedida = true
                            break
                        }
                    }

                    if(!permissaoConcedida)
                        response.value = Response.error(Throwable("Permissão negada ao módulo"))
                    else {
                        loginController.salvarCredenciaisUsuario(loginResponse)
                        response.value = Response.success(Object())
                    }
                },
                { e ->

                    this.loading.set(false)
                    when (e) {
                        is JsonDataException -> response.value = Response.error(Throwable("Ocorreu um erro inesperado"))
                        is UnknownHostException -> response.value = Response.error(Throwable("Sem conexão"))
                        else -> response.value = Response.error(Throwable("Ocorreu um erro inesperado"))
                    }
                }
            )
        )
    }

    private fun salvarPermissoes(loginResponse: LoginDataResponse){


    }
}