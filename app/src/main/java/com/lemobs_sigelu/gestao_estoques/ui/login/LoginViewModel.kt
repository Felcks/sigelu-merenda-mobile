package com.lemobs_sigelu.gestao_estoques.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableField
import android.util.Log
import com.lemobs_sigelu.gestao_estoques.api_model.login.LoginDataResponse
import com.lemobs_sigelu.gestao_estoques.common.domain.interactors.LoginUseCase
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.squareup.moshi.JsonDataException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.UnknownHostException

class LoginViewModel (private val loginUseCase: LoginUseCase): ViewModel(){

    private val disposables = CompositeDisposable()
    var response = MutableLiveData<Response>()

    var username: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun response(): MutableLiveData<Response> {
        return response
    }

    fun login(){

        val username = username.get() ?: ""
        val password = password.get() ?: ""

        if(username.isBlank() || password.isBlank()){
            Log.i("script2", "nada acontece")
            return
        }

        disposables.add(loginUseCase.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Response.loading()) }
            .subscribe(
                { result ->
                    checarPermissaoModulo(result)
                    //response.setValue(Response.success(result))
                },
                { throwable ->
                    response.setValue(Response.error(throwable))
                }
            )
        )
    }

    private fun checarPermissaoModulo(response: LoginDataResponse) {

        val auth: String = response.token_usuario.substring(7)

        val subscription = loginUseCase.carregaPermissoesModulo(auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->

                    var permissaoConcedida = false
//                    for(r in response){
//                        if(r == Permissao.LOGIN.nome){
//                            permissaoConcedida = true
//                            break
//                        }
//                    }
//
//                    if(!permissaoConcedida)
//                        view.showAcessoNegadoAModulo()
//                    else {
//                        this.salvarPermissoes(response)
//                        view.loginSucess(login)
//                    }
//                    //checarPermissaoPlataforma(login, auth, response)
                },
                { e ->
//                    if(e is JsonDataException){
//                        view.showLoginError()
//                    }
//                    else if(e is UnknownHostException){
//                        view.showNoConnectionError()
//                    }
//                    else
//                        view.showLoginError()
                }
            )
        //subscriptions.add(subscription)
    }
}