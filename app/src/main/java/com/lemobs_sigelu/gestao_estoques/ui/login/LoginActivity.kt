package com.lemobs_sigelu.gestao_estoques.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.OnClick
import com.lemobs_sigelu.gestao_estoques.R
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Response
import com.lemobs_sigelu.gestao_estoques.common.viewmodel.Status
import kotlinx.android.synthetic.main.activity_login2.*
import javax.inject.Inject

class LoginActivity: AppCompatActivity(){


    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory
    var viewModel: LoginViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        viewModel!!.response().observe(this, Observer<Response> { response -> processResponse(response) })


        val mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_login2)


        //viewModel!!.login()
    }

    @OnClick(R.id.btn_login)
    private fun login(){
        viewModel!!.login()
    }

    private fun processResponse(response: Response?) {
        when (response?.status) {
            Status.LOADING -> renderLoadingState()
            Status.SUCCESS -> renderDataState(response.data)
            Status.ERROR -> renderErrorState(response.error)
        }
    }

    private fun renderLoadingState() {
        ll_loading.visibility = View.VISIBLE
    }

    private fun renderDataState(result: Any?) {
        ll_loading.visibility = View.INVISIBLE
    }

    private fun renderErrorState(throwable: Throwable?) {
        ll_loading.visibility = View.INVISIBLE
        //Material dialog
    }

}